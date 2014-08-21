package com.joanflo.tagit;

import com.joanflo.models.City;
import com.joanflo.models.Country;
import com.joanflo.models.Language;
import com.joanflo.models.Region;
import com.joanflo.models.User;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;


public class UpdateUserDataActivity extends BaseActivity implements OnItemSelectedListener {

	
	private String[] cities = {"Palma", "Paris", "London"};
	private String[] regions = {"Illes Balears", "Paris", "Barcelona"};
	private String[] countries = {"Espanya", "Françe", "United Kingdom"};
	private String[] languages = {"Català", "French", "English"};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_updateuserdata);
		
        
        prepareInfoSection();
	}
	
	
	
	private void prepareInfoSection() {
		Country espanya = new Country("Espanya", null, 34, Country.EURO);
		Region balears = new Region("Balears", null, espanya);
		City palma = new City("Palma", null, balears);
		Language catala = new Language("Català");
		User user = new User("joan@uib.cat", palma, catala, "Joan_flo", "Joan", "Florit Gomila", 23, "password", "686922414", "Sant Vicenç Ferrer 117");
		TextView tv;
		EditText et;
		
		tv = (TextView) findViewById(R.id.textView_updateuserdata_email);
		tv.setText(user.getUserEmail());
		
		et = (EditText) findViewById(R.id.editText_updateuserdata_age);
		et.setText(String.valueOf(user.getAge()));
		
		et = (EditText) findViewById(R.id.editText_updateuserdata_name);
		et.setText(user.getName());
		
		et = (EditText) findViewById(R.id.editText_updateuserdata_surname);
		et.setText(user.getSurname());
		
		
		
		et = (EditText) findViewById(R.id.editText_updateuserdata_phone);
		if (!user.getPhone().equals("-")) {
			et.setText(user.getPhone());
		}
		
		et = (EditText) findViewById(R.id.editText_updateuserdata_direction);
		if (!user.getDirection().equals("-")) {
			et.setText(user.getDirection());
		}
		
		fillSpinners();
	}
	
	
	
	private void fillSpinners() {
		fillSpinner(R.id.spinner_updateuserdata_city, cities);
		fillSpinner(R.id.spinner_updateuserdata_region, regions);
		fillSpinner(R.id.spinner_updateuserdata_country, countries);
		fillSpinner(R.id.spinner_updateuserdata_language, languages);
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
		case R.id.button_updateuserdata_update:
			// update user data
			
			break;
		
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
		case R.id.spinner_updateuserdata_city:
			name = cities[i];
			break;

		case R.id.spinner_updateuserdata_region:
			name = regions[i];
			break;

		case R.id.spinner_updateuserdata_country:
			name = countries[i];
			break;

		case R.id.spinner_updateuserdata_language:
			name = languages[i];
			break;
		}
	}



	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public void onCheckboxClicked(View v) {
		
		
	}
	
	
}