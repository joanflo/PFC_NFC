package com.joanflo.tagit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class ProductSearchActivity extends BaseActivity implements OnItemSelectedListener {

	
	private String[] brands = {"Quicksilver", "Vans", "Zara"};
	private String[] categories = {"Shorts", "T-Shirts", "Trousers"};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// update the main content by replacing view
		LayoutInflater factory = LayoutInflater.from(this);
		View activityView = factory.inflate(R.layout.activity_productsearch, null);
		// inflate activity layout
        FrameLayout viewContainer = (FrameLayout) findViewById(R.id.frame_container);
        viewContainer.addView(activityView);
        
        // update selected item and title, then close the drawer
        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("drawerPosition");
        super.updateSelected(pos);
		        
		prepareSearchSection();
	}



	private void prepareSearchSection() {
		TextView et;
		String coin = "€";
		
		// price from
		et = (TextView) findViewById(R.id.from_coin);
		et.setText(coin);
		
		// price until
		et = (TextView) findViewById(R.id.until_coin);
		et.setText(coin);
		
		// brand and category spinners
		fillSpinner(R.id.spinner_productsearch_brand, brands);
		fillSpinner(R.id.spinner_productsearch_category, categories);
	}



	private void fillSpinner(int spinnerId, String[] items) {
		Spinner spinner = (Spinner) findViewById(spinnerId);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}



	public void onClickButton(View v) {
		// search button clicked
	}



	@Override
	public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
		String name;
		
		TextView tv = (TextView) adapterView.getChildAt(0);
		tv.setTextColor(getResources().getColor(R.color.grey_background));
		
		switch (v.getId()) {
		case R.id.spinner_productsearch_brand:
			name = brands[i];
			break;

		case R.id.spinner_productsearch_category:
			name = categories[i];
			break;
		}
	}



	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

}
