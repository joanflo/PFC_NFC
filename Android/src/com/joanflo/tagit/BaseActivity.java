package com.joanflo.tagit;

import java.util.ArrayList;
import com.joanflo.adapters.NavigationDrawerListAdapter;
import com.joanflo.adapters.NavigationDrawerListItem;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class BaseActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private LinearLayout mDrawerLinearLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    
    // nav drawer title
    private CharSequence mDrawerTitle;
    
	// used to store app title
    private CharSequence mTitle;
 
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
 
    private ArrayList<NavigationDrawerListItem> navDrawerItems;
    private NavigationDrawerListAdapter adapter;
    
    private static final int NO_CURRENT_POSITION = -2;
    private static final int PROFILE = -1;
    // 0..5 ListView item selected
    private int currentPosition = NO_CURRENT_POSITION;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		
		prepareResources();
		
		// setting the nav drawer list adapter
        adapter = new NavigationDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);
        // setting the nav drawer click listener
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        
        prepareActionBar();
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        
        // Handle action bar actions click
        switch (item.getItemId()) {
        case R.id.action_help:
            return true;
            
        case R.id.action_settings:
            return true;
            
        case R.id.action_logout:
            return true;
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	
	
	private void prepareResources() {
		loadUserData();
		
		// load app title
		mTitle = mDrawerTitle = getTitle();
		// load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        // nav drawer icons from resources
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
		
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLinearLayout = (LinearLayout) findViewById(R.id.drawer_view);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        
        // adding nav drawer items to array
        navDrawerItems = new ArrayList<NavigationDrawerListItem>();
        // Home
        navDrawerItems.add(new NavigationDrawerListItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Current shop
        navDrawerItems.add(new NavigationDrawerListItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Categories
        navDrawerItems.add(new NavigationDrawerListItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Search products
        navDrawerItems.add(new NavigationDrawerListItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // My cart
        navDrawerItems.add(new NavigationDrawerListItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1), true, "22"));
        // My wishlist
        navDrawerItems.add(new NavigationDrawerListItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));
	
        // Recycle the typed array
        navMenuIcons.recycle();
	}
	
	
	
	private void prepareActionBar() {
		// enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        // action bar toggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        getActionBar().show();
	}
	
	
	
	private void loadUserData() {
		// show user image
		ImageView iv = (ImageView) findViewById(R.id.profile_image_drawer);
		iv.setImageResource(R.drawable.user_profile);
		
		// set user nick
		TextView tv = (TextView) findViewById(R.id.profile_nick_drawer);
		tv.setText("@" + "Joan_flo");
		
		// set user points
		tv = (TextView) findViewById(R.id.profile_pointsnumber_drawer);
		tv.setText("326");
		
		// set user followers
		tv = (TextView) findViewById(R.id.profile_counterfollowers_drawer);
		tv.setText("106");
		
		// set user following
		tv = (TextView) findViewById(R.id.profile_counterfollowing_drawer);
		tv.setText("256");
	}
	
	
	
	/**
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	// if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLinearLayout);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    
    
    
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    
    
    
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    
    
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    
    
    /**
     * update selected item and title, then close the drawer
     */
    protected void updateSelected(int position) {
    	currentPosition = position;
    	
    	ImageButton ib = (ImageButton) findViewById(R.id.profile_image_drawer);
    	
    	if (position == PROFILE) {
    		ib.setBackgroundResource(R.drawable.image_bg_selected);
    		String txt = getResources().getString(R.string.title_profile);
    		setTitle(txt);
    		
    	} else {
    		ib.setBackgroundResource(R.drawable.image_bg_normal);
	    	mDrawerList.setItemChecked(position, true);
	        mDrawerList.setSelection(position);
	        mDrawerLayout.closeDrawer(mDrawerLinearLayout);
	        setTitle(navMenuTitles[position]);
    	}
    	
    }
    
    
    
    public void displayView(int position) {
    	
    	if (currentPosition == position) {
    		// close navigation drawer
    		updateSelected(position);
    		
    	} else {
	        Intent intent;
	        switch (position) {
	        case 0:
	        	intent = new Intent(this, HomeActivity.class);
	            break;
	        case 1:
	        	intent = new Intent(this, ShopSelectionActivity.class);
	            break;
	        case 2:
	        	intent = new Intent(this, CategoryListActivity.class);
	            break;
	        case 3:
	        	intent = new Intent(this, ProductSearchActivity.class);
	            break;
	        case 4:
	        	intent = new Intent(this, PurchaseDetailListActivity.class);
	            break;
	        case 5:
	        	intent = new Intent(this, WishListActivity.class);
	            break;
	        default:
	        	// home activity by default
	        	intent = new Intent(this, HomeActivity.class);
	            break;
	        }
	        currentPosition = mDrawerList.getCheckedItemPosition();
	        intent.putExtra("drawerPosition", position);
			startActivity(intent);
    	}
    	
    }
    
    
    
    protected void onClickButton(View v) {

		switch (v.getId()) {
		case R.id.profile_image_drawer:
			// see user's following
			viewProfile();
			break;
			
		case R.id.profile_pointsnumber_drawer:
			// see user's points
			viewUserPoints();
			break;
			
		case R.id.profile_counterfollowing_drawer:
			// see user's following
			viewUserRealationship(false);
			break;
	
		case R.id.profile_counterfollowers_drawer:
			// see user's followers
			viewUserRealationship(true);
			break;
		}
    }
    
    
    
    protected void viewProfile() {
    	if (currentPosition != PROFILE) {
			Intent i = new Intent(this, UserProfileActivity.class);
			currentPosition = PROFILE;
	        i.putExtra("drawerPosition", currentPosition);
			startActivity(i);
    	} else {
    		mDrawerLayout.closeDrawer(mDrawerLinearLayout);
    	}
    }
    
    
    
    protected void viewUserPoints() {
    	Intent i = new Intent(this, PointsActivity.class);
    	startActivity(i);
    }
    
    
    
    protected void viewUserRealationship(boolean seeFollowers) {
		Intent i = new Intent(this, FollowsListActivity.class);
        i.putExtra("seeFollowers", seeFollowers);
        i.putExtra("userEmail", "prova@prova.com");
		startActivity(i);
    }
    
    
    
    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements ListView.OnItemClickListener {
    	
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
	
	
}
