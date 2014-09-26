package com.joanflo.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Color model
 * @author Joanflo
 */
public class Color {

	
	// primary key
	private CharSequence colorCode;
	
	// color info
	private CharSequence name;
	
	
	
	/**
	 * Color model constructor
	 * @param colorCode
	 * @param name
	 */
	public Color(CharSequence colorCode, CharSequence name) {
		this.colorCode = colorCode;
		this.name = name;
	}
	
	/**
	 * Color model constructor
	 * @param jColor
	 * @param lang
	 * @throws JSONException
	 */
	public Color(JSONObject jColor, String lang) throws JSONException {
		this.colorCode = jColor.getString("colorCode");
		this.name = jColor.getString("name_" + lang);
	}



	public CharSequence getColorCode() {
		return colorCode;
	}


	public CharSequence getName() {
		return name;
	}

	public void setName(CharSequence name) {
		this.name = name;
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Color) {
			Color color = (Color) obj;
			return colorCode.equals(color.getColorCode()); 
		} else {
			return false;
		}
	}
	
	
}