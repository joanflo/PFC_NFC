package com.joanflo.models;

public class ProductImage {

	
	// type constants
	public static final char TYPE_FRONT = 'f';
	public static final char TYPE_REGULAR = 'r';
	
	
	// primary key
	private CharSequence url;
	
	// foreign key
	private Product product;
	
	// product image info
	private char type;
	private CharSequence description;
	
	
	
	public ProductImage(CharSequence url, Product product, char type, CharSequence description) {
		this.url = url;
		this.product = product;
		this.type = type;
		this.description = description;
	}



	public CharSequence getUrl() {
		return url;
	}

	public void setUrl(CharSequence url) {
		this.url = url;
	}

	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}


	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}


	public CharSequence getDescription() {
		return description;
	}

	public void setDescription(CharSequence description) {
		this.description = description;
	}
	
	
}