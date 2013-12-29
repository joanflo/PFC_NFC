package com.joanflo.tagit;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;


public class BrandActivity extends BaseActivity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// inflate brand activity layout
		LayoutInflater factory = LayoutInflater.from(this);
		FrameLayout viewContainer = (FrameLayout) findViewById(R.id.frame_container);
		View brandActivity = factory.inflate(R.layout.activity_brand, null);
		viewContainer.addView(brandActivity);
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
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
