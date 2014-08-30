package com.joanflo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import com.joanflo.models.City;
import com.joanflo.models.Country;
import com.joanflo.models.Language;
import com.joanflo.models.Region;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.LoginActivity;
import com.joanflo.tagit.RegistrationActivity;

public class CountriesController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public CountriesController(Activity activity) {
		this.activity = activity;
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(JSONObject jObject) {
		try {
			
		
			if (activity instanceof RegistrationActivity) {
				RegistrationActivity registrationActivity = (RegistrationActivity) activity;
				
				// identify request
				JSONArray jArray;
				if (jObject.has("countries")) {
					// array of countries
					jArray = jObject.getJSONArray("countries");
					List<Country> countries = new ArrayList<Country>();
					for (int i = 0; i < jArray.length(); i++) {
						// create Country model from JSON object
						JSONObject jCountry = (JSONObject) jArray.get(i);
						Country country = new Country(jCountry);
						countries.add(country);
					}
					registrationActivity.countriesReceived(countries);
					
				} else if(jObject.has("regions")) {
					// array of regions
					jArray = jObject.getJSONArray("regions");
					List<Region> regions = new ArrayList<Region>();
					for (int i = 0; i < jArray.length(); i++) {
						// create Region model from JSON object
						JSONObject jRegion = (JSONObject) jArray.get(i);
						Region region = new Region(jRegion);
						regions.add(region);
					}
					registrationActivity.regionsReceived(regions);
					
				} else if(jObject.has("cities")) {
					// array of cities
					jArray = jObject.getJSONArray("cities");
					List<City> cities = new ArrayList<City>();
					for (int i = 0; i < jArray.length(); i++) {
						// create City model from JSON object
						JSONObject jCity = (JSONObject) jArray.get(i);
						City city = new City(jCity);
						cities.add(city);
					}
					registrationActivity.citiesReceived(cities);
					
				}
				
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Get countries
	 */
	public void getCountries() {
		client.getCountries(this);
	}
	
	
	
	/**
	 * Get country
	 * @param cityName
	 */
	public void getCountry(CharSequence cityName) {
		client.getCountry(this, cityName);
	}
	
	
	
	/**
	 * Get regions
	 * @param countryName
	 */
	public void getRegions(CharSequence countryName) {
		client.getRegions(this, countryName);
	}
	
	
	
	/**
	 * Get cities
	 * @param countryName
	 * @param regionName
	 */
	public void getCities(CharSequence countryName, CharSequence regionName) {
		client.getCities(this, countryName, regionName);
	}
	
	
}
