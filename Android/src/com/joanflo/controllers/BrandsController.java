package com.joanflo.controllers;

import org.json.JSONObject;
import com.joanflo.network.RESTClient;

public class BrandsController {

	
	private RESTClient client;
	
	
	public BrandsController() {
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(JSONObject jResponses) {
		// TODO
	}
	
	
	
	/**
	 * Get brand
	 * @param brandName
	 */
	public void getBrand(CharSequence brandName) {
		client.getBrand(this, brandName);
	}
	
}
