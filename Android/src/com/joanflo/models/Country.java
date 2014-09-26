package com.joanflo.models;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Country model
 * @author Joanflo
 */
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
	
	
	
	/**
	 * Country model constructor
	 * @param countryName
	 * @param regions
	 * @param code
	 * @param coin
	 */
	public Country(CharSequence countryName, List<Region> regions, int code, char coin) {
		this.countryName = countryName;
		this.regions = regions;
		this.code = code;
		this.coin = coin;
	}
	
	/**
	 * Country model constructor
	 * @param jCountry
	 * @throws JSONException
	 */
	public Country(JSONObject jCountry) throws JSONException {
		this.countryName = jCountry.getString("countryName");
		this.regions = null;
		this.code = jCountry.getInt("code");
		this.coin = jCountry.getString("coin").charAt(0);
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
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Country) {
			Country country = (Country) obj;
			return countryName.equals(country.getCountryName()); 
		} else {
			return false;
		}
	}
	

}