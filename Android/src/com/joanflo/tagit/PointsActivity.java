package com.joanflo.tagit;

import java.sql.Timestamp;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class PointsActivity extends BaseActivity {


	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// update the main content by replacing view
		LayoutInflater factory = LayoutInflater.from(this);
		View activityView = factory.inflate(R.layout.activity_points, null);
		// inflate activity layout
        FrameLayout viewContainer = (FrameLayout) findViewById(R.id.frame_container);
        viewContainer.addView(activityView);
        
        preparePointsSection();
	}
	
	
	
	private void preparePointsSection() {
		String[] descriptions = getResources().getStringArray(R.array.points_descriptions);
		int[] idsDescriptions = {R.id.textView_points1_txtdescription, R.id.textView_points2_txtdescription, 
								 R.id.textView_points3_txtdescription, R.id.textView_points4_txtdescription, 
								 R.id.textView_points5_txtdescription, R.id.textView_points6_txtdescription};
		
		for (int i = 0; i < descriptions.length; i++) {
			TextView tv = (TextView) findViewById(idsDescriptions[i]);
			tv.setText(descriptions[i]);
		}
	}



	public void onClickButton(View v) {
    	
		switch (v.getId()) {
		default:
			super.onClickButton(v);
			break;
		}
    }

	
}