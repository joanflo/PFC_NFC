package com.joanflo.models;

import java.util.List;

public class City {


	// primary key
	private CharSequence cityName;
	
	// foreign key
	private List<Shop> shops;
	private Region region;
	
	
	
	public City(CharSequence cityName, List<Shop> shops, Region region) {
		this.cityName = cityName;
		this.shops = shops;
		this.region = region;
	}
	
	
	
	public CharSequence getCityName() {
		return cityName;
	}
	
	
	public List<Shop> getShops() {
		return shops;
	}
	
	public void addShop(Shop shop) {
		shops.add(shop);
	}
	
	
	public Region getRegion() {
		return region;
	}
	
	
}