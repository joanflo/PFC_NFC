package com.joanflo.network;

import java.net.URI;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Encapsulates the info necessary to handle responses from the server
 * @author Joanflo
 */
public class InfoResponse {

	
	private Object controller;
	private String route;
	private int statusCode;
	private JSONObject jObject;
	private JSONArray jArray;
	
	
	
	/**
	 * Constructor used when the server returns a JSON object.
	 * @param controller
	 * @param uri
	 * @param statusCode
	 * @param jObject
	 */
	public InfoResponse(Object controller, URI uri, int statusCode, JSONObject jObject) {
		this.controller = controller;
		this.route = getRoute(uri);
		this.statusCode = statusCode;
		this.jObject = jObject;
		this.jArray = null;
	}
	
	/**
	 * Constructor used when the server returns a JSON array.
	 * @param controller
	 * @param uri
	 * @param statusCode
	 * @param jArray
	 */
	public InfoResponse(Object controller, URI uri, int statusCode, JSONArray jArray) {
		this.controller = controller;
		this.route = getRoute(uri);
		this.statusCode = statusCode;
		this.jObject = null;
		this.jArray = jArray;
	}
	
	/**
	 * Constructor used when the server returns a void response or an error has occurred.
	 * @param controller
	 * @param uri
	 * @param statusCode
	 */
	public InfoResponse(Object controller, URI uri, int statusCode) {
		this.controller = controller;
		this.route = getRoute(uri);
		this.statusCode = statusCode;
		this.jObject = null;
		this.jArray = null;
	}



	public Object getController() {
		return controller;
	}

	public void setController(Object controller) {
		this.controller = controller;
	}



	public String getRoute() {
		return route;
	}

	private String getRoute(URI uri) {
		String strUri = uri.toString();
		// remove query params?
		int index = strUri.indexOf('?');
		if (index != -1) {
			strUri = strUri.substring(0, index);
		}
		// encode
		strUri = strUri
				.replace(RESTClient.HOST, "")
				.replace("%20", " ")
				.replace("%21", "!")
				.replace("%27", "'")
				.replace("%28", "(")
				.replace("%29", ")");
		return strUri;
	}

	public void setRoute(String route) {
		this.route = route;
	}



	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}



	public JSONObject getJObject() {
		return jObject;
	}

	public void setJObject(JSONObject jObject) {
		this.jObject = jObject;
	}



	public JSONArray getJArray() {
		return jArray;
	}

	public void setJArray(JSONArray jArray) {
		this.jArray = jArray;
	}
	
	
}
