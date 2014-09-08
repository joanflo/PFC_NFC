package com.joanflo.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;

import com.joanflo.network.RESTClient;
import com.joanflo.tagit.R;

public class CollectionsController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public CollectionsController(Activity activity) {
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
	 * Get collection
	 * @param idCollection
	 */
	public void getCollection(int idCollection) {
		client.getCollection(this, idCollection);
	}
	
	
}
