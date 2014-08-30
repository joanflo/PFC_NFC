package com.joanflo.controllers;

import org.json.JSONObject;
import com.joanflo.network.RESTClient;

public class CategoriesController {

	
	private RESTClient client;
	
	
	public CategoriesController() {
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(JSONObject jResponses) {
		// TODO
	}
	
	

	/**
	 * Get categories
	 * (includes subcategories/products count for each category)
	 * @param level
	 */
	public void getCategories(int level) {
		client.getCategories(this, level);
	}
	
	
	
	/**
	 * Get subcategories
	 * (includes sub-subcategories/products for each category)
	 * @param idCategory
	 */
	public void getSubategories(int idCategory) {
		client.getSubategories(this, idCategory);
	}
	
	
}
