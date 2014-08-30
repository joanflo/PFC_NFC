package com.joanflo.controllers;

import org.json.JSONObject;
import com.joanflo.network.RESTClient;

public class CollectionsController {

	
	private RESTClient client;
	
	
	public CollectionsController() {
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(JSONObject jResponses) {
		// TODO
	}
	
	
	
	/**
	 * Get collection
	 * @param idCollection
	 */
	public void getCollection(int idCollection) {
		client.getCollection(this, idCollection);
	}
	
	
}
