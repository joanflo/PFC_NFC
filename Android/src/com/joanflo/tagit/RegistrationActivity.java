package com.joanflo.tagit;


import java.util.List;
import com.joanflo.controllers.CountriesController;
import com.joanflo.controllers.LanguagesController;
import com.joanflo.controllers.UsersController;
import com.joanflo.models.City;
import com.joanflo.models.Country;
import com.joanflo.models.Language;
import com.joanflo.models.Region;
import com.joanflo.models.User;
import com.joanflo.utils.LocalStorage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends Activity implements OnItemSelectedListener {

	
	private String[] cityNames;
	private String[] regionNames;
	private String[] countryNames;
	private String[] languageNames;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_registration);
		
		// call web service
		CountriesController cController = new CountriesController(this);
		cController.getCountries();
		// call web service
		LanguagesController lController = new LanguagesController(this);
		lController.getLanguages();
		
		fillSpinners();
	}
	
	
	
	private void fillSpinners() {
		// default values
		String[] defaultValue = { getResources().getString(R.string.list_default) };
		fillSpinner(R.id.spinner_registration_city, defaultValue);
		fillSpinner(R.id.spinner_registration_region, defaultValue);
		fillSpinner(R.id.spinner_registration_country, defaultValue);
		fillSpinner(R.id.spinner_registration_language, defaultValue);
	}
	
	
	
	private void fillSpinner(int spinnerId, String[] items) {
		Spinner spinner = (Spinner) findViewById(spinnerId);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}
	
	
	
	private boolean requiredFieldsAreFilled() {
		EditText et;
		Spinner spinner;
		
		// email field
		et = (EditText) findViewById(R.id.editText_registration_email);
		if (et.getText().toString().equals("")) {
			// empty field
			return false;
		}
		
		// nick field
		et = (EditText) findViewById(R.id.editText_registration_nick);
		if (et.getText().toString().equals("")) {
			// empty field
			return false;
		}
		
		// password fields
		et = (EditText) findViewById(R.id.editText_registration_password);
		if (et.getText().toString().equals("")) {
			// empty field
			return false;
		} else {
			CharSequence firstPassword = et.getText().toString();
			et = (EditText) findViewById(R.id.editText_registration_repeatpassword);
			if (!et.getText().toString().equals(firstPassword)) {
				// both passwords must be equals
				return false;
			}
		}
		
		// country field
		spinner = (Spinner) findViewById(R.id.spinner_registration_country);
		if (spinner.getSelectedItemPosition() == 0) {
			// default value not allowed
			return false;
		}
		
		// region field
		spinner = (Spinner) findViewById(R.id.spinner_registration_region);
		if (spinner.getSelectedItemPosition() == 0) {
			// default value not allowed
			return false;
		}
		
		// city field
		spinner = (Spinner) findViewById(R.id.spinner_registration_city);
		if (spinner.getSelectedItemPosition() == 0) {
			// default value not allowed
			return false;
		}
		
		// language field
		spinner = (Spinner) findViewById(R.id.spinner_registration_language);
		if (spinner.getSelectedItemPosition() == 0) {
			// default value not allowed
			return false;
		}
		
		// age field
		et = (EditText) findViewById(R.id.editText_registration_age);
		if (et.getText().toString().equals("")) {
			// empty field
			return false;
		}
		
		// name field
		et = (EditText) findViewById(R.id.editText_registration_name);
		if (et.getText().toString().equals("")) {
			// empty field
			return false;
		}
		
		// surname field
		et = (EditText) findViewById(R.id.editText_registration_surname);
		if (et.getText().toString().equals("")) {
			// empty field
			return false;
		}
		
		// terms and conditions field
		CheckBox cb = (CheckBox) findViewById(R.id.checkBox_termsconditions);
		if (!cb.isChecked()) {
			// policy not accepted
			return false;
		}
		
		return true;
	}
	
	
	
	public void countriesReceived(List<Country> countries) {
		countryNames = new String[countries.size() + 1];
		String defaultValue = getResources().getString(R.string.list_default);
		countryNames[0] = defaultValue;
		for (int i = 0; i < countries.size(); i++) {
			CharSequence countryName = countries.get(i).getCountryName();
			countryNames[i + 1] = (String) countryName;
		}
		fillSpinner(R.id.spinner_registration_country, countryNames);
		fillSpinner(R.id.spinner_registration_city, new String[] {defaultValue});
	}
	
	
	
	public void regionsReceived(List<Region> regions) {
		regionNames = new String[regions.size() + 1];
		regionNames[0] = getResources().getString(R.string.list_default);
		for (int i = 0; i < regions.size(); i++) {
			CharSequence regionName = regions.get(i).getRegionName();
			regionNames[i + 1] = (String) regionName;
		}
		fillSpinner(R.id.spinner_registration_region, regionNames);
	}
	
	
	
	public void citiesReceived(List<City> cities) {
		cityNames = new String[cities.size() + 1];
		cityNames[0] = getResources().getString(R.string.list_default);
		for (int i = 0; i < cities.size(); i++) {
			CharSequence cityName = cities.get(i).getCityName();
			cityNames[i + 1] = (String) cityName;
		}
		fillSpinner(R.id.spinner_registration_city, cityNames);
	}
	
	
	
	public void languagesReceived(List<Language> languages) {
		languageNames = new String[languages.size() + 1];
		languageNames[0] = getResources().getString(R.string.list_default);
		for (int i = 0; i < languages.size(); i++) {
			CharSequence languageName = languages.get(i).getLanguageName();
			languageNames[i + 1] = (String) languageName;
		}
		fillSpinner(R.id.spinner_registration_language, languageNames);
	}
	
	
	
	public void userCreated(User user) {
		showProgressBar(false);
		
		if (user == null) {
			// problem
			Toast.makeText(this, R.string.toast_problem_creatinguser, Toast.LENGTH_SHORT).show();
			
		} else {
			// user created successfuly
			LocalStorage storage = LocalStorage.getInstance();
			storage.setUserLoged(this, true);
			storage.saveUser(this, user);
			goToHomeActivity();
		}
	}
	
	
	
	public void onClickButton(View v) {
		
		switch (v.getId()) {
		case R.id.button_skip_singin:
			// Skip log in
			goToHomeActivity();
			break;
			
		case R.id.button_singin_singin:
			// Sing in
			if (requiredFieldsAreFilled()) {
				EditText et;
				Spinner spinner;
				// get fields
				et = (EditText) findViewById(R.id.editText_registration_email);
				CharSequence userEmail = et.getText().toString();
				spinner = (Spinner) findViewById(R.id.spinner_registration_city);
				CharSequence cityName = spinner.getSelectedItem().toString();
				spinner = (Spinner) findViewById(R.id.spinner_registration_language);
				CharSequence languageName = spinner.getSelectedItem().toString();
				et = (EditText) findViewById(R.id.editText_registration_nick);
				CharSequence nick = et.getText().toString();
				et = (EditText) findViewById(R.id.editText_registration_name);
				CharSequence name = et.getText().toString();
				et = (EditText) findViewById(R.id.editText_registration_surname);
				CharSequence surname = et.getText().toString();
				et = (EditText) findViewById(R.id.editText_registration_age);
				int age = Integer.parseInt(et.getText().toString());
				et = (EditText) findViewById(R.id.editText_registration_password);
				CharSequence password = et.getText().toString();
				et = (EditText) findViewById(R.id.editText_registration_phone);
				CharSequence phone = et.getText().toString();
				et = (EditText) findViewById(R.id.editText_registration_direction);
				CharSequence direction = et.getText().toString();
				// UI
				showProgressBar(true);
				// call web service
				UsersController controller = new UsersController(this);
				controller.signInUser(userEmail, cityName, languageName, nick, name, surname, age, password, phone, direction);
			} else {
				Toast.makeText(this, R.string.toast_requiredfields, Toast.LENGTH_SHORT).show();
			}
			break;
			
		case R.id.button_terms:
			// See terms and conditions
			Intent intentTerms = new Intent(android.content.Intent.ACTION_VIEW);
			intentTerms.setData(Uri.parse("http://alumnes-ltim.uib.es/~jflorit/termsandconditions.html"));
			startActivity(intentTerms);
			break;
		}
	}



	@Override
	public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
		TextView tv = (TextView) adapterView.getChildAt(0);
		tv.setTextColor(getResources().getColor(R.color.grey_background));
		
		if (i != 0) { // to avoid default value
			CountriesController cController = new CountriesController(this);
			Spinner spinner = (Spinner) findViewById(R.id.spinner_registration_country);
			CharSequence countryName = spinner.getSelectedItem().toString();
			switch (adapterView.getId()) {
			case R.id.spinner_registration_region:
				// call web service to load cities from selected region
				spinner = (Spinner) findViewById(R.id.spinner_registration_region);
				CharSequence regionName = spinner.getSelectedItem().toString();
				cController.getCities(countryName, regionName);
				break;
	
			case R.id.spinner_registration_country:
				// call web service to load regions from selected country
				cController.getRegions(countryName);
				break;
			}
		}
	}



	@Override
	public void onNothingSelected(AdapterView<?> adapterView) { }
	
	
	
	private void showProgressBar(boolean show) {
		ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar_registration);
		ScrollView sv = (ScrollView) findViewById(R.id.aux3);
		Button buttonSignIn = (Button) findViewById(R.id.button_singin_singin);
		// show progress bar?
		if (show) {
			spinner.setVisibility(View.VISIBLE);
			// hide scroll view
			sv.setVisibility(View.GONE);
			// disable sign in button
			buttonSignIn.setEnabled(false);
		} else {
			spinner.setVisibility(View.GONE);
			// show scroll view
			sv.setVisibility(View.VISIBLE);
			// enable sign in button
			buttonSignIn.setEnabled(true);
		}
		
	}
	
	
	
	private void goToHomeActivity() {
		Intent iMain = new Intent(this, HomeActivity.class);
		startActivity(iMain);
		finish();
	}
	
	
}
