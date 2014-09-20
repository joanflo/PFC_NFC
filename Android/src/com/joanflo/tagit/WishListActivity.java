package com.joanflo.tagit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.joanflo.adapters.WishListAdapter;
import com.joanflo.adapters.WishListItem;
import com.joanflo.controllers.BatchesController;
import com.joanflo.controllers.UsersController;
import com.joanflo.models.Batch;
import com.joanflo.models.Product;
import com.joanflo.models.ProductImage;
import com.joanflo.models.Purchase;
import com.joanflo.models.PurchaseDetail;
import com.joanflo.models.Wish;
import com.joanflo.utils.LocalStorage;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


public class WishListActivity extends BaseActivity implements Button.OnClickListener {

	
	private List<Wish> wishes;
	private List<WishListItem> wishItems;
	
	private int currentPosition;
	private View currentView;
	
	private Purchase purchase;
	private Batch batch;
    
	private int requestsNumber;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_wishlist);
		
        
        // update selected item and title, then close the drawer
        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("drawerPosition");
        super.updateSelected(pos);
        
        super.showProgressBar(true);
        // get user email
        CharSequence userEmail = LocalStorage.getInstance().getUserEmail(this);
        // call web service
        UsersController controller = new UsersController(this);
        controller.getWishes(userEmail);
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// uncheck elements
        ListView wishList = (ListView) findViewById(R.id.listView_wish);
        wishList.setItemChecked(currentPosition, false);
	}
	
	
	
	public void wishesReceived(List<Wish> wishes) {
		LocalStorage.getInstance().setWishlistItemsCount(this, wishes.size());
		
		if (wishes.size() == 0) {
			// empty wishes list
			ImageView iv = (ImageView) findViewById(R.id.imageview_wishList_empty);
			iv.setVisibility(View.VISIBLE);
			ListView productList = (ListView) findViewById(R.id.listView_wish);
			productList.setVisibility(View.GONE);
			Toast.makeText(this, R.string.toast_problem_empty, Toast.LENGTH_LONG).show();
			
		} else {
			this.wishes = wishes;
			prepareList();
		}
		
		super.showProgressBar(false);
	}
	
	
	
	public void wishRemoved(boolean successful) {
		if (successful) {
			Toast.makeText(this, R.string.toast_deleteitem, Toast.LENGTH_SHORT).show();
			LocalStorage.getInstance().updateWishlistItemsCount(this, false);
			removeItem(currentPosition);
		} else {
			Toast.makeText(this, R.string.toast_problem_deleteitem, Toast.LENGTH_SHORT).show();
		}
		// hide animation
		showImageButton(currentView, true);
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
	
	
	
	public void batchesReceived(List<Batch> batches) {
		this.batch = batches.get(0);
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void wishAddedToCart(boolean successful) {
		if (successful) {
			Toast.makeText(this, R.string.toast_wishadded, Toast.LENGTH_SHORT).show();
			LocalStorage.getInstance().updateCartItemsCount(this, true);
		} else {
			Toast.makeText(this, R.string.toast_problem_wishadded, Toast.LENGTH_SHORT).show();
		}
		// hide animation
		showImageButton(currentView, true);
	}
	
	
	
	private synchronized void checkLastRequest() {
		requestsNumber--;
		if (requestsNumber == 0) {
			// last request
			CharSequence userEmail = LocalStorage.getInstance().getUserEmail(this);
			PurchaseDetail detail = new PurchaseDetail(purchase, batch, 1);
			// call web service
			UsersController controller = new UsersController(this);
			controller.createPurchaseDetail(userEmail, detail);
		}
	}
	
	
	
	private void prepareList() {
		// creating wish list items
		wishItems = new ArrayList<WishListItem>();
		Iterator<Wish> it = wishes.iterator();
        while(it.hasNext()) {
        	Wish wish = it.next();
        	Product product = wish.getProduct();
        	ProductImage front = product.getFrontImage();
        	WishListItem wishItem = new WishListItem(wish.getDate(), product.getName(), product.getIdProduct(), front.getUrl(), front.getDescription());
        	wishItems.add(wishItem);
        }
        
        // setting the wish list adapter
        WishListAdapter adapter = new WishListAdapter(this, getApplicationContext(), wishItems);
        ListView wishList = (ListView) findViewById(R.id.listView_wish);
        wishList.setAdapter(adapter);
        
        // setting the wish click listener
        wishList.setOnItemClickListener(new WishClickListener());
	}



	public void onClickButton(View v) {
		currentPosition = (Integer) v.getTag();
		UsersController uController = new UsersController(this);
		LocalStorage storage = LocalStorage.getInstance();
		CharSequence userEmail = storage.getUserEmail(this);
    	
		switch (v.getId()) {
		case R.id.button_wishlist_remove:
			int idWish = wishes.get(currentPosition).getIdWish();
			// call web service
			uController.deleteWish(userEmail, idWish);
			// show button animation
			showImageButton(v, false);
			break;
			
		case R.id.button_wishlist_addtocart:
			requestsNumber = 2;
			int idProduct = wishes.get(currentPosition).getProduct().getIdProduct();
			// call web service
			uController.getCartPurchase(userEmail);
			// call web service
			BatchesController bController = new BatchesController(this);
			bController.getBatches(idProduct);
			// show button animation
			showImageButton(v, false);
			break;
		
		default:
			super.onClickButton(v);
			break;
		}
    }



	private void removeItem(int position) {
		// remove from array lists
		wishes.remove(position);
		wishItems.remove(position);
		// remove from list view
		ListView wishList = (ListView) findViewById(R.id.listView_wish);
		WishListAdapter badapter = (WishListAdapter) wishList.getAdapter();
		badapter.notifyDataSetChanged();
	}
	
	
	
	private void showImageButton(View v, boolean show) {
		currentView = v;
		ImageButton ib = (ImageButton) v;
		if (show) {
			switch (v.getId()) {
			case R.id.button_wishlist_remove:
				ib.setImageResource(R.drawable.ic_removewish);
				break;
			case R.id.button_wishlist_addtocart:
				ib.setImageResource(R.drawable.ic_addtocart);
				break;
			}
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
	
	
	
	private class WishClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// go to user profile
			currentPosition = position;
			int idProduct = wishItems.get(position).getProductId();
			Intent i = new Intent(getBaseContext(), ProductActivity.class);
			i.putExtra("idProduct", idProduct);
			startActivity(i);
		}
		
	}
	
	
}