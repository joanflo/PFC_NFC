package com.joanflo.tagit;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

public class HomeFragment extends Fragment {

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        
        prepareSearchSection(rootView);
        prepareShopSection(rootView);
        prepareMyCartSection(rootView);
        prepareMyWishlistSection(rootView);
        prepareProfileSection(rootView);
        
        return rootView;
    }
	
	

	private void prepareSearchSection(View fragment) {
		// Get the SearchView and set the searchable configuration
	    SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) fragment.findViewById(R.id.searchview_product_home);
	    ComponentName cn = new ComponentName(getActivity(), ProductListActivity.class);
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));
	    
	    // expand widget by default
	    searchView.setIconifiedByDefault(false);
	}
	
	
	
	private void prepareShopSection(View fragment) {
		
		boolean currentShopSelected = true;
		// current shop info
		TextView tv = (TextView) fragment.findViewById(R.id.currentshop_home);
		// see shop button
		Button b = (Button) fragment.findViewById(R.id.button_seeshop_home);
		if (currentShopSelected) {
			tv.setText("4, Aragó St.\nPalma 07008");
			b.setVisibility(View.VISIBLE);
		} else {
			tv.setText(R.string.nocurrentshopselected_text);
			b.setVisibility(View.INVISIBLE);
		}
	}
	
	
	
	private void prepareMyCartSection(View fragment) {
		
		// set cart items number
		TextView tv = (TextView) fragment.findViewById(R.id.mycart_itemsnumber_home);
		tv.setText("22");
	}
	
	
	
	private void prepareMyWishlistSection(View fragment) {

		// set wishlist items number
		TextView tv = (TextView) fragment.findViewById(R.id.mywishlist_itemsnumber_home);
		tv.setText("50+");
	}
	
	
	
	private void prepareProfileSection(View fragment) {
		
		// show user image
		ImageView iv = (ImageView) fragment.findViewById(R.id.profile_image_home);
		iv.setImageResource(R.drawable.user_profile);
		
		// set user nick
		TextView tv = (TextView) fragment.findViewById(R.id.profile_nick_home);
		tv.setText("Joan_flo");
		
		// set user points
		tv = (TextView) fragment.findViewById(R.id.profile_pointsnumber_home);
		tv.setText("326");
		
		// set user followers
		tv = (TextView) fragment.findViewById(R.id.profile_counterfollowers_home);
		tv.setText("106");
		
		// set user following
		tv = (TextView) fragment.findViewById(R.id.profile_counterfollowing_home);
		tv.setText("256");
	}
	
	
	
    public void onClickButton(View v) {
		
		switch (v.getId()) {
		case R.id.button_searchcategory_home:
			// Search by category
			break;
			
		case R.id.button_advancedsearch_home:
			// Advanced search
			break;
			
		case R.id.button_seeshop_home:
			// see shop details
			break;
			
		case R.id.button_pickshop_home:
			// shop selection
			break;
			
		case R.id.button_viewmycart_home:
			// see user's cart
			break;
			
		case R.id.button_viewmywishlist_home:
			// see user's wishlist
			break;
			
		case R.id.button_viewprofile_home:
			// see user's profile
			break;
		}
		
		//ep! fer un onClick a followers i following
    }
	
	
}