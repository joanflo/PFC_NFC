package com.joanflo.models;

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