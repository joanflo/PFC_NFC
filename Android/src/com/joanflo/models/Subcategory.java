package com.joanflo.models;

/**
 * Subcategory model
 * @author Joanflo
 */
public class Subcategory {

	
	// primary & foreign key
	private Category categoryFather;
	private Category categorySon;
	
	
	
	/**
	 * Subcategory model constructor
	 * @param categoryFather
	 * @param categorySon
	 */
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