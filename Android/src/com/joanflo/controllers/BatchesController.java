package com.joanflo.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;

import com.joanflo.models.Batch;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.R;

public class BatchesController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public BatchesController(Activity activity) {
		this.activity = activity;
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(String route, int statusCode, JSONObject jObject, JSONArray jArray) {
		/*try {
			
			if (route.equals("")) {
				// 
				
				
			} else if (route.equals("")) {
				// 
				
				
			}
			
			Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_request), Toast.LENGTH_SHORT).show();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
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
