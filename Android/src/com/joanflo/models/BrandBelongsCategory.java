package com.joanflo.models;

/**
 * BrandBelongsCategory model
 * @author Joanflo
 */
public class BrandBelongsCategory {
	
	
	// primary & foreign keys
	private Brand brand;
	private Category category;
	
	
	
	/**
	 * BrandBelongsCategory model constructor
	 * @param brand
	 * @param category
	 */
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