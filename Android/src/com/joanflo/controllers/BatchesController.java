package com.joanflo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;

import com.joanflo.models.Batch;
import com.joanflo.models.Shop;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.ProductActivity;
import com.joanflo.tagit.R;
import com.joanflo.utils.Regex;

public class BatchesController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public BatchesController(Activity activity) {
		this.activity = activity;
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(String route, int statusCode, JSONObject jObject, JSONArray jArray) {
		try {
			
			if (route.matches("batches")) {
				// GET <URLbase>/batches?idProduct={idProduct}
				// GET <URLbase>/batches?idProduct={idProduct}&idShop={idShop}
				if (jArray != null) {
					// list of batches
					List<Batch> batches = processBatches(jArray);
					
					if (activity instanceof ProductActivity) {
						ProductActivity productActivity = (ProductActivity) activity;
						productActivity.batchesReceived(batches);
					}
				}
				
			} else if (route.matches("batches/" + Regex.INTEGER)) {
				// GET <URLbase>/batches/{idBatch}
				// PUT <URLbase>/batches/{idBatch}?units={units}
				if (jObject != null) {
					// batch
					Batch batch = new Batch(jObject);
					
					// TODO
				}
			}
			
		} catch (JSONException e) {
			Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_request), Toast.LENGTH_SHORT).show();
		}
	}
	
	
	
	/**
	 * Get batches from product id
	 * @param idProduct
	 */
	public void getBatches(int idProduct) {
		// GET <URLbase>/batches?idProduct={idProduct}
		client.getBatches(this, idProduct);
	}
	
	
	
	/**
	 * Get batches from product id & shop id
	 * @param idProduct
	 * @param idShop
	 */
	public void getBatches(int idProduct, int idShop) {
		// GET <URLbase>/batches?idProduct={idProduct}&idShop={idShop}
		client.getBatches(this, idProduct, idShop);
	}
	

	
	/**
	 * Get batch
	 * @param idBatch
	 */
	public void getBatch(int idBatch) {
		// GET <URLbase>/batches/{idBatch}
		client.getBatch(this, idBatch);
	}
	
	
	
	/**
	 * Update batch
	 * @param batch
	 */
	public void updateBatch(Batch batch) {
		// PUT <URLbase>/batches/{idBatch}?units={units}
		client.updateBatch(this, batch);
	}
	
	
	
	private List<Batch> processBatches(JSONArray jBatches) throws JSONException {
		List<Batch> batches = new ArrayList<Batch>(jBatches.length());
		
		// for each batch JSON object
		for (int i = 0; i < jBatches.length(); i++) {
			// create Batch model from JSON object
			JSONObject jBatch = jBatches.getJSONObject(i);
			Batch batch = new Batch(jBatch);
			batches.add(batch);
		}
		
		return batches;
	}
	
	
}
