package com.joanflo.models;

public class Badge {

	// primary key
	private CharSequence badgeName;
	
	// badge info
	private CharSequence description;
	private int type;
	
	
	
	public Badge(CharSequence badgeName, CharSequence description, int type) {
		this.badgeName = badgeName;
		this.description = description;
		this.type = type;
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
	
	
	public int getType() {
		return type;
	}
	
	
}