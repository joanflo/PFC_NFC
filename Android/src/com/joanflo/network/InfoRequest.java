package com.joanflo.network;

import java.net.URI;
import java.net.URISyntaxException;

import android.app.Activity;

public class InfoRequest {

	
	private Activity activity;
	private HttpMethod method;
	private URI uri;
	
	
	
	public InfoRequest(Activity activity, HttpMethod method, String uri) {
		this.activity = activity;
		this.method = method;
		try {
			this.uri = new URI(uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}



	public Activity getActivity() {
		return activity;
	}


	public void setActivity(Activity activity) {
		this.activity = activity;
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
