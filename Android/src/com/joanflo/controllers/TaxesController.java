package com.joanflo.controllers;

import org.json.JSONObject;
import com.joanflo.network.RESTClient;

public class TaxesController {

	
	private RESTClient client;
	
	
	public TaxesController() {
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(JSONObject jResponses) {
		// TODO
	}
	
	
	
	/**
	 * Get tax
	 * @param idProduct
	 * @param countryName
	 */
	public void getTax(int idProduct, CharSequence countryName) {
		 client.getTax(this, idProduct, countryName);
	}
	
	
}
