package com.joanflo.controllers;

import org.json.JSONObject;
import com.joanflo.network.RESTClient;

public class ColorsController {

	
	private RESTClient client;
	
	
	public ColorsController() {
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(JSONObject jResponses) {
		// TODO
	}
	
	
	
	/**
	 * Get color
	 * @param colorCode
	 */
	public void getColor(CharSequence colorCode) {
		client.getColor(this, colorCode);
	}
	
	
}
