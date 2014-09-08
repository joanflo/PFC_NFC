package com.joanflo.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Brand {

	
	// primary key
	private CharSequence brandName;
	
	// brand info
	private CharSequence headquarter;
	private CharSequence phone;
	private CharSequence email;
	
	// location
	private double latitude;
	private double longitude;
	
	
	
	public Brand(CharSequence brandName, CharSequence headquarter, CharSequence phone, CharSequence email, double latitude, double longitude) {
		this.brandName = brandName;
		this.headquarter = headquarter;
		this.phone = phone;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Brand(CharSequence brandName) {
		this.brandName = brandName;
	}
	
	public Brand(JSONObject jBrand) throws JSONException {
		this.brandName = jBrand.getString("brandName");
		this.headquarter = jBrand.getString("headquarter");
		this.phone = jBrand.getString("phone");
		this.email = jBrand.getString("email");
		this.latitude = jBrand.getDouble("latitude");
		this.longitude = jBrand.getDouble("longitude");
	}
	
	
	
	public CharSequence getBrandName() {
		return brandName;
	}


	public CharSequence getHeadquarter() {
		return headquarter;
	}
	
	public void setHeadquarter(CharSequence headquarter) {
		this.headquarter = headquarter;
	}


	public CharSequence getPhone() {
		return phone;
	}
	
	public void setPhone(CharSequence phone) {
		this.phone = phone;
	}


	public CharSequence getEmail() {
		return email;
	}
	
	public void setEmail(CharSequence email) {
		this.email = email;
	}


	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
}