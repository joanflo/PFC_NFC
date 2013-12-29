package com.joanflo.models;

public class ProductBelongsCategory {
	
	
	// primary & foreign keys
	private Product product;
	private Category category;
	
	
	
	public ProductBelongsCategory(Product product, Category category) {
		this.product = product;
		this.category = category;
	}



	public Product getProduct() {
		return product;
	}


	public Category getCategory() {
		return category;
	}
	
	
}