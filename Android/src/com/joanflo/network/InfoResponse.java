package com.joanflo.network;

import org.json.JSONObject;

import android.app.Activity;

public class InfoResponse {

	
	private Activity activity;
	private JSONObject jObject;
	
	
	
	public InfoResponse(Activity activity, JSONObject jObject) {
		this.activity = activity;
		this.jObject = jObject;
	}



	public Activity getActivity() {
		return activity;
	}


	public void setActivity(Activity activity) {
		this.activity = activity;
	}



	public JSONObject getJObject() {
		return jObject;
	}


	public void setJObject(JSONObject jObject) {
		this.jObject = jObject;
	}
	
	
}
