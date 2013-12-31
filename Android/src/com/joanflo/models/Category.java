package com.joanflo.models;

import java.util.List;

public class Category {

	
	// primary key
	private int idCategory;
	
	// foreign key
	private List<Product> products;
	private List<Category> categories;
	
	// category info
	private int level;
	private CharSequence name;
	
	
	
	public Category(int idCategory, List<Product> products, List<Category> categories, int level, CharSequence name) {
		this.idCategory = idCategory;
		this.products = products;
		this.categories = categories;
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


	public List<Category> getCategories() {
		return categories;
	}
	
	public void addCategory(Category category) {
		categories.add(category);
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
	
	
	public int getItemsNumber() {
		if (categories != null) {
			return categories.size();
		}
		if (products != null) {
			return products.size();
		}
		return 0;
	}
	
	
	public boolean isLeafNode() {
		return categories == null;
	}
	
	
}