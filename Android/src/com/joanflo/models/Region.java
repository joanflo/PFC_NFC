package com.joanflo.models;

import java.util.List;

public class Region {

	
	// primary key
	private CharSequence regionName;
	
	// foreign keys
	private List<City> cities;
	private Country country;
	
	
	
	public Region(CharSequence regionName, List<City> cities, Country country) {
		this.regionName = regionName;
		this.cities = cities;
		this.country = country;
	}
	
	
	
	public CharSequence getRegionName() {
		return regionName;
	}
	
	
	public List<City> getCities() {
		return cities;
	}
	
	public void addCity(City city) {
		cities.add(city);
	}
	
	
	public Country getCountry() {
		return country;
	}
	
	
}