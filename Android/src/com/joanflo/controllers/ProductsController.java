package com.joanflo.controllers;

import org.json.JSONObject;
import com.joanflo.network.RESTClient;

public class ProductsController {

	
	private RESTClient client;
	
	
	public ProductsController() {
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(JSONObject jResponses) {
		// TODO
	}
	
	

	/**
	 * Get products by name
	 * @param queryName
	 */
	public void getProducts(CharSequence queryName) {
		client.getProducts(this, queryName);
	}
	
	
	
	/**
	 * Get products by advanced search
	 * @param queryName
	 * @param priceFrom
	 * @param priceSince
	 * @param coin
	 * @param brandName
	 * @param idCategory
	 * @param rating
	 */
	public void getProducts(CharSequence queryName, float priceFrom, float priceSince, char coin, CharSequence brandName, int idCategory, float rating) {
		client.getProducts(this, queryName, priceFrom, priceSince, coin, brandName, idCategory, rating);
	}
	
	
	
	/**
	 * Get product reviews
	 * @param idProduct
	 */
	public void getReviews(int idProduct) {
		client.getReviews(this, idProduct);
	}
	
	
	
	/**
	 * Get product
	 * @param idProduct
	 */
	public void getProduct(int idProduct) {
		client.getProduct(this, idProduct);
	}	
	
	
	
	/**
	 * Get product images
	 * @param idProduct
	 */
	public void getProductImages(int idProduct) {
		client.getProductImages(this, idProduct);
	}
	
	
	
	/**
	 * Get product's front image
	 * @param idProduct
	 */
	public void getProductFrontImage(int idProduct) {
		client.getProductFrontImage(this, idProduct);
	}
	
	
	
	/**
	 * Get related products
	 * @param idProduct
	 */
	public void getRelatedProducts(int idProduct) {
		client.getRelatedProducts(this, idProduct);
	}
	
	
}
