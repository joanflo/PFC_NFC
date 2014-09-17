package com.joanflo.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Color {

	
	// primary key
	private CharSequence colorCode;
	
	// color info
	private CharSequence name;
	
	
	
	public Color(CharSequence colorCode, CharSequence name) {
		this.colorCode = colorCode;
		this.name = name;
	}
	
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
	
	
	public boolean equals(Object obj) {
		if (obj instanceof Color) {
			Color color = (Color) obj;
			return colorCode.equals(color.getColorCode()); 
		} else {
			return false;
		}
	}
	
	
}