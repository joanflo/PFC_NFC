package com.joanflo.network;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class Request extends AsyncTask<Void, Void, Void> {

	
	public static final CharSequence HOST = "http://alumnes-ltim.uib.es/~jflorit/";
	
	
	
	@Override
	protected Void doInBackground(Void... params) {
		HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        
        
        
		return null;
	}
	
	
	

}
