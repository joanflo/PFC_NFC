package com.joanflo.network;

import java.net.URI;
import java.net.URISyntaxException;

public class InfoRequest {

	
	private Object controller;
	private HttpMethod method;
	private URI uri;
	
	
	
	public InfoRequest(Object controller, HttpMethod method, String uri) {
		this.controller = controller;
		this.method = method;
		try {
			uri = encodeURIComponent(uri);
			this.uri = new URI(uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static String encodeURIComponent(String uri) {
	    String result;
        result = uri
        		.replace(" ", "%20")
	    		.replace("!", "%21")
	    		.replace("'", "%27")
	    		.replace("(", "%28")
	    		.replace(")", "%29");
	    return result;
	}



	public Object getController() {
		return controller;
	}


	public void setController(Object controller) {
		this.controller = controller;
	}



	public HttpMethod getMethod() {
		return method;
	}


	public void setMethod(HttpMethod method) {
		this.method = method;
	}



	public URI getUri() {
		return uri;
	}
	

	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	
}
