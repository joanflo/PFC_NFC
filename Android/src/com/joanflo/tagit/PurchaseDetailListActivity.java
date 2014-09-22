package com.joanflo.tagit;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.joanflo.adapters.PurchaseDetailListAdapter;
import com.joanflo.adapters.PurchaseDetailListItem;
import com.joanflo.controllers.ColorsController;
import com.joanflo.controllers.ProductsController;
import com.joanflo.controllers.ShopsController;
import com.joanflo.controllers.SizesController;
import com.joanflo.controllers.TaxesController;
import com.joanflo.controllers.UsersController;
import com.joanflo.models.Batch;
import com.joanflo.models.Color;
import com.joanflo.models.Country;
import com.joanflo.models.Product;
import com.joanflo.models.ProductImage;
import com.joanflo.models.Purchase;
import com.joanflo.models.PurchaseDetail;
import com.joanflo.models.Shop;
import com.joanflo.models.Size;
import com.joanflo.models.Tax;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.Time;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class PurchaseDetailListActivity extends BaseActivity implements Button.OnClickListener {

	
	private boolean viewingCart;
	private List<PurchaseDetailListItem> purchaseDetailItems;
	
	private int currentPosition;
	private View currentView;

	private Purchase purchase;
	private Shop shop;
	private int requestsNumber;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_purchasedetaillist);
        
        
        // update selected item and title, then close the drawer
        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("drawerPosition", 0);
        viewingCart = pos != 0;
        
        CharSequence userEmail = LocalStorage.getInstance().getUserEmail(this);
        UsersController controller = new UsersController(this);
        super.showProgressBar(true);
        if (viewingCart) {
        	// view cart
        	super.updateSelected(pos);
        	// call web service
        	controller.getCartPurchase(userEmail);
        	
        } else {
        	// view finished purchase
        	int purchaseId = bundle.getInt("purchaseId");
        	// call web service
        	controller.getPurchase(userEmail, purchaseId);
        }
	}
	
	
	
	public void purchaseReceived(Purchase purchase) {
		if (purchase == null) {
			hideList();
			
		} else {
			this.purchase = purchase;
			
			CharSequence userEmail = LocalStorage.getInstance().getUserEmail(this);
			int purchaseId = purchase.getIdPurchase();
			// call web service
			UsersController controller = new UsersController(this);
			controller.getPurchaseDetails(userEmail, purchaseId);
		}
	}
	
	
	
	public void purchaseDetailsReceived(List<PurchaseDetail> purchaseDetails) {
		if (viewingCart) {
			LocalStorage.getInstance().setCartItemsCount(this, purchaseDetails.size());
			// for each product, 2 requests (tax, front image)
			requestsNumber = purchaseDetails.size() * 2;
		} else {
			// for each product, 4 requests (tax, front image, color, size) + 1 request (shop)
			requestsNumber = purchaseDetails.size() * 4 + 1;
		}
		
		boolean shopRequested = false;
		if (purchaseDetails.size() == 0) {
			hideList();
			
		} else {
			// for each purchase detail
			ProductsController pController = new ProductsController(this);
			ColorsController cController = new ColorsController(this);
			SizesController siController = new SizesController(this);
			ShopsController shController = new ShopsController(this);
			for (PurchaseDetail purchaseDetail : purchaseDetails) {
				purchase.addPurchaseDetail(purchaseDetail);
				
				// (each purchase detail includes his batch)
				Batch batch = purchaseDetail.getBatch();
				// call web service
				pController.getProduct(batch.getProduct().getIdProduct());
				
				if (!viewingCart) {
					// call web service
					cController.getColor(batch.getColor().getColorCode());
					// call web service
					siController.getSize(batch.getSize().getIdSize());
					if (!shopRequested) {
						shopRequested = true;
						// call web service
						shController.getShop(batch.getShop().getIdShop());
					}
				}
			}
		}
	}
	
	
	
	public void productReceived(Product product) {
		// search product position
		int i = 0;
		boolean found = false;
		List<PurchaseDetail> purchaseDetails = purchase.getPurchaseDetails();
		while (i < purchaseDetails.size() && !found) {
			Batch batch = purchaseDetails.get(i).getBatch();
			int idProductAux = batch.getProduct().getIdProduct();
			if (idProductAux == product.getIdProduct()) {
				// add product to batch
				batch.setProduct(product);
				found = true;
			} else {
				i++;
			}
		}
		// search tax
		if (found) {
			Country country = LocalStorage.getInstance().getLocaleCountry(this);
			// call web service
			TaxesController tController = new TaxesController(this);
			tController.getTax(product.getIdProduct(), country.getCountryName());
			// call web service
			ProductsController pController = new ProductsController(this);
			pController.getProductFrontImage(product.getIdProduct());
		}
	}
	
	
	
	public void taxReceived(Tax tax) {
		// search product position
		int i = 0;
		boolean found = false;
		List<PurchaseDetail> purchaseDetails = purchase.getPurchaseDetails();
		while (i < purchaseDetails.size() && !found) {
			Batch batch = purchaseDetails.get(i).getBatch();
			int idProductAux = batch.getProduct().getIdProduct();
			if (idProductAux == tax.getProduct().getIdProduct()) {
				// add tax to product
				batch.getProduct().addTax(tax);
				found = true;
			} else {
				i++;
			}
		}
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	// REVISAR com tots els altres SEARCHUTILS?
	public void frontImageReceived(ProductImage productImage) {
		// search product position
		int i = 0;
		boolean found = false;
		List<PurchaseDetail> purchaseDetails = purchase.getPurchaseDetails();
		while (i < purchaseDetails.size() && !found) {
			Batch batch = purchaseDetails.get(i).getBatch();
			int idProductAux = batch.getProduct().getIdProduct();
			if (idProductAux == productImage.getProduct().getIdProduct()) {
				// add product image to product
				batch.getProduct().addImage(productImage);
				found = true;
			} else {
				i++;
			}
		}
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void colorReceived(Color color) {
		// search batch position
		int i = 0;
		boolean found = false;
		List<PurchaseDetail> purchaseDetails = purchase.getPurchaseDetails();
		while (i < purchaseDetails.size() && !found) {
			Batch batch = purchaseDetails.get(i).getBatch();
			Color colorAux = batch.getColor();
			if (colorAux.getColorCode().equals(color.getColorCode()) && colorAux.getName() == null) {
				// add color to batch
				batch.setColor(color);
				found = true;
			} else {
				i++;
			}
		}
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void sizeReceived(Size size) {
		// search batch position
		int i = 0;
		boolean found = false;
		List<PurchaseDetail> purchaseDetails = purchase.getPurchaseDetails();
		while (i < purchaseDetails.size() && !found) {
			Batch batch = purchaseDetails.get(i).getBatch();
			Size sizeAux = batch.getSize();
			if (sizeAux.getIdSize() == size.getIdSize() && sizeAux.getSize() == 0) {
				// add color to batch
				batch.setSize(size);
				found = true;
			} else {
				i++;
			}
		}
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void shopReceived(Shop shop) {
		this.shop = shop;
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void purchaseDetailDeleted(boolean successful) {
		if (successful) {
			Toast.makeText(this, R.string.toast_deleteitem, Toast.LENGTH_SHORT).show();
			LocalStorage.getInstance().updateCartItemsCount(this, false);
			removeItem();
		} else {
			Toast.makeText(this, R.string.toast_problem_deleteitem, Toast.LENGTH_SHORT).show();
		}
		// hide animation
		showImageButton(currentView, true);
	}
	
	
	
	private synchronized void checkLastRequest() {
		requestsNumber--;
		if (requestsNumber == 0) {
			// last request
			preparePurchaseInfoSection();
			prepareList();
	        super.showProgressBar(false);
		}
	}
	
	
	
	private void preparePurchaseInfoSection() {
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.layout_purchaseinfo);
		if (viewingCart) {
			rl.setVisibility(View.GONE);
			
		} else {
			rl.setVisibility(View.VISIBLE);
			TextView tv;
			
			tv = (TextView) findViewById(R.id.textView_purchasedetail_totalpricecoin);
			Country country = LocalStorage.getInstance().getLocaleCountry(this);
			tv.setText(String.valueOf(country.getCoin()));
			
			tv = (TextView) findViewById(R.id.textView_purchasedetail_totalprice);
			TaxesController controller = new TaxesController(this);
			double totalPrice = controller.calculateTotalPrice(purchase.getPurchaseDetails(), country);
			tv.setText(String.valueOf(totalPrice));
			
			tv = (TextView) findViewById(R.id.textView_purchasedetail_purchaseinfo);
			String date = Time.convertTimestampToString(purchase.getDate());
			tv.setText(shop.getDirection() + "\n(" + date + ")");
		}
	}



	@Override
	protected void onResume() {
		super.onResume();
		
		// uncheck elements
        ListView purchaseDetailList = (ListView) findViewById(R.id.listView_purchasedetail);
        purchaseDetailList.setItemChecked(currentPosition, false);
	}
	
	
	
	private void prepareList() {
		// creating purchase detail list items
		purchaseDetailItems = new ArrayList<PurchaseDetailListItem>();
		Iterator<PurchaseDetail> it = purchase.getPurchaseDetails().iterator();
        while(it.hasNext()) {
        	PurchaseDetail purchaseDetail = it.next();
        	
        	// get units
        	int units = purchaseDetail.getUnits();
        	// get product id
        	Batch batch = purchaseDetail.getBatch();
        	Product product = batch.getProduct();
        	int productId = product.getIdProduct();
        	// get product name
        	CharSequence productName = product.getName();
        	// get product image
        	ProductImage image = product.getFrontImage();
        	URL url = image.getUrl();
        	CharSequence description = image.getDescription();
        	// get product price
        	Tax tax = product.getTaxes().get(0);
        	DecimalFormat df = new DecimalFormat("0.00");
        	CharSequence totalPrice = df.format(product.calculatePrice(tax));
        	// get coin
        	char coin = LocalStorage.getInstance().getLocaleCountry(this).getCoin();
        	
        	PurchaseDetailListItem purchaseDetailItem;
        	if (viewingCart) {
        		purchaseDetailItem = new PurchaseDetailListItem(units, productId, productName, url, description, totalPrice, coin);
        	
        	} else {
            	// get size
            	int size = batch.getSize().getSize();
            	// get color
            	CharSequence colorCode = batch.getColor().getColorCode();
            	CharSequence colorName = batch.getColor().getName();
        		
        		purchaseDetailItem = new PurchaseDetailListItem(units, productId, productName, url, description, totalPrice, coin, size, colorCode, colorName);
        	}
        	purchaseDetailItems.add(purchaseDetailItem);
        }
        
        // setting the purchase detail list adapter
        PurchaseDetailListAdapter adapter = new PurchaseDetailListAdapter(this, getApplicationContext(), purchaseDetailItems, viewingCart);
        ListView purchaseDetailList = (ListView) findViewById(R.id.listView_purchasedetail);
        purchaseDetailList.setAdapter(adapter);
        
        // setting the purchase detail click listener
        purchaseDetailList.setOnItemClickListener(new PurchaseDetailClickListener());
	}
	
	
	
	public void onClickButton(View v) {
		
		switch (v.getId()) {
		case R.id.button_purchase_remove:
			currentPosition = (Integer) v.getTag();
			
			CharSequence userEmail = LocalStorage.getInstance().getUserEmail(this);
			int idPurchase = purchase.getIdPurchase();
			int idPurchaseDetail = purchase.getPurchaseDetails().get(currentPosition).getIdPurchaseDetail();
			// call web service
			UsersController controller = new UsersController(this);
			controller.deletePurchaseDetail(userEmail, idPurchase, idPurchaseDetail);
			// show button animation
			showImageButton(v, false);
			break;
			
		case R.id.button_purchasedetail_seeshop:
			Intent i = new Intent(this, ShopActivity.class);
			i.putExtra("idShop", shop.getIdShop());
			i.putExtra("cityName", shop.getCity().getCityName().toString());
			i.putExtra("direction", shop.getDirection().toString());
			i.putExtra("schedule", shop.getSchedule().toString());
			i.putExtra("phone", shop.getPhone().toString());
			i.putExtra("email", shop.getEmail().toString());
			i.putExtra("latitude", shop.getLatitude());
			i.putExtra("longitude", shop.getLongitude());
			boolean isSelected = false;
			LocalStorage storage = LocalStorage.getInstance();
			if (storage.isShopPicked(this)) {
				isSelected = storage.getShop(this).equals(shop);
			}
			i.putExtra("isSelected", isSelected);
			startActivity(i);
			break;
		
		default:
			super.onClickButton(v);
			break;
		}
	}


	
	private void removeItem() {
		// remove from array lists
		purchase.getPurchaseDetails().remove(currentPosition);
		purchaseDetailItems.remove(currentPosition);
		// remove from list view
		ListView purchaseDetailList = (ListView) findViewById(R.id.listView_purchasedetail);
		PurchaseDetailListAdapter badapter = (PurchaseDetailListAdapter) purchaseDetailList.getAdapter();
		badapter.notifyDataSetChanged();
	}
	
	
	
	private void hideList() {
		// empty purchase detail list
		ImageView iv = (ImageView) findViewById(R.id.imageview_purchaseDetailList_empty);
		iv.setVisibility(View.VISIBLE);
		ListView productList = (ListView) findViewById(R.id.listView_purchasedetail);
		productList.setVisibility(View.GONE);
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.layout_purchaseinfo);
		rl.setVisibility(View.GONE);
		Toast.makeText(this, R.string.toast_problem_empty, Toast.LENGTH_LONG).show();
		super.showProgressBar(false);
	}
	
	
	
	private void showImageButton(View v, boolean show) {
		currentView = v;
		ImageButton ib = (ImageButton) v;
		if (show) {
			ib.setImageResource(R.drawable.ic_removecart);
			ib.setBackgroundResource(R.drawable.button_selector2);
		} else {
			ib.setImageResource(android.R.color.transparent);
			ib.setBackgroundResource(R.drawable.animation_refresh);
			AnimationDrawable frameAnimation = (AnimationDrawable) ib.getBackground();
			frameAnimation.start();
		}
	}



	@Override
	public void onClick(View v) {
		onClickButton(v);
	}
	
	
	
	private class PurchaseDetailClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// go to product
			currentPosition = position;
			int idProduct = purchaseDetailItems.get(position).getProductId();
			Intent i = new Intent(getBaseContext(), ProductActivity.class);
			i.putExtra("idProduct", idProduct);
			startActivity(i);
		}
		
	}
	

}