package com.joanflo.webservice;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;

public class WebserviceHTTP extends AsyncTask<Void, Void, Void> {

	private JSONArray jArray;
	private String result;
	private StringBuilder sb;
	private InputStream is;
	
	
	public WebserviceHTTP() {
		jArray = null;
		result = null;
		sb = null;
		is = null;
	}
	
	
	public String callWebservice() {
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		//http post
		try{
			HttpClient httpclient = new DefaultHttpClient();
			
			//Why to use 10.0.2.2
			HttpPost httppost = new HttpPost("http://10.0.2.2/pfcWebservice/webservice.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		}catch(Exception e){
			Log.e("log_tag", "Error in http connection "+e.toString());
		}

		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			sb = new StringBuilder();
			sb.append(reader.readLine() + "\n");
			
			String line="0";
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
		}catch(Exception e){
			Log.e("log_tag", "Error converting result "+e.toString());
		}
		
		String name = "";
		try{
			jArray = new JSONArray(result);
			JSONObject json_data=null;
			for(int i=0;i<jArray.length();i++){
				json_data = jArray.getJSONObject(i);
				name = json_data.getString("languageName");
			}
		} catch(JSONException e1){
			e1.printStackTrace();
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		
		return name;
	}


	@Override
	protected Void doInBackground(Void... unused) {
		String ret = callWebservice();
		Log.i("JOANFLO", ret);
		return null;
	}
	
	
	
	
}