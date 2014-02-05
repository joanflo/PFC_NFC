package com.joanflo.tagit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class ProductSearchActivity extends BaseActivity implements OnItemSelectedListener {

	
	private String[] brands = {"Quicksilver", "Vans", "Zara"};
	private String[] categories = {"Shorts", "T-Shirts", "Trousers"};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_productsearch);
		
        
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
		Intent i;
    	
		switch (v.getId()) {
		// case: search button clicked
		
		default:
			super.onClickButton(v);
			break;
		}
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
