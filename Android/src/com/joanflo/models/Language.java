package com.joanflo.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Language model
 * @author Joanflo
 */
public class Language {

	
	// primary key
	private CharSequence languageName;
	
	
	
	/**
	 * Language model constructor
	 * @param languageName
	 */
	public Language(CharSequence languageName) {
		this.languageName = languageName;
	}
	
	/**
	 * Language model constructor
	 * @param jLanguage
	 * @throws JSONException
	 */
	public Language(JSONObject jLanguage) throws JSONException {
		this.languageName = jLanguage.getString("languageName");
	}
	
	
	
	public CharSequence getLanguageName() {
		return languageName;
	}
	
	
	
	/**
	 * Convert Language model JSON model
	 * @return
	 */
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