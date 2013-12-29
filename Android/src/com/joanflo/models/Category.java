package com.joanflo.models;

import java.util.List;

public class Category {

	
	// primary key
	private int idCategory;
	
	// foreign key
	private List<Product> products;
	
	// category info
	private int level;
	private CharSequence name;
	
	
	
	public Category(int idCategory, List<Product> products, int level, CharSequence name) {
		this.idCategory = idCategory;
		this.products = products;
		this.level = level;
		this.name = name;
	}



	public int getIdCategory() {
		return idCategory;
	}


	public List<Product> getProducts() {
		return products;
	}
	
	public void addProduct(Product product) {
		products.add(product);
	}


	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


	public CharSequence getName() {
		return name;
	}

	public void setName(CharSequence name) {
		this.name = name;
	}
	
	
}