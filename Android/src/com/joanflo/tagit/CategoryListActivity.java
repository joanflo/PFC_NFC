package com.joanflo.tagit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


public class CategoryListActivity extends BaseActivity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

        // update the main content by replacing view
		LayoutInflater factory = LayoutInflater.from(this);
		View activityView = factory.inflate(R.layout.activity_categorylist, null);
		// inflate activity layout
        FrameLayout viewContainer = (FrameLayout) findViewById(R.id.frame_container);
        viewContainer.addView(activityView);
        
        // update selected item and title, then close the drawer
        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("drawerPosition");
        super.updateSelected(pos);
	}

	
	
	
	
	public void onClickButton(View v) {
		Intent i;
    	
		switch (v.getId()) {
		default:
			super.onClickButton(v);
			break;
		}
    }
	
	
}
