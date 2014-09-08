package com.joanflo.models;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class City {


	// primary key
	private CharSequence cityName;
	
	// foreign key
	private List<Shop> shops;
	private Region region;
	
	
	
	public City(CharSequence cityName, List<Shop> shops, Region region) {
		this.cityName = cityName;
		this.shops = shops;
		this.region = region;
	}
	
	public City(CharSequence cityName) {
		this.cityName = cityName;
		this.shops = null;
		this.region = null;
	}
	
	public City(JSONObject jCities) throws JSONException {
		this.cityName = jCities.getString("cityName");
		this.shops = null;
		this.region = null;
	}
	
	
	
	public CharSequence getCityName() {
		return cityName;
	}
	
	
	public List<Shop> getShops() {
		return shops;
	}
	
	public void addShop(Shop shop) {
		shops.add(shop);
	}
	
	
	public Region getRegion() {
		return region;
	}
	
	
	
	public JSONObject convertToJSON() {
		JSONObject jCity = new JSONObject();
		
		try {
			jCity.put("cityName", cityName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jCity;
	}
	
	
}