package com.joanflo.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ProductBelongsCategory model
 * @author Joanflo
 */
public class ProductBelongsCategory {
	
	
	// primary key
	private int idProductBelongsCategory;
	
	// foreign keys
	private Product product;
	private Category category;
	
	
	
	/**
	 * ProductBelongsCategory model constructor
	 * @param idProductBelongsCategory
	 * @param product
	 * @param category
	 */
	public ProductBelongsCategory(int idProductBelongsCategory, Product product, Category category) {
		this.idProductBelongsCategory = idProductBelongsCategory;
		this.product = product;
		this.category = category;
	}
	
	/**
	 * ProductBelongsCategory model constructor
	 * @param jProductBelongsCategory
	 * @throws JSONException
	 */
	public ProductBelongsCategory(JSONObject jProductBelongsCategory) throws JSONException {
		// id
		this.idProductBelongsCategory = jProductBelongsCategory.getInt("idProductBelongsCategory");
		// product
		int idProduct = jProductBelongsCategory.getInt("idProduct");
		this.product = new Product(idProduct, null, null, "", "", "", "");
		// category
		int idCategory = jProductBelongsCategory.getInt("idCategory");
		this.category = new Category(idCategory, null, null, 0, "");
	}
	
	
	
	public int getIdProductBelongsCategory() {
		return idProductBelongsCategory;
	}



	public Product getProduct() {
		return product;
	}


	public Category getCategory() {
		return category;
	}
	
	
}