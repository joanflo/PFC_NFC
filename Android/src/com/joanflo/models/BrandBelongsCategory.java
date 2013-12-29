package com.joanflo.models;

public class BrandBelongsCategory {
	
	
	// primary & foreign keys
	private Brand brand;
	private Category category;
	
	
	
	public BrandBelongsCategory(Brand brand, Category category) {
		this.brand = brand;
		this.category = category;
	}
	
	
	
	public Brand getBrand() {
		return brand;
	}


	public Category getCategory() {
		return category;
	}
	
	
}