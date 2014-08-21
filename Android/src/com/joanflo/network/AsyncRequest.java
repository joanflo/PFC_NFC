package com.joanflo.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncRequest extends AsyncTask<InfoRequest, Void, InfoResponse[]> {
	// AsyncTask<Input, Progress, Output>
	
	
	public AsyncResponse delegate = null;
	
	
	public AsyncRequest(AsyncResponse delegate) {
		Log.i("test", "constructor");
		this.delegate = delegate;
	}
	
	
	
	@Override
	protected InfoResponse[] doInBackground(InfoRequest... infoRequests) {
		// (executed in secondary thread)
		InfoResponse[] infoResponses = new InfoResponse[infoRequests.length];
		Log.i("test", "a");
		// for each URI
		for (int i = 0; i < infoRequests.length; i++) {
			Log.i("test", "b");
			// prepare request
			URI uri = infoRequests[i].getUri();
			HttpRequestBase httpMethod = null;
			switch (infoRequests[i].getMethod()) {
			case POST:
				httpMethod = new HttpPost(uri);
				break;
			case GET:
				httpMethod = new HttpGet(uri);Log.i("test", "c");
				break;
			case PUT:
				httpMethod = new HttpPut(uri);
				break;
			case DELETE:
				httpMethod = new HttpDelete(uri);
				break;
			}
			Log.i("test", "d");
			try {
				// execute request
				Log.i("test", "d1");
				HttpClient httpClient = new DefaultHttpClient();
				Log.i("test", "d2");
				HttpResponse httpResponse = httpClient.execute(httpMethod);
				Log.i("test", "e");
				// convert response to JSON
				HttpEntity entity = httpResponse.getEntity();
				InputStream is = entity.getContent();
				InputStreamReader isr = new InputStreamReader(is, "iso-8859-1");
				BufferedReader reader = new BufferedReader(isr, 8);
				StringBuilder sb = new StringBuilder();
				sb.append(reader.readLine() + "\n");
				String line = "0";
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();Log.i("test", "f");
				JSONObject jObject = new JSONObject(sb.toString());
				Activity activity = infoRequests[i].getActivity();
				infoResponses[i] = new InfoResponse(activity, jObject);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		Log.i("test", "g");
		return infoResponses;
	}
	
	
	
	@Override
	protected void onPostExecute(InfoResponse[] infoResponses) {
		Log.i("test", "h");
		delegate.requestFinished(infoResponses);
	}
	
	

}
