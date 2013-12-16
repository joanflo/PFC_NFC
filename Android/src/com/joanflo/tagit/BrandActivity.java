package com.joanflo.tagit;

//import com.joanflo.tagit.MainActivity.SlideMenuClickListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;


public class BrandActivity extends BaseActivity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// inflate brand activity layout
		LayoutInflater factory = LayoutInflater.from(this);
		FrameLayout viewContainer = (FrameLayout) findViewById(R.id.frame_container);
		View brandActivity = factory.inflate(R.layout.activity_brand, null);
		viewContainer.addView(brandActivity);
		
		ListView mDrawerList = super.getNavigationDrawerList();
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	
	/**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // display view for selected nav drawer item
        	
            //TODO
        }
    }
	
	
}
