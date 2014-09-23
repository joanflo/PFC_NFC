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
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


public class UpdateUserDataActivity extends BaseActivity implements OnItemSelectedListener {

	
	private String[] cityNames;
	private String[] regionNames;
	private String[] countryNames;
	private String[] languageNames;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_updateuserdata);
		super.setTitle(R.string.title_userdata);
		
        prepareInfoSection();
        
		// call web service
		CountriesController cController = new CountriesController(this);
		cController.getCountries();
		// call web service
		LanguagesController lController = new LanguagesController(this);
		lController.getLanguages();
	}
	
	
	
	private boolean requiredFieldsAreFilled() {
		EditText et;
		Spinner spinner;
		
		// city field
		spinner = (Spinner) findViewById(R.id.spinner_updateuserdata_city);
		String defaultValue = getResources().getString(R.string.list_default);
		String selectedItem = spinner.getSelectedItem().toString();
		if (defaultValue.equals(selectedItem)) {
			// default value not allowed
			return false;
		}
		
		// language field
		spinner = (Spinner) findViewById(R.id.spinner_updateuserdata_language);
		selectedItem = spinner.getSelectedItem().toString();
		if (defaultValue.equals(selectedItem)) {
			// default value not allowed
			return false;
		}
		
		// age field
		et = (EditText) findViewById(R.id.editText_updateuserdata_age);
		if (et.getText().toString().equals("")) {
			// empty field
			return false;
		}
		
		// name field
		et = (EditText) findViewById(R.id.editText_updateuserdata_name);
		if (et.getText().toString().equals("")) {
			// empty field
			return false;
		}
		
		// surname field
		et = (EditText) findViewById(R.id.editText_updateuserdata_surname);
		if (et.getText().toString().equals("")) {
			// empty field
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
		fillSpinner(R.id.spinner_updateuserdata_country, countryNames);
	}
	
	
	
	public void regionsReceived(List<Region> regions) {
		regionNames = new String[regions.size() + 1];
		regionNames[0] = getResources().getString(R.string.list_default);
		for (int i = 0; i < regions.size(); i++) {
			CharSequence regionName = regions.get(i).getRegionName();
			regionNames[i + 1] = (String) regionName;
		}
		fillSpinner(R.id.spinner_updateuserdata_region, regionNames);
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void citiesReceived(List<City> cities) {
		cityNames = new String[cities.size() + 1];
		cityNames[0] = getResources().getString(R.string.list_default);
		for (int i = 0; i < cities.size(); i++) {
			CharSequence cityName = cities.get(i).getCityName();
			cityNames[i + 1] = (String) cityName;
		}
		fillSpinner(R.id.spinner_updateuserdata_city, cityNames);
		// select current city
		Spinner spinner = (Spinner) findViewById(R.id.spinner_updateuserdata_city);
		ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
		City city = LocalStorage.getInstance().getUser(this).getCity();
		int position = adapter.getPosition(city.getCityName());
		if (position == - 1) {
			position = 0;
		}
		spinner.setSelection(position);
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void languagesReceived(List<Language> languages) {
		languageNames = new String[languages.size() + 1];
		languageNames[0] = getResources().getString(R.string.list_default);
		for (int i = 0; i < languages.size(); i++) {
			CharSequence languageName = languages.get(i).getLanguageName();
			languageNames[i + 1] = (String) languageName;
		}
		fillSpinner(R.id.spinner_updateuserdata_language, languageNames);
		// select current language
		Spinner spinner = (Spinner) findViewById(R.id.spinner_updateuserdata_language);
		ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
		Language language = LocalStorage.getInstance().getUser(this).getLanguage();
		int position = adapter.getPosition(language.getLanguageName());
		spinner.setSelection(position);
	}
	
	
	
	public void userUpdated(User user) {
		super.showProgressBar(false);
		
		if (user == null) {
			// problem
			Toast.makeText(this, R.string.toast_problem_updatinguser, Toast.LENGTH_SHORT).show();
			
		} else {
			// user updated successfully
			Toast.makeText(this, R.string.toast_userupdated, Toast.LENGTH_LONG).show();
			LocalStorage storage = LocalStorage.getInstance();
			storage.saveUser(this, user);
			finish();
		}
	}
	
	
	
	private void prepareInfoSection() {
		User user = LocalStorage.getInstance().getUser(this);
		TextView tv;
		EditText et;
		
		// email
		tv = (TextView) findViewById(R.id.textView_updateuserdata_email);
		tv.setText(user.getUserEmail());
		
		// age
		et = (EditText) findViewById(R.id.editText_updateuserdata_age);
		et.setText(String.valueOf(user.getAge()));
		
		// name
		et = (EditText) findViewById(R.id.editText_updateuserdata_name);
		et.setText(user.getName());
		
		// surname
		et = (EditText) findViewById(R.id.editText_updateuserdata_surname);
		et.setText(user.getSurname());
		
		// phone
		et = (EditText) findViewById(R.id.editText_updateuserdata_phone);
		if (!user.getPhone().equals("null")) {
			et.setText(user.getPhone());
		}
		
		// direction
		et = (EditText) findViewById(R.id.editText_updateuserdata_direction);
		if (!user.getDirection().equals("null")) {
			et.setText(user.getDirection());
		}
		
        fillSpinners(user.getCity(), user.getLanguage());
	}
	
	
	
	private void fillSpinners(City city, Language language) {
		// default values
		String[] defaultValue = { getResources().getString(R.string.list_default) };
		// saved values
		String[] cityValue = { (String) city.getCityName() };
		String[] languageValue = { (String) language.getLanguageName() };
		fillSpinner(R.id.spinner_updateuserdata_city, cityValue);
		fillSpinner(R.id.spinner_updateuserdata_region, defaultValue);
		fillSpinner(R.id.spinner_updateuserdata_country, defaultValue);
		fillSpinner(R.id.spinner_updateuserdata_language, languageValue);
	}
	
	
	
	private void fillSpinner(int spinnerId, String[] items) {
		Spinner spinner = (Spinner) findViewById(spinnerId);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}



	public void onClickButton(View v) {
    	
		switch (v.getId()) {
		case R.id.button_updateuserdata_update:
			// update user data
			if (requiredFieldsAreFilled()) {
				User user = LocalStorage.getInstance().getUser(this);
				EditText et;
				Spinner spinner;
				
				// email
				CharSequence userEmail = user.getUserEmail();
				// nick
				CharSequence nick = user.getNick();
				// city
				spinner = (Spinner) findViewById(R.id.spinner_updateuserdata_city);
				CharSequence cityName = spinner.getSelectedItem().toString();
				// language
				spinner = (Spinner) findViewById(R.id.spinner_updateuserdata_language);
				CharSequence languageName = spinner.getSelectedItem().toString();
				// name
				et = (EditText) findViewById(R.id.editText_updateuserdata_name);
				CharSequence name = et.getText().toString();
				// surname
				et = (EditText) findViewById(R.id.editText_updateuserdata_surname);
				CharSequence surname = et.getText().toString();
				// age
				et = (EditText) findViewById(R.id.editText_updateuserdata_age);
				int age = Integer.parseInt(et.getText().toString());
				// phone
				et = (EditText) findViewById(R.id.editText_updateuserdata_phone);
				CharSequence phone = et.getText().toString();
				// direction
				et = (EditText) findViewById(R.id.editText_updateuserdata_direction);
				CharSequence direction = et.getText().toString();
				
				// create user
				City city = new City(cityName);
				Language language = new Language(languageName);
				User userAux = new User(userEmail, city, language, nick, name, surname, age, "", phone, direction);
				
				// UI
				super.showProgressBar(true);
				
				// call web service
				UsersController controller = new UsersController(this);
				controller.updateUserData(userAux);
				
			} else {
				Toast.makeText(this, R.string.toast_requiredfields3, Toast.LENGTH_SHORT).show();
			}
			break;
		
		default:
			super.onClickButton(v);
			break;
		}
    }



	@Override
	public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
		TextView tv = (TextView) adapterView.getChildAt(0);
		
		if (i != 0 && tv != null) { // to avoid default value
			tv.setTextColor(getResources().getColor(R.color.grey_background));
			CountriesController cController = new CountriesController(this);
			Spinner spinner = (Spinner) findViewById(R.id.spinner_updateuserdata_country);
			CharSequence countryName = spinner.getSelectedItem().toString();
			switch (adapterView.getId()) {
			case R.id.spinner_updateuserdata_region:
				// call web service to load cities from selected region
				spinner = (Spinner) findViewById(R.id.spinner_updateuserdata_region);
				CharSequence regionName = spinner.getSelectedItem().toString();
				cController.getCities(countryName, regionName);
				break;
	
			case R.id.spinner_updateuserdata_country:
				// call web service to load regions from selected country
				cController.getRegions(countryName);
				break;
			}
		}
	}



	@Override
	public void onNothingSelected(AdapterView<?> arg0) { }
	
	
}