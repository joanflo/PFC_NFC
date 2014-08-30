package com.joanflo.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Language {

	
	// primary key
	private CharSequence languageName;
	
	
	
	public Language(CharSequence languageName) {
		this.languageName = languageName;
	}
	
	public Language(JSONObject jLanguage) throws JSONException {
		this.languageName = jLanguage.getString("languageName");
	}
	
	
	
	public CharSequence getLanguageName() {
		return languageName;
	}
	
	
	
	public JSONObject convertToJSON() {
		JSONObject jLanguage = new JSONObject();
		
		try {
			jLanguage.put("languageName", languageName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jLanguage;
	}
	
	
}