package com.joanflo.controllers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.Toast;
import com.joanflo.models.City;
import com.joanflo.models.Country;
import com.joanflo.models.Region;
import com.joanflo.network.HttpStatusCode;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.R;
import com.joanflo.tagit.RegistrationActivity;
import com.joanflo.tagit.ShopSelectionActivity;
import com.joanflo.tagit.UpdateUserDataActivity;
import com.joanflo.utils.Regex;

public class CountriesController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public CountriesController(Activity activity) {
		this.activity = activity;
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(String route, int statusCode, JSONObject jObject, JSONArray jArray) {
		try {
			if (statusCode == HttpStatusCode.REQUEST_TIMEOUT) {
				throw new Exception();
			}
			
			if (route.matches("countries")) {
				// GET <URLbase>/countries
				if (jArray != null) {
					List<Country> countries = processCountries(jArray);
					
					if (activity instanceof RegistrationActivity) {
						RegistrationActivity registrationActivity = (RegistrationActivity) activity;
						registrationActivity.countriesReceived(countries);
					} else if (activity instanceof ShopSelectionActivity) {
						ShopSelectionActivity shopSelectionActivity = (ShopSelectionActivity) activity;
						shopSelectionActivity.countriesReceived(countries);
					} else if (activity instanceof UpdateUserDataActivity) {
						UpdateUserDataActivity updateUserDataActivity = (UpdateUserDataActivity) activity;
						updateUserDataActivity.countriesReceived(countries);
					}
				}
				// GET <URLbase>/countries?cityName={cityName}
				if (jObject != null) {
					// Country country = new Country(jObject);
					
					// TODO
				}
				
			} else if (route.matches("countries/" + Regex.TEXT + "/regions")) {
				// GET <URLbase>/countries/{countryName}/regions
				List<Region> regions = processRegions(jArray);
				
				if (activity instanceof RegistrationActivity) {
					RegistrationActivity registrationActivity = (RegistrationActivity) activity;
					registrationActivity.regionsReceived(regions);
				} else if (activity instanceof ShopSelectionActivity) {
					ShopSelectionActivity shopSelectionActivity = (ShopSelectionActivity) activity;
					shopSelectionActivity.regionsReceived(regions);
				} else if (activity instanceof UpdateUserDataActivity) {
					UpdateUserDataActivity updateUserDataActivity = (UpdateUserDataActivity) activity;
					updateUserDataActivity.regionsReceived(regions);
				}
				
			} else if (route.matches("countries/" + Regex.TEXT + "/regions/" + Regex.TEXT + "/cities")) {
				// GET <URLbase>/countries/{countryName}/regions/{regionName}/cities
				List<City> cities = processCities(jArray);
				
				if (activity instanceof RegistrationActivity) {
					RegistrationActivity registrationActivity = (RegistrationActivity) activity;
					registrationActivity.citiesReceived(cities);
				} else if (activity instanceof ShopSelectionActivity) {
					ShopSelectionActivity shopSelectionActivity = (ShopSelectionActivity) activity;
					shopSelectionActivity.citiesReceived(cities);
				} else if (activity instanceof UpdateUserDataActivity) {
					UpdateUserDataActivity updateUserDataActivity = (UpdateUserDataActivity) activity;
					updateUserDataActivity.citiesReceived(cities);
				}
			}
			
		} catch (Exception e) {
			if (!RESTClient.isOnline(activity)) {
				Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_internetconnection), Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_request), Toast.LENGTH_SHORT).show();
			}
			activity.finish();
		}
	}
	
	
	
	/**
	 * Get countries
	 */
	public void getCountries() {
		// GET <URLbase>/countries
		client.getCountries(this);
	}
	
	
	
	/**
	 * Get country
	 * @param cityName
	 */
	public void getCountry(CharSequence cityName) {
		// GET <URLbase>/countries?cityName={cityName}
		client.getCountry(this, cityName);
	}
	
	
	
	/**
	 * Get regions
	 * @param countryName
	 */
	public void getRegions(CharSequence countryName) {
		// GET <URLbase>/countries/{countryName}/regions
		client.getRegions(this, countryName);
	}
	
	
	
	/**
	 * Get cities
	 * @param countryName
	 * @param regionName
	 */
	public void getCities(CharSequence countryName, CharSequence regionName) {
		// GET <URLbase>/countries/{countryName}/regions/{regionName}/cities
		client.getCities(this, countryName, regionName);
	}
	
	
	
	private List<Country> processCountries(JSONArray jCountries) throws JSONException {
		List<Country> countries = new ArrayList<Country>(jCountries.length());
		
		// for each country JSON object
		for (int i = 0; i < jCountries.length(); i++) {
			// create Country model from JSON object
			JSONObject jCountry = jCountries.getJSONObject(i);
			Country country = new Country(jCountry);
			countries.add(country);
		}
		
		return countries;
	}
	
	
	
	private List<Region> processRegions(JSONArray jRegions) throws JSONException {
		List<Region> regions = new ArrayList<Region>(jRegions.length());
		
		// for each region JSON object
		for (int i = 0; i < jRegions.length(); i++) {
			// create Region model from JSON object
			JSONObject jRegion = jRegions.getJSONObject(i);
			Region region = new Region(jRegion);
			regions.add(region);
		}
		
		return regions;
	}
	
	
	
	private List<City> processCities(JSONArray jCities) throws JSONException {
		List<City> cities = new ArrayList<City>(jCities.length());
		
		// for each city JSON object
		for (int i = 0; i < jCities.length(); i++) {
			// create City model from JSON object
			JSONObject jCity = jCities.getJSONObject(i);
			City city = new City(jCity);
			cities.add(city);
		}
		
		return cities;
	}
	
	
}
