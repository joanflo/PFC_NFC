package com.joanflo.controllers;

import org.json.JSONObject;
import com.joanflo.models.Batch;
import com.joanflo.network.RESTClient;

public class BatchesController {

	
	private RESTClient client;
	
	
	public BatchesController() {
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(JSONObject jResponses) {
		// TODO
	}
	
	
	
	/**
	 * Get batches from product id
	 * @param idProduct
	 */
	public void getBatches(int idProduct) {
		client.getBatches(this, idProduct);
	}
	
	
	
	/**
	 * Get batches from product id & shop id
	 * @param idProduct
	 * @param idShop
	 */
	public void getBatches(int idProduct, int idShop) {
		client.getBatches(this, idProduct, idShop);
	}
	

	
	/**
	 * Get batch
	 * @param idBatch
	 */
	public void getBatch(int idBatch) {
		client.getBatch(this, idBatch);
	}
	
	
	
	/**
	 * Update batch
	 * @param batch
	 */
	public void updateBatch(Batch batch) {
		client.updateBatch(this, batch);
	}
	
	
}
