package com.joanflo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import com.joanflo.models.Language;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.RegistrationActivity;

public class LanguagesController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public LanguagesController(Activity activity) {
		this.activity = activity;
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(JSONObject jObject) {
		try {
			
			if (activity instanceof RegistrationActivity) {
				RegistrationActivity registrationActivity = (RegistrationActivity) activity;
				
				// for each language JSON object
				JSONArray jArray = jObject.getJSONArray("languages");
				List<Language> languages = new ArrayList<Language>();
				for (int i = 0; i < jArray.length(); i++) {
					// create Language model from JSON object
					JSONObject jLanguage = (JSONObject) jArray.get(i);
					Language language = new Language(jLanguage);
					languages.add(language);
				}
				registrationActivity.languagesReceived(languages);
				
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Get languages
	 */
	public void getLanguages() {
		client.getLanguages(this);
	}
	
	
}
