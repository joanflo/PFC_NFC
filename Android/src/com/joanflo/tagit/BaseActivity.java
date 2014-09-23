package com.joanflo.tagit;

import java.util.ArrayList;
import com.joanflo.adapters.NavigationDrawerListAdapter;
import com.joanflo.adapters.NavigationDrawerListItem;
import com.joanflo.controllers.UsersController;
import com.joanflo.models.Achievement;
import com.joanflo.models.Badge;
import com.joanflo.models.User;
import com.joanflo.utils.Gamification;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.Time;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    protected static final int PROFILE = -1;
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
	
	
	
	protected void setFrameContainerView(int idView) {
		// update the main content by replacing view
		LayoutInflater factory = LayoutInflater.from(this);
		View activityView = factory.inflate(idView, null);
		// inflate activity layout
        FrameLayout viewContainer = (FrameLayout) findViewById(R.id.frame_container);
        viewContainer.addView(activityView);
	}
	
	
	
	protected void prepareResources() {
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
		int itemsNumber = LocalStorage.getInstance().getCartItemsCount(this);
        navDrawerItems.add(new NavigationDrawerListItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1), true, String.valueOf(itemsNumber)));
        // My wishlist
		itemsNumber = LocalStorage.getInstance().getWishlistItemsCount(this);
        navDrawerItems.add(new NavigationDrawerListItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, String.valueOf(itemsNumber)));
	
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
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.profile_view);
		// is user loged?
		LocalStorage storage = LocalStorage.getInstance();
		if (storage.isUserLoged(this) && storage.getUser(this) != null) {
			ImageView iv;
			TextView tv;
			// show user image
			iv = (ImageView) findViewById(R.id.profile_image_drawer);
			Uri uri = storage.getProfileImage(this);
            iv.setImageURI(uri);
			// set user nick
			tv = (TextView) findViewById(R.id.profile_nick_drawer);
			tv.setText("@" + storage.getUserNick(this));
			// set user points
			tv = (TextView) findViewById(R.id.profile_pointsnumber_drawer);
			tv.setText(String.valueOf(storage.getUserPoints(this)));
			// set user followers
			tv = (TextView) findViewById(R.id.profile_counterfollowers_drawer);
			int followersCount = storage.getFollowersCount(this);
			tv.setText(String.valueOf(followersCount));
			// set user following
			tv = (TextView) findViewById(R.id.profile_counterfollowing_drawer);
			int followingCount = storage.getFollowingCount(this);
			tv.setText(String.valueOf(followingCount));
			// show user profile layout
			rl.setVisibility(View.VISIBLE);
			
		} else {
			// hide user profile layout
			rl.setVisibility(View.GONE);
		}
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
    
    
    
    protected void showProgressBar(boolean show) {
    	View v = findViewById(R.id.drawer_layout);
    	ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar_base);
    	FrameLayout fl = (FrameLayout) findViewById(R.id.frame_container);
		// show progress bar?
		if (show) {
			spinner.setVisibility(View.VISIBLE);
			// show background image
			v.setBackgroundResource(R.drawable.background_base);
			// hide frame container
			fl.setVisibility(View.INVISIBLE);
		} else {
			spinner.setVisibility(View.GONE);
			// hide background image
			v.setBackgroundColor(Color.TRANSPARENT);
			// show frame container
			fl.setVisibility(View.VISIBLE);
		}
    }
    
    
    
    public void displayView(int position) {
    	
    	if (currentPosition == position) {
    		// close navigation drawer
    		updateSelected(position);
    		
    	} else {
    		LocalStorage storage = LocalStorage.getInstance();
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
	        	if (storage.isUserLoged(this)) {
	        		intent = new Intent(this, PurchaseDetailListActivity.class);
	        	} else {
	        		Toast.makeText(this, R.string.toast_registration, Toast.LENGTH_SHORT).show();
	        		intent = new Intent(this, RegistrationActivity.class);
	        	}
	            break;
	        case 5:
	        	if (storage.isUserLoged(this)) {
	        		intent = new Intent(this, WishListActivity.class);
	        	} else {
	        		Toast.makeText(this, R.string.toast_registration, Toast.LENGTH_SHORT).show();
	        		intent = new Intent(this, RegistrationActivity.class);
	        	}
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
        CharSequence userEmail = LocalStorage.getInstance().getUserEmail(this);
        i.putExtra("userEmail", userEmail);
		startActivity(i);
    }
    
    
    
    protected void createAchievement(int event) {
    	LocalStorage storage = LocalStorage.getInstance();
    	if (storage.isUserLoged(this)) {
	    	// creacte achievement
	    	Badge badge = Gamification.getBadge(event);
	    	User user = storage.getUser(this);
	    	Achievement achievement = new Achievement(badge, user);
	    	// call web service
	    	UsersController controller = new UsersController(this);
	    	controller.createAchievement(achievement);
    	}
    }
    
    
    
    public void achievementCreated(Achievement achievement) {
		// get data
		Badge badge = achievement.getBadge();
		CharSequence badgeName = badge.getBadgeName();
		CharSequence description = badge.getDescription();
		CharSequence date = Time.convertTimestampToString(achievement.getDate());
		// show toast
		Gamification.showToastBadge(this, badgeName, description, date);
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
