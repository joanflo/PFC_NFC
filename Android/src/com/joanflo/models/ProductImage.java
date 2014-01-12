package com.joanflo.models;

import java.net.MalformedURLException;
import java.net.URL;

public class ProductImage {

	
	// type constants
	public static final char TYPE_FRONT = 'f';
	public static final char TYPE_REGULAR = 'r';
	
	
	// primary key
	private URL url;
	
	// foreign key
	private Product product;
	
	// product image info
	private char type;
	private CharSequence description;
	
	
	
	public ProductImage(String url, Product product, char type, CharSequence description) throws MalformedURLException {
		this.url = new URL(url);
		this.product = product;
		this.type = type;
		this.description = description;
	}



	public URL getUrl() {
		return url;
	}

	public void setUrl(String url) throws MalformedURLException {
		this.url = new URL(url);
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