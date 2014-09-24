package com.joanflo.tagit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import com.joanflo.controllers.BatchesController;
import com.joanflo.controllers.CollectionsController;
import com.joanflo.controllers.ColorsController;
import com.joanflo.controllers.ProductsController;
import com.joanflo.controllers.SizesController;
import com.joanflo.controllers.TaxesController;
import com.joanflo.controllers.UsersController;
import com.joanflo.models.Batch;
import com.joanflo.models.Collection;
import com.joanflo.models.Color;
import com.joanflo.models.Country;
import com.joanflo.models.Product;
import com.joanflo.models.ProductImage;
import com.joanflo.models.Purchase;
import com.joanflo.models.PurchaseDetail;
import com.joanflo.models.Review;
import com.joanflo.models.Shop;
import com.joanflo.models.Size;
import com.joanflo.models.Tax;
import com.joanflo.models.User;
import com.joanflo.models.Wish;
import com.joanflo.network.ImageLoader;
import com.joanflo.utils.AssetsUtils;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.NFC;
import com.joanflo.utils.SearchUtils;


public class ProductActivity extends BaseActivity implements CreateNdefMessageCallback {
	
	
	private int idProduct;
	private Product product;
	
	private Shop shop;
	private Purchase purchase;
    private Batch batch;
	
