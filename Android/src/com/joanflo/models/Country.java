package com.joanflo.models;

import java.util.List;

public class Country {
	
	
	// coin constants
	public static final char EURO = '€';
	public static final char DOLAR = '$';
	public static final char POUND = '£';
	
	
	// primary key
	private CharSequence countryName;
	
	// foreign key
	private List<Region> regions;
	
	// country info
	private int code;
	private char coin;
	
	
	
	public Country(CharSequence countryName, List<Region> regions, int code, char coin) {
		this.countryName = countryName;
		this.regions = regions;
		this.code = code;
		this.coin = coin;
	}
	


	public CharSequence getCountryName() {
		return countryName;
	}
	
	
	public List<Region> getRegions() {
		return regions;
	}
	
	public void addRegion(Region region) {
		regions.add(region);
	}
	
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	
	public char getCoin() {
		return coin;
	}
	
	public void setCoin(char coin) {
		this.coin = coin;
	}
	

}