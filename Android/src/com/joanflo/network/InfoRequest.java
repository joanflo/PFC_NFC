package com.joanflo.network;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Encapsulates the info necessary to perform requests to the server
 * @author Joanflo
 */
public class InfoRequest {

	
	private Object controller;
	private HttpMethod method;
	private URI uri;
	
	
	
	/**
	 * Constructor
	 * @param controller
	 * @param method
	 * @param uri
	 */
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
	
	
	
	/**
	 * Encodes special characters from the given URL string
	 * @param uri
	 * @return
	 */
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
