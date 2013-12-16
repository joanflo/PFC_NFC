package com.joanflo.tagit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class RegistrationActivity extends Activity implements OnItemSelectedListener {

	
	private String[] cities = {"Palma", "Paris", "London"};
	private String[] regions = {"Illes Balears", "Paris", "Barcelona"};
	private String[] countries = {"Espanya", "Françe", "United Kingdom"};
	private String[] languages = {"Català", "French", "English"};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_registration);
		
		fillSpinners();
	}
	
	
	
	
	private void fillSpinners() {
		fillSpinner(R.id.spinner_registration_city, cities);
		fillSpinner(R.id.spinner_registration_region, regions);
		fillSpinner(R.id.spinner_registration_country, countries);
		fillSpinner(R.id.spinner_registration_language, languages);
	}
	
	
	
	private void fillSpinner(int spinnerId, String[] items) {
		Spinner spinner = (Spinner) findViewById(spinnerId);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}




	public void onClickButton(View v) {
		
		switch (v.getId()) {
		case R.id.button_skip_singin:
			// Skip log in
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			finish();
			break;
			
		case R.id.button_singin_singin:
			// Sing in
			
			break;
			
		case R.id.button_terms:
			// See terms and conditions
			Intent intentTerms = new Intent(android.content.Intent.ACTION_VIEW);
			intentTerms.setData(Uri.parse("http://alumnes-ltim.uib.es/~jflorit/terms-conditions.html"));
			startActivity(intentTerms);
			break;
		}
	}



	@Override
	public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
		String name;
		
		TextView tv = (TextView) adapterView.getChildAt(0);
		tv.setTextColor(getResources().getColor(R.color.grey_background));
		
		switch (v.getId()) {
		case R.id.spinner_registration_city:
			name = cities[i];
			break;

		case R.id.spinner_registration_region:
			name = regions[i];
			break;

		case R.id.spinner_registration_country:
			name = countries[i];
			break;

		case R.id.spinner_registration_language:
			name = languages[i];
			break;
		}
	}



	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
		
	}
	
	
	
	public void onCheckboxClicked(View v) {
		
		
	}
	
	
}
