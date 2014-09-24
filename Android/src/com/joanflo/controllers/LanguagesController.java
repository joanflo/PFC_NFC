package com.joanflo.controllers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.Toast;
import com.joanflo.models.Language;
import com.joanflo.network.HttpStatusCode;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.R;
import com.joanflo.tagit.RegistrationActivity;
import com.joanflo.tagit.UpdateUserDataActivity;

public class LanguagesController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public LanguagesController(Activity activity) {
		this.activity = activity;
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(String route, int statusCode, JSONObject jObject, JSONArray jArray) {
		try {
			if (statusCode == HttpStatusCode.REQUEST_TIMEOUT) {
				throw new Exception();
			}
			
			if (route.equals("languages")) {
				// GET <URLbase>/languages
				if (jArray != null) {
					// list of languages
					List<Language> languages = processLanguages(jArray);
					
					if (activity instanceof RegistrationActivity) {
						RegistrationActivity registrationActivity = (RegistrationActivity) activity;
						registrationActivity.languagesReceived(languages);
					} else if (activity instanceof UpdateUserDataActivity) {
						UpdateUserDataActivity updateUserDataActivity = (UpdateUserDataActivity) activity;
						updateUserDataActivity.languagesReceived(languages);
					}
				}
			}
			
		} catch (Exception e) {
			if (!RESTClient.isOnline(activity)) {
				Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_internetconnection), Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_request), Toast.LENGTH_SHORT).show();
			}
			activity.finish();
		}
	}
	
	
	
	/**
	 * Get languages
	 */
	public void getLanguages() {
		// GET <URLbase>/languages
		client.getLanguages(this);
	}
	
	
	
	private List<Language> processLanguages(JSONArray jLanguages) throws JSONException {
		List<Language> languages = new ArrayList<Language>(jLanguages.length());
		
		// for each language JSON object
		for (int i = 0; i < jLanguages.length(); i++) {
			// create Language model from JSON object
			JSONObject jLanguage = jLanguages.getJSONObject(i);
			Language language = new Language(jLanguage);
			languages.add(language);
		}
		
		return languages;
	}
	
	
}
