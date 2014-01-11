package com.joanflo.models;

public class Color {

	
	// primary key
	private CharSequence colorCode;
	
	// color info
	private CharSequence name;
	
	
	
	public Color(CharSequence colorCode, CharSequence name) {
		this.colorCode = colorCode;
		this.name = name;
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
			return colorCode == color.getColorCode(); 
		} else {
			return false;
		}
	}
	
	
}