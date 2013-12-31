package com.joanflo.tagit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


public class ProductActivity extends BaseActivity {
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		
        // update the main content by replacing view
		LayoutInflater factory = LayoutInflater.from(this);
		View activityView = factory.inflate(R.layout.activity_product, null);
		// inflate activity layout
        FrameLayout viewContainer = (FrameLayout) findViewById(R.id.frame_container);
        viewContainer.addView(activityView);
        
        
        
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