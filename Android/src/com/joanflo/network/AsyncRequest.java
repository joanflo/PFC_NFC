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
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;

/**
 * AsyncTask to execute requests to the server
 * @author Joanflo
 */
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
				// set the connection timeout value to 10 seconds
				HttpClient httpClient = new DefaultHttpClient();
				final HttpParams httpParams = httpClient.getParams();
				HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
				HttpConnectionParams.setSoTimeout(httpParams, 5000);
				
				// execute request
				HttpResponse httpResponse = httpClient.execute(httpMethod);
				
				// get status code
				JSONObject jObject = null;
				JSONArray jArray = null;
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				if (statusCode == HttpStatusCode.OK
					|| statusCode == HttpStatusCode.CREATED) {
					
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
					
					// check JSON response type
					String jString = sb.toString();
					if (jString.charAt(0) == '[') {
						// JSON array
						jArray = new JSONArray(jString);
					} else { // jString.charAt(0) == '{'
						// JSON object
						jObject = new JSONObject(jString);
					}
					
				}
				// else: BAD_REQUEST, NOT_FOUND, INTERNAL_SERVER_ERROR, NO_CONTENT
				
				// encapsulate response
				Object controller = infoRequests[i].getController();
				if (jArray != null) {
					// JSON array
					infoResponses[i] = new InfoResponse(controller, uri, statusCode, jArray);
				} else if (jObject != null) {
					// JSON object
					infoResponses[i] = new InfoResponse(controller, uri, statusCode, jObject);
				} else {
					// error or delete
					infoResponses[i] = new InfoResponse(controller, uri, statusCode);
				}
				
			} catch (IOException | JSONException e) {
				// error
				Object controller = infoRequests[i].getController();
				infoResponses[i] = new InfoResponse(controller, uri, HttpStatusCode.REQUEST_TIMEOUT);
			}
		}
		return infoResponses;
	}
	
	
	
	@Override
	protected void onPostExecute(InfoResponse[] infoResponses) {
		delegate.requestFinished(infoResponses);
	}
	
	

}
