package com.joanflo.tagit;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

public class MainActivity extends BaseActivity {
	
    private Fragment currentFragment;
    
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().show();
		
		// on first time display view for first nav item
        if (savedInstanceState == null) {
            displayView(0);
        }
        
        ListView mDrawerList = super.getNavigationDrawerList();
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        currentFragment = null;
        switch (position) {
        case 0:
        	currentFragment = new HomeFragment();
            break;
        case 1:
            currentFragment = new ShopSelectionFragment();
            break;
        case 2:
            currentFragment = new CategoryListFragment();
            break;
        case 3:
            currentFragment = new ProductSearchFragment();
            break;
        case 4:
            currentFragment = new PurchaseDetailListFragment();
            break;
        case 5:
            currentFragment = new WishListFragment();
            break;
        default:
            break;
        }
 
        if (currentFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, currentFragment).commit();
 
            // update selected item and title, then close the drawer
            super.updateSelected(position);
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
    
    
    
    public void onClickButton(View v) {
    	// delegating event to the corresponding fragment
    	
    	if (currentFragment instanceof HomeFragment) {
    		HomeFragment fragment = (HomeFragment) currentFragment;
    		fragment.onClickButton(v);
    		
    	} else if (currentFragment instanceof ShopSelectionFragment) {
    		ShopSelectionFragment fragment = (ShopSelectionFragment) currentFragment;
    		fragment.onClickButton(v);
    		
    	} else if (currentFragment instanceof CategoryListFragment) {
    		CategoryListFragment fragment = (CategoryListFragment) currentFragment;
    		fragment.onClickButton(v);
    		
    	} else if (currentFragment instanceof ProductSearchFragment) {
    		ProductSearchFragment fragment = (ProductSearchFragment) currentFragment;
    		fragment.onClickButton(v);
    		
    	} else if (currentFragment instanceof PurchaseDetailListFragment) {
    		PurchaseDetailListFragment fragment = (PurchaseDetailListFragment) currentFragment;
    		fragment.onClickButton(v);
    		
    	} else if (currentFragment instanceof WishListFragment) {
    		WishListFragment fragment = (WishListFragment) currentFragment;
    		fragment.onClickButton(v);
    	}
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
