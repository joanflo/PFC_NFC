package com.joanflo.models;

public class Brand {

	
	// primary key
	private CharSequence brandName;
	
	// brand info
	private CharSequence headquarter;
	private CharSequence phone;
	private CharSequence email;
	
	
	
	public Brand(CharSequence brandName, CharSequence headquarter, CharSequence phone, CharSequence email) {
		this.brandName = brandName;
		this.headquarter = headquarter;
		this.phone = phone;
		this.email = email;
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
	
	
}