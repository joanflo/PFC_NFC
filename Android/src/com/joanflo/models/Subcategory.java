package com.joanflo.models;

public class Subcategory {

	
	// primary & foreign key
	private Category categoryFather;
	private Category categorySon;
	
	
	
	public Subcategory(Category categoryFather, Category categorySon) {
		this.categoryFather = categoryFather;
		this.categorySon = categorySon;
	}
	
	
	
	public Category getCategoryFather() {
		return categoryFather;
	}


	public Category getCategorySon() {
		return categorySon;
	}
	
	
}