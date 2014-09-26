package com.joanflo.models;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Category model
 * @author Joanflo
 */
public class Category {

	
	// primary key
	private int idCategory;
	
	// foreign key
	private List<Product> products;
	private List<Category> categories;
	
	// category info
	private int level;
	private CharSequence name;
	
	
	
	/**
	 * Category model constructor
	 * @param idCategory
	 * @param products
	 * @param categories
	 * @param level
	 * @param name
	 */
	public Category(int idCategory, ArrayList<Product> products, ArrayList<Category> categories, int level, CharSequence name) {
		this.idCategory = idCategory;
		this.products = products;
		this.categories = categories;
		this.level = level;
		this.name = name;
	}
	
	/**
	 * Category model constructor
	 * @param jCategory
	 * @param lang
	 * @throws JSONException
	 */
	public Category(JSONObject jCategory, String lang) throws JSONException {
		// id category
		this.idCategory = jCategory.getInt("idCategory");
		// products list
		this.products = new ArrayList<Product>();
		if (jCategory.has("products")) {
			JSONArray jProducts = jCategory.getJSONArray("products");
			for (int i = 0; i < jProducts.length(); i++) {
				JSONObject jProduct = (JSONObject) jProducts.get(i);
				Product product = new Product(jProduct, lang);
				this.products.add(product);
			}
		}
		// categories list
		this.categories = new ArrayList<Category>();
		if (jCategory.has("categories")) {
			JSONArray jCategories = jCategory.getJSONArray("categories");
			for (int i = 0; i < jCategories.length(); i++) {
				JSONObject jSubcategory = (JSONObject) jCategories.get(i);
				Category category = new Category(jSubcategory, lang);
				this.categories.add(category);
			}
		}
		// level
		this.level = jCategory.getInt("level");
		// name
		this.name = jCategory.getString("name_" + lang);
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
	
	public void setCategories(List<Category> categories) {
		this.categories = categories;
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
	
	
	public void setItemsNumber(int count, boolean productCount) {
		this.categories = null;
		this.products = null;
		if (productCount) {
			this.products = new ArrayList<Product>(count);
			for (int i = 0; i < count; i++) {
				this.products.add(i, null);
			}
		} else {
			this.categories = new ArrayList<Category>(count);
			for (int i = 0; i < count; i++) {
				this.categories.add(i, null);
			}
		}
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