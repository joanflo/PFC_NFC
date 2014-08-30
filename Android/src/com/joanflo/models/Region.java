package com.joanflo.models;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Region {

	
	// primary key
	private CharSequence regionName;
	
	// foreign keys
	private List<City> cities;
	private Country country;
	
	
	
	public Region(CharSequence regionName, List<City> cities, Country country) {
		this.regionName = regionName;
		this.cities = cities;
		this.country = country;
	}
	
	public Region(JSONObject jRegions) throws JSONException {
		this.regionName = jRegions.getString("regionName");
		this.cities = null;
		this.country = null;
	}
	
	
	
	public CharSequence getRegionName() {
		return regionName;
	}
	
	
	public List<City> getCities() {
		return cities;
	}
	
	public void addCity(City city) {
		cities.add(city);
	}
	
	
	public Country getCountry() {
		return country;
	}
	
	
}