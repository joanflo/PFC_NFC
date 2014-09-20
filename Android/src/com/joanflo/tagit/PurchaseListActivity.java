package com.joanflo.tagit;

import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import com.joanflo.adapters.PurchaseListAdapter;
import com.joanflo.adapters.PurchaseListItem;
import com.joanflo.controllers.BrandsController;
import com.joanflo.controllers.TaxesController;
import com.joanflo.controllers.UsersController;
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
import com.joanflo.models.Purchase;
import com.joanflo.models.PurchaseDetail;
import com.joanflo.models.Region;
import com.joanflo.models.Review;
import com.joanflo.models.Shop;
import com.joanflo.models.Size;
import com.joanflo.models.Tax;
import com.joanflo.models.User;
import com.joanflo.models.Wish;
import com.joanflo.utils.LocalStorage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class PurchaseListActivity extends BaseActivity {


	private List<PurchaseListItem> purchaseItems;
	private List<Purchase> purchases;
	
	private Shop shop;
	private int currentPosition;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_purchaselist);
		
        
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
			prepareList();
		}
		
		super.showProgressBar(false);
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
			double tPrice = controller.calculateTotalPrice(purchase.getPurchaseDetails(), country);
        	CharSequence totalPrice = df.format(tPrice);
        	// get coin
        	List<PurchaseDetail> details = purchase.getPurchaseDetails();
        	shop = details.get(0).getBatch().getShop();
        	char coin = shop.getCity().getRegion().getCountry().getCoin();
        	// get items number
        	CharSequence totalItems = String.valueOf(details.size());
        	// get shop direction
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
			
			/*
			// get button info
			TextView tv;
			tv = (TextView) view.findViewById(R.id.textView_totalprice);
			i.putExtra("totalPrice", tv.getText());
			tv = (TextView) view.findViewById(R.id.textView_totalprice_coin);
			i.putExtra("totalPriceCoin", tv.getText());
			tv = (TextView) view.findViewById(R.id.textView_purchaseinfo);
			i.putExtra("purchaseInfo", tv.getText());
			
			// shop id
			i.putExtra("idShop", shop.getIdShop());
			*/
			
			// start activity
			startActivity(i);
		}
		
	}
	
	
}