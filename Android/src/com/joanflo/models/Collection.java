package com.joanflo.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Collection {

	
	// primary key
	private int idCollection;
	
	// collection info
	private CharSequence name;
	private int year;
	
	
	
	public Collection(int idCollection, CharSequence name, int year) {
		this.idCollection = idCollection;
		this.name = name;
		this.year = year;
	}
	
	public Collection(int idCollection) {
		this.idCollection = idCollection;
	}
	
	public Collection(JSONObject jCollection, String lang) throws JSONException {
		this.idCollection = jCollection.getInt("idCollection");
		this.name = jCollection.getString("name_" + lang);
		this.year = jCollection.getInt("year");
	}



	public int getIdCollection() {
		return idCollection;
	}


	public CharSequence getName() {
		return name;
	}

	public void setName(CharSequence name) {
		this.name = name;
	}


	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	
}