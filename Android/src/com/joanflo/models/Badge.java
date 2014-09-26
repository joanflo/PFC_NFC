package com.joanflo.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Badge model
 * @author Joanflo
 */
public class Badge {

	// primary key
	private CharSequence badgeName;
	
	// badge info
	private CharSequence description;
	
	
	
	/**
	 * Badge model constructor
	 * @param badgeName
	 * @param description
	 */
	public Badge(CharSequence badgeName, CharSequence description) {
		this.badgeName = badgeName;
		this.description = description;
	}
	
	/**
	 * Badge model constructor
	 * @param jBadge
	 * @param lang
	 * @throws JSONException
	 */
	public Badge(JSONObject jBadge, String lang) throws JSONException {
		this.badgeName = jBadge.getString("badgeName");
		this.description = jBadge.getString("description_" + lang);
	}



	public CharSequence getBadgeName() {
		return badgeName;
	}

	
	
	public CharSequence getDescription() {
		return description;
	}

	public void setDescription(CharSequence description) {
		this.description = description;
	}
	
	
}