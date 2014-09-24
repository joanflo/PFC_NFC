package com.joanflo.tagit;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import com.joanflo.controllers.UsersController;
import com.joanflo.models.Purchase;
import com.joanflo.models.User;
import com.joanflo.utils.LocalStorage;


public class HomeActivity extends BaseActivity {
 
    private static final int SELECT_PICTURE = 1;
	private int requestsNumber;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_home);
		
        
        // update selected item and title, then close the drawer
        Bundle bundle = getIntent().getExtras();
        int pos = 0;
        if (bundle != null) {
        	pos = bundle.getInt("drawerPosition");
        }
        super.updateSelected(pos);
        
        // load user data if isn't available locally
        if (bundle != null) {
        	CharSequence userEmail = bundle.getCharSequence("userEmail", null);
        	if (userEmail != null) {
        		//GUI
        		super.showProgressBar(true);
        		// Call web service
        		requestsNumber = 5;
        		UsersController controller = new UsersController(this);
        		controller.getCurrentUser(userEmail);
        		controller.getFollowers(userEmail);
        		controller.getFollowing(userEmail);
        		controller.getWishes(userEmail);
        		controller.getCartPurchase(userEmail);
        	}
        }
        
		prepareSearchSection();
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		// update shop section
        prepareShopSection();
        prepareMyCartSection();
        prepareMyWishlistSection();
        prepareProfileSection();
	}
	
	

	private void prepareSearchSection() {
		// Get search view and activity
	    SearchView searchView = (SearchView) findViewById(R.id.searchview_product_home);
	    
	    // Customize search view
	    int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchPlate = (EditText) searchView.findViewById(searchPlateId);
        searchPlate.setTextColor(getResources().getColor(R.color.grey_light));
        searchPlate.setHintTextColor(getResources().getColor(R.color.grey_light));
        searchPlate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
	    
		// Set the searchable configuration
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    ComponentName cn = new ComponentName(this, ProductListActivity.class);
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));
	    
	    // expand widget by default
	    searchView.setIconifiedByDefault(false);
	}
	
	
	
	private void prepareShopSection() {
		TextView tv = (TextView) findViewById(R.id.currentshop_home);
		Button b = (Button) findViewById(R.id.button_seeshop_home);
		// shop selected?
		LocalStorage storage = LocalStorage.getInstance();
		if (storage.isShopPicked(this)) {
			tv.setText(storage.getShopLocation(this));
			// show shop button
			b.setVisibility(View.VISIBLE);
		} else {
			tv.setText(R.string.nocurrentshopselected_text);
			// hide shop button
			b.setVisibility(View.INVISIBLE);
		}
	}
	
	
	
	private void prepareMyCartSection() {
		// set cart items number
		TextView tv = (TextView) findViewById(R.id.mycart_itemsnumber_home);
		int itemsNumber = LocalStorage.getInstance().getCartItemsCount(this);
		tv.setText(String.valueOf(itemsNumber));
	}
	
	
	
	private void prepareMyWishlistSection() {
		// set wishlist items number
		TextView tv = (TextView) findViewById(R.id.mywishlist_itemsnumber_home);
		int itemsNumber = LocalStorage.getInstance().getWishlistItemsCount(this);
		tv.setText(String.valueOf(itemsNumber));
	}
	
	
	
	private void prepareProfileSection() {
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.section_profile);
		
		// is user loged?
		LocalStorage storage = LocalStorage.getInstance();
		if (storage.isUserLoged(this) && storage.getUser(this) != null) {
			// show section
			rl.setVisibility(View.VISIBLE);
			
			// show user image
			ImageView iv = (ImageView) findViewById(R.id.profile_image_home);
			Uri uri = storage.getProfileImage(this);
            iv.setImageURI(uri);
            
			// set user nick
			TextView tv = (TextView) findViewById(R.id.profile_nick_home);
			tv.setText("@" + storage.getUserNick(this));
			
			// set user points
			tv = (TextView) findViewById(R.id.profile_pointsnumber_home);
			tv.setText(String.valueOf(storage.getUserPoints(this)));
			
			// set user followers
			tv = (TextView) findViewById(R.id.profile_counterfollowers_home);
			int followersCount = storage.getFollowersCount(this);
			tv.setText(String.valueOf(followersCount));
			
			// set user following
			tv = (TextView) findViewById(R.id.profile_counterfollowing_home);
			int followingCount = storage.getFollowingCount(this);
			tv.setText(String.valueOf(followingCount));
			
		} else {
			// hide section
			rl.setVisibility(View.GONE);
		}
	}
	
	
	
	public void userReceived(User user) {
		LocalStorage.getInstance().saveUser(this, user);
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void followersCountReceived(int followersCount) {
		LocalStorage.getInstance().setFollowersCount(this, followersCount);
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void followingCountReceived(int followingCount) {
		LocalStorage.getInstance().setFollowingCount(this, followingCount);
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void zeroCountReceived() {
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void wishlistItemsCountReceived(int wishlistItemsCount) {
		LocalStorage.getInstance().setWishlistItemsCount(this, wishlistItemsCount);
		prepareMyWishlistSection();
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void cartPurchaseReceived(Purchase purchase) {
		// call web service
		UsersController controller = new UsersController(this);
		controller.getPurchaseDetails(purchase.getUser().getUserEmail(), purchase.getIdPurchase());
	}
	
	
	
	public void cartItemsCountReceived(int cartItemsCount) {
		LocalStorage.getInstance().setCartItemsCount(this, cartItemsCount);
		prepareMyCartSection();
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	private synchronized void checkLastRequest() {
		requestsNumber--;
		if (requestsNumber == 0) {
			// last request
			super.prepareResources();
	        prepareProfileSection();
			super.showProgressBar(false);
		}
	}
	
	
	
    @SuppressLint("InlinedApi")
	public void onClickButton(View v) {
		Intent i;
    	
		switch (v.getId()) {
		case R.id.button_pickshop_home:
			// shop selection
			super.displayView(1);
			break;
		
		case R.id.button_searchcategory_home:
			// Search by category
			super.displayView(2);
			break;
			
		case R.id.button_advancedsearch_home:
			// Advanced search
			super.displayView(3);
			break;
			
		case R.id.button_viewmycart_home:
			// see user's cart
			super.displayView(4);
			break;
			
		case R.id.button_viewmywishlist_home:
			// see user's wishlist
			super.displayView(5);
			break;
			
		case R.id.button_seeshop_home:
			// see shop details
			i = new Intent(this, ShopActivity.class);
			int idCurrentShop = LocalStorage.getInstance().getShop(this).getIdShop();
			i.putExtra("idCurrentShop", idCurrentShop);
			i.putExtra("isSelected", true);
			startActivity(i);
			break;
		
		case R.id.profile_image_home:
			// select profile image
			if (Build.VERSION.SDK_INT < 19){
                i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
            } else {
                i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
            }
			i.setType("image/*");
            startActivityForResult(i, SELECT_PICTURE);
			break;
			
		case R.id.button_viewprofile_home:
			// see user's profile
			super.viewProfile();
			break;
			
		case R.id.profile_pointsnumber_home:
			// see user's points
			super.viewUserPoints();
			break;
		
		case R.id.profile_counterfollowing_home:
			// see user's following
			super.viewUserRealationship(false);
			break;
	
		case R.id.profile_counterfollowers_home:
			// see user's followers
			super.viewUserRealationship(true);
			break;
			
		default:
			super.onClickButton(v);
			break;
		}
    }
    
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
            	// Get data from intent
                Uri selectedImageUri = data.getData();
                // Set profile image
                ImageButton ib = (ImageButton) findViewById(R.id.profile_image_home);
                ib.setImageURI(selectedImageUri);
                ImageView iv = (ImageView) findViewById(R.id.profile_image_drawer);
                iv.setImageURI(selectedImageUri);
                // save local
                LocalStorage.getInstance().setProfileImage(this, selectedImageUri);
                // upload image to server
                // TODO
            }
        }
    }
	
	
}