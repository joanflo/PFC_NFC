package com.joanflo.controllers;

import org.json.JSONObject;
import com.joanflo.network.RESTClient;

public class ShopsController {

	
	private RESTClient client;
	
	
	public ShopsController() {
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(JSONObject jResponses) {
		// TODO
	}
	
	
	
	/**
	 * Get all the shops in the city
	 * @param cityName
	 */
	public void getShops(CharSequence cityName) {
		client.getShops(this, cityName);
	}
	
	
	
	/**
	 * Get the nearest shops from current location
	 * @param latitude
	 * @param longitude
	 */
	public void getShops(double latitude, double longitude) {
		client.getShops(this, latitude, longitude);
	}
	
	
	
	/**
	 * Get shop
	 * @param idShop
	 */
	public void getShop(int idShop) {
		client.getShop(this, idShop);
	}
	
	
}
