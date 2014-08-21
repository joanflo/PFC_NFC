package com.joanflo.tagit;

import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import com.joanflo.models.Batch;
import com.joanflo.models.Brand;
import com.joanflo.models.Category;
import com.joanflo.models.City;
import com.joanflo.models.Collection;
import com.joanflo.models.Color;
import com.joanflo.models.Country;
import com.joanflo.models.Language;
import com.joanflo.models.Product;
import com.joanflo.models.ProductImage;
import com.joanflo.models.Region;
import com.joanflo.models.Review;
import com.joanflo.models.Shop;
import com.joanflo.models.Size;
import com.joanflo.models.Tax;
import com.joanflo.models.User;
import com.joanflo.network.ImageLoader;
import com.joanflo.utils.AssetsUtils;
import com.joanflo.utils.NFC;


public class ProductActivity extends BaseActivity implements CreateNdefMessageCallback {
	
	
	private Product product;
	private Shop shop;
	private User user;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_product);
		
        
        // set adapter
        NFC.setNfcAdapter(this);
        
        int productId;
        Intent intent = getIntent();

        if (NFC.isNFCIntent(intent)) {
        	// If an activity starts because of an NFC intent...
        	productId = NFC.retrieveData(intent);
        	if (productId == NFC.PARSING_ERROR) {
        		
        		return;
        	} else {
        		Toast.makeText(this, "product id: " + productId, Toast.LENGTH_LONG).show();
        	}
        	
        } else {
        	Bundle bundle = intent.getExtras();
        	productId = bundle.getInt("productId");
        }
        
        loadInfo(productId);
        prepareSections();
	}

	
	
    @Override
    public void onResume() {
        super.onResume();
        
        // Check to see that the Activity started due to an Android Beam
        Intent intent = getIntent();
        if (NFC.isNFCIntent(intent)) {
        	int productId = NFC.retrieveData(intent);
        	loadInfo(productId);
        	prepareSections();
        }
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
		Iterator<ProductImage> it = images.iterator();
		while (it.hasNext()) {
			ProductImage img = (ProductImage) it.next();
			appendImage(img);
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
		tv.setText(String.valueOf(col.getYear()));
	}
	


	private void preparePriceSection() {
		// get tax
    	Tax tax = product.searchTax(user.getCity().getRegion().getCountry());
    	DecimalFormat df = new DecimalFormat("0.00");
    	String coin = String.valueOf(user.getCity().getRegion().getCountry().getCoin());
    	TextView tv;
    	
    	// set price with IVA
    	int iva = tax.getIva();
    	double priceWithIva = tax.getBasePrice() + (tax.getBasePrice() * (iva / 100));
    	tv = (TextView) findViewById(R.id.textView_product_ivapricetxt);
    	tv.setText(getResources().getString(R.string.product_price) + " (" + String.valueOf(iva) + "% IVA)");
    	tv = (TextView) findViewById(R.id.textView_product_ivaprice);
    	tv.setText(df.format(priceWithIva));
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
    		CharSequence discountTxt = getResources().getString(R.string.product_discount);
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
				if (!sizesUnisex.contains(size)) {
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
		int c = android.graphics.Color.parseColor((String) color.getColorCode());
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



	public void onClickButton(View v) {
		Intent i;
    	
		switch (v.getId()) {
		case R.id.button_product_seereviews:
			i = new Intent(this, ReviewListActivity.class);
	        i.putExtra("idProduct", product.getIdProduct());
	        startActivity(i);
			break;
			
		case R.id.button_product_addreview:
			i = new Intent(this, NewReviewActivity.class);
	        i.putExtra("idProduct", product.getIdProduct());
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
			break;
			
		case R.id.button_product_addtowishlist:
			break;
			
		default:
			super.onClickButton(v);
			break;
		}
    }
	
	
	
	public void onClickRelatedProduct(View v) {
		// get related product
		ViewGroup vg = (ViewGroup) v.getParent();
		int pos = vg.indexOfChild(v);
		Product relatedProduct = product.getRelatedProducts().get(pos);
		
		// go to product activity
		Intent i = new Intent(getBaseContext(), ProductActivity.class);
		i.putExtra("idProduct", relatedProduct.getIdProduct());
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
		String shareTxt = r.getString(R.string.share_part1) + " " + product.getName() + " " + r.getString(R.string.share_part2) + " " + r.getString(R.string.app_name);
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
	
	
	
	private void loadInfo(int productId) {
		Country espanya = new Country("Espanya", null, 34, Country.EURO);
		Region balears = new Region("Balears", null, espanya);
		City palma = new City("Palma", null, balears);
		Language catala = new Language("Català");
		user = new User("joan@uib.cat", palma, catala, "Joan_flo", "Joan", "Florit Gomila", 23, "password", "686922414", "Sant Vicenç Ferrer 117");
		
		Category c1 = new Category(1, null, new ArrayList<Category>(), 0, "Male");
		List<Category> categories = new ArrayList<Category>();
		categories.add(c1);
		Category c2 = new Category(2, null, new ArrayList<Category>(), 0, "Female");
		categories.add(c2);
		Category c3 = new Category(3, null, new ArrayList<Category>(), 0, "Kids");
		categories.add(c3);
		Category c4 = new Category(4, null, new ArrayList<Category>(), 1, "Trousers");
		categories.add(c4);
		Category c5 = new Category(5, new ArrayList<Product>(), null, 1, "Shoes");
		categories.add(c5);
		Category c6 = new Category(6, new ArrayList<Product>(), null, 1, "T-Shirts");
		categories.add(c6);
		Category c7 = new Category(7, new ArrayList<Product>(), null, 1, "Handbag");
		categories.add(c7);
		Category c8 = new Category(8, new ArrayList<Product>(), null, 1, "Skirts");
		categories.add(c8);
		Category c9 = new Category(9, new ArrayList<Product>(), null, 1, "Dress");
		categories.add(c9);
		Category c10 = new Category(10, new ArrayList<Product>(), null, 1, "Assets");
		categories.add(c10);
		Category c11 = new Category(11, new ArrayList<Product>(), null, 2, "Jeans");
		categories.add(c11);
		Category c12 = new Category(12, new ArrayList<Product>(), null, 2, "Fishers");
		categories.add(c12);
		
		List<Category> ac1 = c1.getCategories();
		ac1.add(c4);
		ac1.add(c5);
		ac1.add(c6);
		ac1.add(c10);
		
		List<Category> ac2 = c2.getCategories();
		ac2.add(c4);
		ac2.add(c5);
		ac2.add(c6);
		ac2.add(c7);
		ac2.add(c8);
		ac2.add(c9);
		ac2.add(c10);
		
		List<Category> ac3 = c3.getCategories();
		ac3.add(c4);
		ac3.add(c5);
		ac3.add(c6);
		ac3.add(c8);
		ac3.add(c9);
		ac3.add(c10);
		
		List<Category> ac4 = c4.getCategories();
		ac4.add(c11);
		ac4.add(c12);
		
		
		Brand brand = new Brand("Vans", "", "", "", 0, 0);
		Collection col = new Collection(0, "primavera", 2014);
		Product p1 = new Product(0, brand, col, "Camiseta verda", "Una camiseta verda", "896-25/3", "100% cotton");
		Product p2 = new Product(1, brand, col, "Vestit blau", "", "", "");
		Product p3 = new Product(2, brand, col, "Deportives blanques", "", "", "");
		Product p4 = new Product(3, brand, col, "Vaqueros slim fit", "", "", "");
		Product p5 = new Product(4, brand, col, "Calçons pana", "", "", "");
		Product p6 = new Product(5, brand, col, "Minifalda", "", "", "");
		Product p7 = new Product(6, brand, col, "Bolso 1", "", "", "");
		Product p8 = new Product(7, brand, col, "Tacons", "", "", "");
		Product p9 = new Product(8, brand, col, "Camiseta blanca", "", "", "");
		Product p10 = new Product(9, brand, col, "Camiseta blava", "", "", "");
		Product p11 = new Product(10, brand, col, "Camiseta negra", "", "", "");
		Product p12 = new Product(11, brand, col, "Collar", "", "", "");
		Product p13 = new Product(12, brand, col, "Pulsera", "", "", "");
		
		List<Product> ac5 = c5.getProducts(); // shoes
		ac5.add(p3);
		ac5.add(p8);
		
		List<Product> ac6 = c6.getProducts(); // T-Shirts
		ac6.add(p1);
		ac6.add(p9);
		ac6.add(p10);
		ac6.add(p11);
		
		List<Product> ac7 = c7.getProducts(); //Handbag
		ac7.add(p7);
		
		List<Product> ac8 = c8.getProducts(); //Skirts
		ac8.add(p6);
		
		List<Product> ac9 = c9.getProducts(); //Dress
		ac9.add(p2);
		
		List<Product> ac10 = c10.getProducts(); //Assets
		ac10.add(p12);
		ac10.add(p13);
		
		List<Product> ac11 = c11.getProducts(); //Jeans
		ac11.add(p4);
		
		List<Product> ac12 = c12.getProducts(); // Fishers
		ac12.add(p5);
		

		ProductImage img1;
		try {
			img1 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p1, ProductImage.TYPE_FRONT, "description...");
			p1.addImage(img1);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img14;
		try {
			img14 = new ProductImage("http://shop.camisetasfrikis.es/163-1195-large/camiseta-i-love-mates.jpg", p1, ProductImage.TYPE_REGULAR, "description...");
			p1.addImage(img14);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img15;
		try {
			img15 = new ProductImage("http://shop.camisetasfrikis.es/73-544-large/camiseta-space-invader-game-over.jpg", p1, ProductImage.TYPE_REGULAR, "description...");
			p1.addImage(img15);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img16;
		try {
			img16 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p1, ProductImage.TYPE_REGULAR, "description...");
			p1.addImage(img16);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img17;
		try {
			img17 = new ProductImage("http://1.bp.blogspot.com/-BviKauMn6uY/TicGTGbONII/AAAAAAAAAX0/_QEHg5FADEI/s1600/productimage-picture-hal-9000-60.jpg", p1, ProductImage.TYPE_REGULAR, "description...");
			p1.addImage(img17);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img2;
		try {
			img2 = new ProductImage("http://rlv.zcache.es/mensaje_pionero_camiseta-r4a0ac2d55dc845dca282ff71edc3348c_va6lr_512.jpg?max_dim=400", p2, ProductImage.TYPE_FRONT, "description...");
			p2.addImage(img2);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img3;
		try {
			img3 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p3, ProductImage.TYPE_FRONT, "description...");
			p3.addImage(img3);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img4;
		try {
			img4 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p4, ProductImage.TYPE_FRONT, "description...");
			p4.addImage(img4);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img5;
		try {
			img5 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p5, ProductImage.TYPE_FRONT, "description...");
			p5.addImage(img5);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img6;
		try {
			img6 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p6, ProductImage.TYPE_FRONT, "description...");
			p6.addImage(img6);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img7;
		try {
			img7 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p7, ProductImage.TYPE_FRONT, "description...");
			p7.addImage(img7);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img8;
		try {
			img8 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p8, ProductImage.TYPE_FRONT, "description...");
			p8.addImage(img8);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img9;
		try {
			img9 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p9, ProductImage.TYPE_FRONT, "description...");
			p9.addImage(img9);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img10;
		try {
			img10 = new ProductImage("http://rlv.zcache.es/carl_sagan_las_demandas_requieren_pruebas_camiseta-r23d8d4b20ae04eeab221a535d6bea717_8naiq_512.jpg?max_dim=400", p10, ProductImage.TYPE_FRONT, "description...");
			p10.addImage(img10);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img11;
		try {
			img11 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p11, ProductImage.TYPE_FRONT, "description...");
			p11.addImage(img11);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img12;
		try {
			img12 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p12, ProductImage.TYPE_FRONT, "description...");
			p12.addImage(img12);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img13;
		try {
			img13 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p13, ProductImage.TYPE_FRONT, "description...");
			p13.addImage(img13);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		
		Tax t1 = new Tax(p1, espanya, 50, 21, 10, Tax.DISCOUNT_PERCENT);
		p1.addTax(t1);
		Tax t2 = new Tax(p2, espanya, 50, 21, 50, Tax.DISCOUNT_PERCENT);
		p2.addTax(t2);
		Tax t3 = new Tax(p3, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p3.addTax(t3);
		Tax t4 = new Tax(p4, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p4.addTax(t4);
		Tax t5 = new Tax(p5, espanya, 49.99, 21, 0, Tax.DISCOUNT_MONEY);
		p5.addTax(t5);
		Tax t6 = new Tax(p6, espanya, 49.95, 21, 0, Tax.DISCOUNT_MONEY);
		p6.addTax(t6);
		Tax t7 = new Tax(p7, espanya, 49.678952, 21, 0, Tax.DISCOUNT_MONEY);
		p7.addTax(t7);
		Tax t8 = new Tax(p8, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p8.addTax(t8);
		Tax t9 = new Tax(p9, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p9.addTax(t9);
		Tax t10 = new Tax(p10, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p10.addTax(t10);
		Tax t11 = new Tax(p11, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p11.addTax(t11);
		Tax t12 = new Tax(p12, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p12.addTax(t12);
		Tax t13 = new Tax(p13, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p13.addTax(t13);
		
		
		Review r1 = new Review(0, p1, user, 5, "Lorem ipsum...");
		user.addReview(r1);
		p1.addReview(r1);
		Review r2 = new Review(0, p1, user, (float) 3.5, "Lorem ipsum...");
		user.addReview(r2);
		p1.addReview(r2);
		Review r3 = new Review(0, p1, user, 5, "Lorem ipsum...");
		user.addReview(r3);
		p1.addReview(r3);
		Review r4 = new Review(0, p2, user, 5, "Lorem ipsum...");
		user.addReview(r4);
		p2.addReview(r4);
		Review r5 = new Review(0, p3, user, 5, "Lorem ipsum...");
		user.addReview(r5);
		p3.addReview(r5);
		Review r6 = new Review(0, p4, user, 5, "Lorem ipsum...");
		user.addReview(r6);
		p4.addReview(r6);
		Review r7 = new Review(0, p5, user, 5, "Lorem ipsum...");
		user.addReview(r7);
		p5.addReview(r7);
		Review r8 = new Review(0, p6, user, 5, "Lorem ipsum...");
		user.addReview(r8);
		p6.addReview(r8);
		
		shop = new Shop(0, palma, "Sant Vicenç Ferrer 117", "L-V 9.00h a 19.00h\nS 9.00h a 13.00h", "686922414", "info@tenda.cat", 39.57993, 2.666000);
		
		p1.addRelatedProduct(p2);
		p1.addRelatedProduct(p3);
		p1.addRelatedProduct(p4);
		p1.addRelatedProduct(p10);
		
		Size s1 = new Size(0, 38, Size.GENRE_FEMALE, Size.TYPE_CLOTHES);
		Color co1 = new Color("#ff0000", "red");
		Batch b1 = new Batch(0, p1, s1, co1, shop, 1);
		p1.addBatch(b1);

		Size s2 = new Size(1, 38, Size.GENRE_MALE, Size.TYPE_CLOTHES);
		Color co2 = new Color("#ff0000", "red");
		Batch b2 = new Batch(1, p1, s2, co2, shop, 1);
		p1.addBatch(b2);
		
		Size s3 = new Size(2, 40, Size.GENRE_MALE, Size.TYPE_CLOTHES);
		Color co3 = new Color("#00ff00", "green");
		Batch b3 = new Batch(2, p1, s3, co3, shop, 1);
		p1.addBatch(b3);
		
		
		product = p1;
	}
	
	
	
	public synchronized void requestFinished(JSONObject jResponses) {
		// TODO
	}
	
	
}