	private Integer requestsNumber;
	private boolean isPutRequest;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_product);
		super.setTitle(R.string.title_product);
		
        
        // set adapter
        NFC.setNfcAdapter(this);
        
        Intent intent = getIntent();
        if (NFC.isNFCIntent(intent)) {
        	// If an activity starts because of an NFC intent...
        	idProduct = NFC.retrieveData(intent);
        	if (idProduct == NFC.PARSING_ERROR) {
        		Toast.makeText(this, R.string.toast_problem_productnfc, Toast.LENGTH_LONG).show();
        		finish();
        	}
        } else {
        	Bundle bundle = intent.getExtras();
        	idProduct = bundle.getInt("idProduct");
        }
        
        isPutRequest = false;
	}

	
	
    @Override
    public void onResume() {
        super.onResume();
        
        // Check to see that the Activity started due to an Android Beam
        Intent intent = getIntent();
        if (NFC.isNFCIntent(intent)) {
        	idProduct = NFC.retrieveData(intent);
        }

        super.showProgressBar(true);
        // call web service
        ProductsController productsController = new ProductsController(this);
        productsController.getProduct(idProduct);
    }
    
    

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }



	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		return NFC.createBeamMessage(product.getIdProduct());
	}
	
	
	
	public void productReceived(Product product) {
		this.product = product;
		
		// 6 initial requests (tax, product images, reviews, related products, collection, batches)
		requestsNumber = 6;
		
		// get data
		LocalStorage storage = LocalStorage.getInstance();
		CharSequence countryName = storage.getLocaleCountry(this).getCountryName();
		int idProduct = product.getIdProduct();
		int idCollection = product.getCollection().getIdCollection();
		
		// call web service
		TaxesController tController = new TaxesController(this);
		tController.getTax(idProduct, countryName);
		// call web service
		ProductsController pController = new ProductsController(this);
		pController.getProductImages(idProduct);
		pController.getReviews(idProduct);
		pController.getRelatedProducts(idProduct);
		// call web service
		CollectionsController cController = new CollectionsController(this);
		cController.getCollection(idCollection);
		// call web service
		BatchesController bController = new BatchesController(this);
		if (storage.isShopPicked(this)) {
			// get local availability
			int idShop = storage.getShop(this).getIdShop();
			bController.getBatches(idProduct, idShop);
		} else {
			// get global availability
			bController.getBatches(idProduct);
		}
	}
	
	
	
	public void taxReceived(Tax tax) {
		// add tax to product
		product.addTax(tax);
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void productImagesReceived(List<ProductImage> productImages) {
		// for each product image
		for (ProductImage productImage : productImages) {
			// add image to product
			product.addImage(productImage);
		}
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void reviewsReceived(List<Review> reviews) {
		// for each review
		for (Review review : reviews) {
			// add review to product
			product.addReview(review);
		}
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void relatedProductsReceived(List<Product> relatedProducts) {
		// for each related product
		for (Product relatedProduct : relatedProducts) {
			// add related product to product
			product.addRelatedProduct(relatedProduct);
		}
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void collectionReceived(Collection collection) {
		product.setCollection(collection);
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void batchesReceived(List<Batch> batches) {
		if (isPutRequest) {
			this.batch = batches.get(0);
		
		} else {
			int numExtra = 0;
			List<Integer> sizesId = new ArrayList<Integer>();
			List<CharSequence> colorCodes = new ArrayList<CharSequence>();
			
			// for each batch
			SizesController sizesController = new SizesController(this);
			ColorsController colorsController = new ColorsController(this);
			for (Batch batch : batches) {
				// get ids
				Integer idSize = batch.getSize().getIdSize();
				CharSequence colorCode = batch.getColor().getColorCode();
				
				if (!sizesId.contains(idSize) || !colorCodes.contains(colorCode)) {
					// add product
					product.addBatch(batch);
					
					// check if current size has been already requested
					if (!sizesId.contains(idSize)) {
						sizesId.add(idSize);
						// call web service
						sizesController.getSize(idSize);
						numExtra++;
					}
					
					// check if current color has been already requested
					if (!colorCodes.contains(colorCode)) {
						colorCodes.add(colorCode);
						// call web service
						colorsController.getColor(colorCode);
						numExtra++;
					}
				
				}
			}
			
			// requests all sizes and colors from the received batches
			synchronized (requestsNumber) {
				requestsNumber += numExtra;
			}
		}
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void sizeReceived(Size size) {
		// search batch
		int idSize = size.getIdSize();
		List<Batch> batches = product.getBatches();
		Batch batch = SearchUtils.searchBatchByIdSize(idSize, batches);
		// set size
		batch.setSize(size);
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void colorReceived(Color color) {
		// search batch
		CharSequence colorCode = color.getColorCode();
		List<Batch> batches = product.getBatches();
		Batch batch = SearchUtils.searchBatchByColorCode(colorCode, batches);
		// set color
		batch.setColor(color);
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	private synchronized void checkLastRequest() {
		requestsNumber--;
		if (requestsNumber == 0) {
			// last request
			if (!isPutRequest) {
				prepareSections();
		        super.showProgressBar(false);
		        
			} else {
				CharSequence userEmail = LocalStorage.getInstance().getUserEmail(this);
				PurchaseDetail detail = new PurchaseDetail(purchase, batch, 1);
				// call web service
				UsersController controller = new UsersController(this);
				controller.createPurchaseDetail(userEmail, detail);
			}
		}
	}
	
	
	
	private void prepareSections() {
        prepareProductInfoSection();
        prepareAvailabilitySection();
        preparePriceSection();
        prepareCollectionSection();
        prepareBrandSection();
        prepareSliderSection();
        prepareRelatedProductsSection();
	}
	
	
	
	private void prepareRelatedProductsSection() {
		List<Product> relatedProducts = product.getRelatedProducts();
		if (relatedProducts.isEmpty()) {
			// if there's not related products, hide section
			RelativeLayout rl = (RelativeLayout) findViewById(R.id.section_product_related);
			rl.setVisibility(View.GONE);
			
		} else {
			LinearLayout container = (LinearLayout) findViewById(R.id.layout_relatedproducts);
			container.removeViews(0, container.getChildCount());
			
			Iterator<Product> it = relatedProducts.iterator();
			while (it.hasNext()) {
				Product product = (Product) it.next();
				appendProduct(product);
			}
		}
	}



	private void appendProduct(Product product) {
		// get related product view
		LinearLayout container = (LinearLayout) findViewById(R.id.layout_relatedproducts);
		LayoutInflater mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        ViewGroup convertView = (ViewGroup) mInflater.inflate(R.layout.list_item_relatedproduct, container, false);
		convertView.setTag(product.getIdProduct());
        
        // set image info
		ImageView iv = (ImageView) convertView.findViewById(R.id.imageView_front);
		ProductImage image = product.getFrontImage();
		iv.setContentDescription(image.getDescription());
		ImageLoader il = new ImageLoader(iv);
		il.execute(image.getUrl());
		
		// set name info
		TextView tv = (TextView) convertView.findViewById(R.id.textView_name);
		tv.setText(product.getName());
		
		// add view to root
		container.addView(convertView);
	}



	private void prepareSliderSection() {
		List<ProductImage> images = product.getRegularImages();
		if (images.isEmpty()) {
			HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.section_product_slider);
			hsv.setVisibility(View.GONE);
			
		} else {
			LinearLayout container = (LinearLayout) findViewById(R.id.slider_product);
			container.removeViews(0, container.getChildCount());
			
			Iterator<ProductImage> it = images.iterator();
			while (it.hasNext()) {
				ProductImage img = (ProductImage) it.next();
				appendImage(img);
			}
		}
	}



	private void appendImage(ProductImage image) {
		// set image view info
		ImageView iv = new ImageView(this);
		iv.setContentDescription(image.getDescription());
		ImageLoader il = new ImageLoader(iv);
		il.execute(image.getUrl());
		
		// customize image view
		int width = AssetsUtils.convertDipsToPixels(this, 230);
		int height = AssetsUtils.convertDipsToPixels(this, 230);
		int margin = AssetsUtils.convertDipsToPixels(this, 6);
		LayoutParams params = new LayoutParams(width, height);
		params.setMargins(margin, margin, margin, margin);
		iv.setLayoutParams(params);
		
		// add image to slider
		LinearLayout container = (LinearLayout) findViewById(R.id.slider_product);
		container.addView(iv);
	}



	private void prepareBrandSection() {
		TextView tv = (TextView) findViewById(R.id.textView_product_brand);
		tv.setText(product.getBrand().getBrandName());
	}



	private void prepareCollectionSection() {
		TextView tv;
		Collection col = product.getCollection();
		
		tv = (TextView) findViewById(R.id.textView_product_collectionname);
		tv.setText(col.getName());
		
		tv = (TextView) findViewById(R.id.textView_product_collectionyear);
		int year = col.getYear();
		if (year != 0) {
			tv.setText(String.valueOf(year));
		}
	}
	


	private void preparePriceSection() {
		Country country = LocalStorage.getInstance().getLocaleCountry(this);
		
		// get tax
    	Tax tax = product.getTaxes().get(0);
    	DecimalFormat df = new DecimalFormat("0.00");
    	String coin = String.valueOf(country.getCoin());
    	TextView tv;
    	
    	// set price with IVA
    	int iva = tax.getIva();
    	double priceWithIva = tax.getBasePrice() + (tax.getBasePrice() * (iva / 100));
    	tv = (TextView) findViewById(R.id.textView_product_ivapricetxt);
    	tv.setText(getResources().getString(R.string.product_price) + " (" + String.valueOf(iva) + "% IVA):");
    	tv = (TextView) findViewById(R.id.textView_product_ivaprice);
    	tv.setText(" " + df.format(priceWithIva) + " ");
    	tv = (TextView) findViewById(R.id.textView_product_ivapricecoin);
    	tv.setText(coin);
    	
    	// set discount (if set)
    	if (tax.getDiscount() == 0.0) {
    		// hide discount & final price text views
    		tv = (TextView) findViewById(R.id.textView_product_discounttxt);
    		tv.setVisibility(View.GONE);
        	tv = (TextView) findViewById(R.id.textView_product_discount);
    		tv.setVisibility(View.GONE);
        	tv = (TextView) findViewById(R.id.textView_product_discountcoin);
    		tv.setVisibility(View.GONE);
        	tv = (TextView) findViewById(R.id.textView_product_finalpricetxt);
    		tv.setVisibility(View.GONE);
        	tv = (TextView) findViewById(R.id.textView_product_finalprice);
    		tv.setVisibility(View.GONE);
        	tv = (TextView) findViewById(R.id.textView_product_finalpricecoin);
    		tv.setVisibility(View.GONE);
    		
    	} else { // show discount & final price info
    		
    		// set discount
    		tv = (TextView) findViewById(R.id.textView_product_discounttxt);
    		CharSequence discountTxt = getResources().getString(R.string.product_discount) + ":";
    		Double discountMoney;
    		if (tax.getDiscountType() == Tax.DISCOUNT_PERCENT) {
    			discountTxt = discountTxt + " (-" + df.format(tax.getDiscount()) + "%):";
    			discountMoney = priceWithIva * (tax.getDiscount() / 100);
    		} else { // Tax.DISCOUNT_MONEY
    			discountMoney = tax.getDiscount();
    		}
    		tv.setText(discountTxt);
        	tv = (TextView) findViewById(R.id.textView_product_discount);
    		tv.setText("-" + df.format(discountMoney));
        	tv = (TextView) findViewById(R.id.textView_product_discountcoin);
        	tv.setText(coin);
        	
        	// set final price
        	tv = (TextView) findViewById(R.id.textView_product_finalprice);
        	tv.setText(df.format(product.calculatePrice(tax)));
        	tv = (TextView) findViewById(R.id.textView_product_finalpricecoin);
        	tv.setText(coin);
    	}
	}



	private void prepareAvailabilitySection() {
		List<Batch> batches = product.getBatches(shop);
		Iterator<?> it;
		
		// remove all containers views
		LinearLayout container;
		container = (LinearLayout) findViewById(R.id.linearLayout_product_sizes);
		container.removeViews(1, container.getChildCount() - 1);
		container = (LinearLayout) findViewById(R.id.linearLayout_product_colors);
		container.removeViews(1, container.getChildCount() - 1);
		
		// get colors and sizes
		it = batches.iterator();
		List<Color> colors = new ArrayList<Color>();
		List<Size> sizesFemale = new ArrayList<Size>();
		List<Size> sizesMale = new ArrayList<Size>();
		List<Size> sizesUnisex = new ArrayList<Size>();
		while (it.hasNext()) {
			Batch batch = (Batch) it.next();
			
			Color color = batch.getColor();
			if (!colors.contains(color)) {
				colors.add(color);
			}
			
			Size size = batch.getSize();
			switch (size.getGenre()) {
			case Size.GENRE_FEMALE:
				if (!sizesFemale.contains(size)) {
					sizesFemale.add(size);
				}
				break;
			case Size.GENRE_MALE:
				if (!sizesMale.contains(size)) {
					sizesMale.add(size);
				}
				break;
			case Size.GENRE_UNISEX:
				if (!sizesUnisex.contains(size) && size.getSize() != 0) {
					sizesUnisex.add(size);
				}
				break;
			}
		}
		
		// add colors to interface
		it = colors.iterator();
		while (it.hasNext()) {
			Color color = (Color) it.next();
			appendColor(color);
		}
		
		// add sizes to interface
		int[] sizes;
		if (sizesFemale.size() > 0) {
			sizes = new int[sizesFemale.size()];
			for (int i = 0; i < sizesFemale.size(); i++) {
				Size size = sizesFemale.get(i);
				sizes[i] = size.getSize();
			}
			appendSizes(Size.GENRE_FEMALE, sizes);
		}
		if (sizesMale.size() > 0) {
			sizes = new int[sizesMale.size()];
			for (int i = 0; i < sizesMale.size(); i++) {
				Size size = sizesMale.get(i);
				sizes[i] = size.getSize();
			}
			appendSizes(Size.GENRE_MALE, sizes);
		}
		if (sizesUnisex.size() > 0) {
			sizes = new int[sizesUnisex.size()];
			for (int i = 0; i < sizesUnisex.size(); i++) {
				Size size = sizesUnisex.get(i);
				sizes[i] = size.getSize();
			}
			appendSizes(Size.GENRE_UNISEX, sizes);
		}
		
		TextView textViewSizeTitle = (TextView) findViewById(R.id.textView_product_sizestitle);
		if (sizesFemale.size() + sizesMale.size() + sizesUnisex.size() == 0) {
			// unique size
			textViewSizeTitle.setText(R.string.nosizes);
		} else {
			// add sizes
			textViewSizeTitle.setText(R.string.product_size);
		}
		
		// button
		Button b = (Button) findViewById(R.id.button_product_availability);
		if (shop == null) {
			// check availability
			b.setText(R.string.button_checkavailability);
		} else {
			// change shop
			b.setText(R.string.button_changeshop);
		}
	}



	private void appendColor(Color color) {
		// layout_width="match_parent"
        // layout_height="wrap_content"
    	// orientation="horizontal"
    	// layout_marginTop="6dp"
		LinearLayout child = new LinearLayout(this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		child.setOrientation(LinearLayout.HORIZONTAL);
		int margin = AssetsUtils.convertDipsToPixels(this, 6);
		params.setMargins(0, 0, 0, margin);
		child.setLayoutParams(params);
		
		// style="@style/counter1"
        // layout_marginRight="6dp"
		TextView textViewColorCode = new TextView(this);
		textViewColorCode.setTextAppearance(this, R.style.counter1);
		params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, margin, 0);
		textViewColorCode.setLayoutParams(params);
		int c = android.graphics.Color.parseColor("#" + (String) color.getColorCode());
		textViewColorCode.setBackgroundColor(c);
		textViewColorCode.setText("      ");
		
		// style="@style/p"
		// android:textColor="@color/grey_light"
		TextView textViewColorName = new TextView(this);
		textViewColorName.setTextAppearance(this, R.style.p);
		c = getResources().getColor(R.color.grey_light);
		textViewColorName.setTextColor(c);
		textViewColorName.setText(color.getName());
		
		// add childs
		LinearLayout container = (LinearLayout) findViewById(R.id.linearLayout_product_colors);
		child.addView(textViewColorCode);
		child.addView(textViewColorName);
		container.addView(child);
	}
	
	
	
	private void appendSizes(char sizeType, int[] sizes) {
		// layout_width="match_parent"
        // layout_height="wrap_content"
    	// orientation="horizontal"
    	// layout_marginTop="6dp"
		LinearLayout child = new LinearLayout(this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		child.setOrientation(LinearLayout.HORIZONTAL);
		int margin = AssetsUtils.convertDipsToPixels(this, 6);
		params.setMargins(0, 0, 0, margin);
		child.setLayoutParams(params);
		
		// style="@style/counter1"
        // layout_marginRight="6dp"
		TextView textViewSizeType = new TextView(this);
		textViewSizeType.setTextAppearance(this, R.style.b);
		int c = getResources().getColor(R.color.grey_light);
		textViewSizeType.setTextColor(c);
		params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, margin, 0);
		textViewSizeType.setLayoutParams(params);
		textViewSizeType.setText(String.valueOf(sizeType));
		
		// style="@style/p"
		// android:textColor="@color/grey_light"
		TextView textViewSizes = new TextView(this);
		textViewSizes.setTextAppearance(this, R.style.p);
		c = getResources().getColor(R.color.grey_light);
		textViewSizes.setTextColor(c);
		CharSequence sizesTxt = String.valueOf(sizes[0]);
		for (int i = 1; i < sizes.length; i++) {
			sizesTxt = sizesTxt + ", " + String.valueOf(sizes[i]);
		}
		textViewSizes.setText(sizesTxt);
		
		// add childs
		LinearLayout container = (LinearLayout) findViewById(R.id.linearLayout_product_sizes);
		child.addView(textViewSizeType);
		child.addView(textViewSizes);
		container.addView(child);
	}



	private void prepareProductInfoSection() {
		TextView tv;
		tv = (TextView) findViewById(R.id.title_product_info);
		tv.setText(product.getName());
		
		tv = (TextView) findViewById(R.id.textView_product_rating);
		DecimalFormat df = new DecimalFormat("0.00");
		double averageRate = product.calculateAverageRating();
    	CharSequence rate;
    	if (averageRate == -1) {
    		rate = "-";
    	} else {
    		df = new DecimalFormat("0.0");
    		rate = df.format(averageRate);
    	}
		tv.setText(rate);
		
		tv = (TextView) findViewById(R.id.textView_product_reference);
		tv.setText("ref. " + product.getReference());
		
		tv = (TextView) findViewById(R.id.textView_product_description);
		tv.setText(product.getDescription());
		
		tv = (TextView) findViewById(R.id.textView_product_composition);
		tv.setText(product.getComposition());
		
		ImageView iv = (ImageView) findViewById(R.id.imageView_product_front);
		ProductImage front = product.getFrontImage();
		if (front != null) {
			iv.setContentDescription(front.getDescription());
			ImageLoader il = new ImageLoader(iv);
			il.execute(front.getUrl());
		} else {
			iv.setImageResource(R.drawable.default_image);
		}
	}
	
	
	
	public void productAddedToWishList(Wish wish) {
		if (wish == null) {
			// problem
			Toast.makeText(this, R.string.toast_problem_creatingwish, Toast.LENGTH_SHORT).show();
			
		} else {
			// review created successfully
			Toast.makeText(this, R.string.toast_productaddedwish, Toast.LENGTH_SHORT).show();
			LocalStorage.getInstance().updateWishlistItemsCount(this, true);
		}
		isPutRequest = false;
		// hide spinner
		super.showProgressBar(false);
	}
	
	
	
	public void productAddedToCart(boolean successful) {
		if (successful) {
			Toast.makeText(this, R.string.toast_productaddedcart, Toast.LENGTH_SHORT).show();
			LocalStorage.getInstance().updateCartItemsCount(this, true);
		} else {
			Toast.makeText(this, R.string.toast_problem_productaddedcart, Toast.LENGTH_SHORT).show();
		}
		isPutRequest = false;
		// hide spinner
		super.showProgressBar(false);
	}
	
	
	
	public void cartPurchaseReceived(Purchase cartPurchase) {
		// get purchase id depending on whether or not
		if (cartPurchase == null) {
			this.purchase = new Purchase(0, null, Purchase.STATUS_PENDING);
		} else {
			this.purchase = cartPurchase;
		}
		
		// check if it's the last request
		checkLastRequest();
	}



	public void onClickButton(View v) {
		LocalStorage storage = LocalStorage.getInstance();
		TextView tv;
		Intent i;
    	
		switch (v.getId()) {
		case R.id.button_product_seereviews:
			i = new Intent(this, ReviewListActivity.class);
	        i.putExtra("idProduct", product.getIdProduct());
	        i.putExtra("productName", product.getName());
	        tv = (TextView) findViewById(R.id.textView_product_rating);
	        i.putExtra("averageRate", tv.getText());
	        startActivity(i);
			break;
			
		case R.id.button_product_addreview:
    		if (LocalStorage.getInstance().isUserLoged(this)) {
    			i = new Intent(this, NewReviewActivity.class);
    	        i.putExtra("idProduct", product.getIdProduct());
    	        i.putExtra("productName", product.getName());
    	        tv = (TextView) findViewById(R.id.textView_product_rating);
    	        i.putExtra("averageRate", tv.getText());
    		} else {
        		Toast.makeText(this, R.string.toast_registration, Toast.LENGTH_SHORT).show();
        		i = new Intent(this, RegistrationActivity.class);
        	}
        	startActivity(i);
			break;
			
		case R.id.button_product_seebrand:
			i = new Intent(this, BrandActivity.class);
	        i.putExtra("brandName", product.getBrand().getBrandName());
	        startActivity(i);
			break;
			
		case R.id.button_product_availability:
			Button b = (Button) v;
			if (shop == null) {
				// no current shop selected, check availability
				b.setText(R.string.button_checkavailability);
			} else {
				// change shop
				b.setText(R.string.button_changeshop);
			}
			i = new Intent(this, ShopSelectionActivity.class);
	        i.putExtra("drawerPosition", 1); // 1 = shop selection item position
	        startActivity(i);
			break;
			
		case R.id.button_product_addtocart:
			if (storage.isUserLoged(this)) {
				requestsNumber = 2;
				isPutRequest = true;
				int idProduct = product.getIdProduct();
				super.showProgressBar(true);
				// call web service
				UsersController uController = new UsersController(this);
				uController.getCartPurchase(storage.getUserEmail(this));
				// call web service
				BatchesController bController = new BatchesController(this);
				if (storage.isShopPicked(this)) {
					int idShop = storage.getShop(this).getIdShop();
					bController.getBatches(idProduct, idShop);
				} else {
					bController.getBatches(idProduct);
				}
				
			} else {
				Toast.makeText(this, R.string.toast_registration, Toast.LENGTH_SHORT).show();
        		i = new Intent(this, RegistrationActivity.class);
    	        startActivity(i);
			}
			break;
			
		case R.id.button_product_addtowishlist:
			if (storage.isUserLoged(this)) {
				super.showProgressBar(true);
				// create wish model
				User user = storage.getUser(this);
				Wish wish = new Wish(product, user);
				// call web service
				UsersController uController = new UsersController(this);
				uController.createWish(wish);
				
			} else {
				Toast.makeText(this, R.string.toast_registration, Toast.LENGTH_SHORT).show();
        		i = new Intent(this, RegistrationActivity.class);
    	        startActivity(i);
			}
			break;
			
		default:
			super.onClickButton(v);
			break;
		}
    }
	
	
	
	public void onClickRelatedProduct(View v) {
		// get related product
		ViewGroup vg = (ViewGroup) v.getParent();
		int idProductTag = (int) vg.getTag();
		
		// go to product activity
		Intent i = new Intent(getBaseContext(), ProductActivity.class);
		i.putExtra("idProduct", idProductTag);
		startActivity(i);
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		
		// show share item
		MenuItem shareItem = menu.findItem(R.id.action_share);
		shareItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		// set share item action
		ShareActionProvider provider = (ShareActionProvider) shareItem.getActionProvider();
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		Resources r = getResources();
		String shareTxt = r.getString(R.string.share) + " " + r.getString(R.string.app_name);
		sendIntent.putExtra(Intent.EXTRA_TEXT, shareTxt);
		sendIntent.setType("text/plain");
		provider.setShareIntent(sendIntent);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar actions click
        switch (item.getItemId()) {
        case R.id.action_share:
            return true;
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	
}