package com.joanflo.models;

public class Shop {

	// primary key
	private int idShop;
	
	//foreign key
	private CharSequence cityName;
	
	// shop info
	private CharSequence direction;
	private CharSequence schedule;
	private CharSequence phone;
	private CharSequence email;
	
	// location
	private double latitude;
	private double longitude;
	
	
	
	public Shop(int idShop, CharSequence cityName, CharSequence direction, CharSequence schedule, CharSequence phone, CharSequence email, double latitude, double longitude) {
		this.idShop = idShop;
		this.cityName = cityName;
		this.direction = direction;
		this.schedule = schedule;
		this.phone = phone;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
	}



	public int getIdShop() {
		return idShop;
	}

	public CharSequence getCityName() {
		return cityName;
	}

	public CharSequence getDirection() {
		return direction;
	}

	public CharSequence getSchedule() {
		return schedule;
	}

	public CharSequence getPhone() {
		return phone;
	}

	public CharSequence getEmail() {
		return email;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
	
	
}
