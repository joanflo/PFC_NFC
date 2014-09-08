package com.joanflo.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;

import com.joanflo.network.RESTClient;
import com.joanflo.tagit.R;

public class ColorsController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public ColorsController(Activity activity) {
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
	 * Get color
	 * @param colorCode
	 */
	public void getColor(CharSequence colorCode) {
		client.getColor(this, colorCode);
	}
	
	
}
