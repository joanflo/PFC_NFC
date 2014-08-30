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
import android.os.AsyncTask;

public class AsyncRequest extends AsyncTask<InfoRequest, Void, InfoResponse[]> {
	// AsyncTask<Input, Progress, Output>
	
	
	public AsyncResponse delegate = null;
	
	
	public AsyncRequest(AsyncResponse delegate) {
		this.delegate = delegate;
	}
	
	
	
	@Override
	protected InfoResponse[] doInBackground(InfoRequest... infoRequests) {
		// (executed in secondary thread)
		InfoResponse[] infoResponses = new InfoResponse[infoRequests.length];
		// for each URI
		for (int i = 0; i < infoRequests.length; i++) {
			// prepare request
			URI uri = infoRequests[i].getUri();
			HttpRequestBase httpMethod = null;
			switch (infoRequests[i].getMethod()) {
			case POST:
				httpMethod = new HttpPost(uri);
				break;
			case GET:
				httpMethod = new HttpGet(uri);
				break;
			case PUT:
				httpMethod = new HttpPut(uri);
				break;
			case DELETE:
				httpMethod = new HttpDelete(uri);
				break;
			}
			try {
				// execute request
				HttpClient httpClient = new DefaultHttpClient();
				HttpResponse httpResponse = httpClient.execute(httpMethod);
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
				is.close();
				JSONObject jObject = new JSONObject(sb.toString());
				Object controller = infoRequests[i].getController();
				infoResponses[i] = new InfoResponse(controller, jObject);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return infoResponses;
	}
	
	
	
	@Override
	protected void onPostExecute(InfoResponse[] infoResponses) {
		delegate.requestFinished(infoResponses);
	}
	
	

}
