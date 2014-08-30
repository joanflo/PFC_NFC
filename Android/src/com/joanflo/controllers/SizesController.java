package com.joanflo.controllers;

import org.json.JSONObject;
import com.joanflo.network.RESTClient;

public class SizesController {

	
	private RESTClient client;
	
	
	public SizesController() {
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(JSONObject jResponses) {
		// TODO
	}
	

	
	/**
	 * Get size
	 * @param idSize
	 */
	public void getSize(int idSize) {
		client.getSize(this, idSize);
	}
	
}
