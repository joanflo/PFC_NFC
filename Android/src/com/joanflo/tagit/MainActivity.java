package com.joanflo.tagit;

import java.util.ArrayList;

import com.joanflo.adapters.NavigationDrawerListAdapter;
import com.joanflo.adapters.NavigationDrawerListItem;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	
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
    
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		prepareResources();
		
		// setting the nav drawer list adapter
        adapter = new NavigationDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);
        
        prepareActionBar();
        
        // on first time display view for first nav item
        if (savedInstanceState == null) {
            displayView(0);
        }
        
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
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
	}
	
	
	
	private void loadUserData() {
		// show user image
		ImageView iv = (ImageView) findViewById(R.id.profile_image);
		iv.setImageResource(R.drawable.user_profile);
		
		// set user nick
		TextView tv = (TextView) findViewById(R.id.profile_nick);
		tv.setText("Joan_flo");
		
		// set user points
		tv = (TextView) findViewById(R.id.profile_points_number);
		tv.setText("326");
		
		// set user followers
		tv = (TextView) findViewById(R.id.profile_counter_followers);
		tv.setText("106");
		
		// set user following
		tv = (TextView) findViewById(R.id.profile_counter_following);
		tv.setText("256");
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
	        case R.id.action_settings:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
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
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
    
    
    
    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
        case 0:
            fragment = new HomeFragment();
            break;
        case 1:
            //fragment = new FindPeopleFragment();
            break;
        case 2:
            //fragment = new PhotosFragment();
            break;
        case 3:
            //fragment = new CommunityFragment();
            break;
        case 4:
            //fragment = new PagesFragment();
            break;
        case 5:
            //fragment = new WhatsHotFragment();
            break;
        default:
            break;
        }
 
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
 
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerLinearLayout);
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
    
	
}
