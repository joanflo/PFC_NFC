package com.joanflo.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;

import com.joanflo.models.Color;
import com.joanflo.models.Size;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.R;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.Regex;

public class ColorsController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public ColorsController(Activity activity) {
		this.activity = activity;
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(String route, int statusCode, JSONObject jObject, JSONArray jArray) {
		try {
			
			if (route.matches("colors/" + Regex.SPECIAL_TEXT)) {
				// GET <URLbase>/colors/{colorCode}
				if (jObject != null) {
					// get language code
					String lang = LocalStorage.getInstance().getLocaleLanguage(activity);
					// color
					Color color = new Color(jObject, lang);
					
					// TODO
				}
			}
			
		} catch (JSONException e) {
			Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_request), Toast.LENGTH_SHORT).show();
		}
	}
	
	
	
	/**
	 * Get color
	 * @param colorCode
	 */
	public void getColor(CharSequence colorCode) {
		client.getColor(this, colorCode);
	}
	
	
}
