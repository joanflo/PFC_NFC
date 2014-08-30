package com.joanflo.controllers;

import org.json.JSONObject;
import com.joanflo.network.RESTClient;

public class BadgesController {

	
	private RESTClient client;
	
	
	public BadgesController() {
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(JSONObject jResponses) {
		// TODO
	}
	
	
	
	/**
	 * Get all database badges
	 */
	public void getBadges() {
		client.getBadges(this);
	}
	
	
	
	/**
	 * Get a single badge
	 * @param badgeName
	 */
	public void getBadge(CharSequence badgeName) {
		client.getBadge(this, badgeName);
	}
	
	
}
