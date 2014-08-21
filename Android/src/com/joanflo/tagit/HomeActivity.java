package com.joanflo.tagit;

import org.json.JSONObject;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;


public class HomeActivity extends BaseActivity {


	
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
		
		prepareSearchSection();
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
		
		boolean currentShopSelected = true;
		// current shop info
		TextView tv = (TextView) findViewById(R.id.currentshop_home);
		// see shop button
		Button b = (Button) findViewById(R.id.button_seeshop_home);
		if (currentShopSelected) {
			tv.setText("4, Aragó St.\nPalma 07008");
			b.setVisibility(View.VISIBLE);
		} else {
			tv.setText(R.string.nocurrentshopselected_text);
			b.setVisibility(View.INVISIBLE);
		}
	}
	
	
	
	private void prepareMyCartSection() {
		
		// set cart items number
		TextView tv = (TextView) findViewById(R.id.mycart_itemsnumber_home);
		tv.setText("22");
	}
	
	
	
	private void prepareMyWishlistSection() {

		// set wishlist items number
		TextView tv = (TextView) findViewById(R.id.mywishlist_itemsnumber_home);
		tv.setText("50+");
	}
	
	
	
	private void prepareProfileSection() {
		
		// show user image
		ImageView iv = (ImageView) findViewById(R.id.profile_image_home);
		iv.setImageResource(R.drawable.user_profile);
		
		// set user nick
		TextView tv = (TextView) findViewById(R.id.profile_nick_home);
		tv.setText("@" + "Joan_flo");
		
		// set user points
		tv = (TextView) findViewById(R.id.profile_pointsnumber_home);
		tv.setText("326");
		
		// set user followers
		tv = (TextView) findViewById(R.id.profile_counterfollowers_home);
		tv.setText("106");
		
		// set user following
		tv = (TextView) findViewById(R.id.profile_counterfollowing_home);
		tv.setText("256");
	}
	
	
	
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
			startActivity(i);
			break;
		
		case R.id.profile_image_home:
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
	
	
	
	public synchronized void requestFinished(JSONObject jResponses) {
		// TODO
	}
	
	
}