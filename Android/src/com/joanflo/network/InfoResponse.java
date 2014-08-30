package com.joanflo.network;

import org.json.JSONObject;

public class InfoResponse {

	
	private Object controller;
	private JSONObject jObject;
	
	
	
	public InfoResponse(Object controller, JSONObject jObject) {
		this.controller = controller;
		this.jObject = jObject;
	}



	public Object getController() {
		return controller;
	}


	public void setController(Object controller) {
		this.controller = controller;
	}



	public JSONObject getJObject() {
		return jObject;
	}


	public void setJObject(JSONObject jObject) {
		this.jObject = jObject;
	}
	
	
}
