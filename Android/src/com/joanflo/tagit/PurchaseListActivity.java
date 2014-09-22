package com.joanflo.tagit;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.joanflo.adapters.PurchaseListAdapter;
import com.joanflo.adapters.PurchaseListItem;
import com.joanflo.controllers.ShopsController;
import com.joanflo.controllers.TaxesController;
import com.joanflo.controllers.UsersController;
import com.joanflo.models.Batch;
import com.joanflo.models.Country;
import com.joanflo.models.Purchase;
import com.joanflo.models.PurchaseDetail;
import com.joanflo.models.Shop;
import com.joanflo.models.Tax;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.SearchUtils;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


public class PurchaseListActivity extends BaseActivity {
	
	
	private List<PurchaseListItem> purchaseItems;
	private List<Purchase> purchases;
	
	private List<Tax> taxes;
	private List<Shop> shops;

	private Integer requestsNumber;
	
	private int currentPosition;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_purchaselist);
		
		
		taxes = new ArrayList<Tax>();
		shops = new ArrayList<Shop>();
        
        super.showProgressBar(true);
        // call web service
        UsersController controller = new UsersController(this);
		CharSequence userEmail = LocalStorage.getInstance().getUserEmail(this);
        controller.getPurchases(userEmail);
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// uncheck elements
        ListView purchaseList = (ListView) findViewById(R.id.listView_purchase);
        purchaseList.setItemChecked(currentPosition, false);
	}
	
	
	
	public void purchasesReceived(List<Purchase> purchases) {
		this.purchases = purchases;
		
		if (purchases.size() == 0) {
			// empty purchases list
			ImageView iv = (ImageView) findViewById(R.id.imageview_purchaseList_empty);
			iv.setVisibility(View.VISIBLE);
			ListView productList = (ListView) findViewById(R.id.listView_purchase);
			productList.setVisibility(View.GONE);
			super.showProgressBar(false);
			Toast.makeText(this, R.string.toast_problem_empty, Toast.LENGTH_LONG).show();
			
		} else {
			// 1 request per purchase (purchase detail)
			requestsNumber = purchases.size();
			
			for (Purchase purchase : purchases) {
				int idPurchase = purchase.getIdPurchase();
				CharSequence userEmail = LocalStorage.getInstance().getUserEmail(this);
				// call web service
				UsersController controller = new UsersController(this);
				controller.getPurchaseDetails(userEmail, idPurchase);
			}
		}
	}
	
	
	
	public void purchaseDetailsReceived(List<PurchaseDetail> purchaseDetails) {
		synchronized (requestsNumber) {
			// purchase detail request received
			requestsNumber--;
			// 2 requests per purchase detail (tax, shop)
			requestsNumber += purchaseDetails.size() * 2;
		}
		
		// search purchase
		int idPurchase = purchaseDetails.get(0).getPurchase().getIdPurchase();
		Purchase purchase = SearchUtils.searchPurchaseById(idPurchase, purchases);
		// for each purchase detail
		CharSequence countryName = LocalStorage.getInstance().getLocaleCountry(this).getCountryName();
		TaxesController tController = new TaxesController(this);
		ShopsController sController = new ShopsController(this);
		for (PurchaseDetail purchaseDetail : purchaseDetails) {
			// add purchase detail to the list
			purchase.addPurchaseDetail(purchaseDetail);
			// (each purchase detail includes his batch)
			Batch batch = purchaseDetail.getBatch();
			int idProduct = batch.getProduct().getIdProduct();
			int idShop = batch.getShop().getIdShop();
			// call web service
			tController.getTax(idProduct, countryName);
			// call web service
			sController.getShop(idShop);
		}
	}
	
	
	
	public synchronized void taxReceived(Tax tax) {
		synchronized (taxes) {
			if (!taxes.contains(tax)) {
				taxes.add(tax);
			}
		}
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void shopReceived(Shop shop) {
		synchronized (shops) {
			if (!shops.contains(shop)) {
				shops.add(shop);
			}
		}
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	private synchronized void checkLastRequest() {
		requestsNumber--;
		if (requestsNumber == 0) {
			// last request
			for (Purchase purchase : purchases) {
				for (PurchaseDetail detail : purchase.getPurchaseDetails()) {
					// get ids
					Batch batch = detail.getBatch();
					int idProduct = batch.getProduct().getIdProduct();
					int idShop = batch.getShop().getIdShop();
					// add tax to batch
					Tax tax = SearchUtils.searchTaxByIdProduct(idProduct, taxes);
					batch.getProduct().addTax(tax);
					// add shop to batch
					Shop shop = SearchUtils.searchShopById(idShop, shops);
					batch.setShop(shop);
				}
			}
			prepareList();
	        super.showProgressBar(false);
		}
	}
	
	
	
	private void prepareList() {
    	Country country = LocalStorage.getInstance().getLocaleCountry(this);
		TaxesController controller = new TaxesController(this);
		// creating purchase list items
		purchaseItems = new ArrayList<PurchaseListItem>();
		Iterator<Purchase> it = purchases.iterator();
        while(it.hasNext()) {
        	Purchase purchase = it.next();
        	
        	// get id
        	int id = purchase.getIdPurchase();
        	// get date
        	Timestamp date = purchase.getDate();
        	// get total price
        	DecimalFormat df = new DecimalFormat("0.00");
        	List<PurchaseDetail> details = purchase.getPurchaseDetails();
			double tPrice = controller.calculateTotalPrice(details, country);
        	CharSequence totalPrice = df.format(tPrice);
        	// get coin
        	char coin = country.getCoin();
        	// get items number
        	CharSequence totalItems = String.valueOf(details.size());
        	// get shop direction
        	Shop shop = details.get(0).getBatch().getShop();
        	CharSequence shopDirection = shop.getDirection();
        	
        	PurchaseListItem purchaseItem = new PurchaseListItem(id, date, totalPrice, coin, totalItems, shopDirection);
        	purchaseItems.add(purchaseItem);
        }
        
        // setting the purchase list adapter
        PurchaseListAdapter adapter = new PurchaseListAdapter(getApplicationContext(), purchaseItems);
        ListView purchaseList = (ListView) findViewById(R.id.listView_purchase);
        purchaseList.setAdapter(adapter);
        
        // setting the purchase click listener
        purchaseList.setOnItemClickListener(new PurchaseClickListener());
	}



	public void onClickButton(View v) {
		super.onClickButton(v);
    }
	
	
	
	private class PurchaseClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// go to purchase detail
			currentPosition = position;
			int purchaseId = purchaseItems.get(position).getPurchaseId();
			Intent i = new Intent(getBaseContext(), PurchaseDetailListActivity.class);
			i.putExtra("purchaseId", purchaseId);
			startActivity(i);
		}
		
	}
	
	
}