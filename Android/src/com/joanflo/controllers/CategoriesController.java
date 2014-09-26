package com.joanflo.controllers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.Toast;
import com.joanflo.models.Category;
import com.joanflo.models.ProductBelongsCategory;
import com.joanflo.network.HttpStatusCode;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.CategoryListActivity;
import com.joanflo.tagit.ProductListActivity;
import com.joanflo.tagit.ProductSearchActivity;
import com.joanflo.tagit.R;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.Regex;

/**
 * Categories controller class
 * @author Joanflo
 */
public class CategoriesController {

	
	private RESTClient client;
	private Activity activity;
	
	
	
	/**
	 * Categories controller constructor
	 * @param activity
	 */
	public CategoriesController(Activity activity) {
		this.activity = activity;
		client = RESTClient.getInstance();
	}
	
	
	
	/**
	 * Method called when a request is received from the server.
	 * Parse the JSON data and delivers it to the properly activity
	 * according to the route request and the status code.
	 * @param route
	 * @param statusCode
	 * @param jObject
	 * @param jArray
	 */
	public synchronized void requestFinished(String route, int statusCode, JSONObject jObject, JSONArray jArray) {
		String lang = LocalStorage.getInstance().getLocaleLanguage(activity);
		
		try {
			if (statusCode == HttpStatusCode.REQUEST_TIMEOUT) {
				throw new Exception();
			}
			List<Category> categories;
			
			if (route.matches("categories")) {
				// GET <URLbase>/categories?level={level}
				if (jArray != null) {
					categories = processCategories(jArray, lang);
					
					if (activity instanceof CategoryListActivity) {
						CategoryListActivity categoryListActivity = (CategoryListActivity) activity;
						categoryListActivity.categoriesReceived(categories);
					} else if (activity instanceof ProductSearchActivity) {
						ProductSearchActivity productSearchActivity = (ProductSearchActivity) activity;
						productSearchActivity.categoriesReceived(categories);
					}
				}
				// GET <URLbase>/categories?idProduct={idProduct}
				if (jObject != null) {
					jArray = jObject.getJSONArray("Category");
					categories = processCategories(jArray, lang);
					jArray = jObject.getJSONArray("ProductBelongsCategory");
					List<ProductBelongsCategory> productBelongsCategories = processProductBelongsCategory(jArray);
					
					if (activity instanceof ProductListActivity) {
						ProductListActivity productListActivity = (ProductListActivity) activity;
						productListActivity.categoriesReceived(categories, productBelongsCategories);
					}
				}
				
			} else if (route.matches("categories/" + Regex.INTEGER + "/subcategories")) {
				// GET <URLbase>/categories/{idCategory}/subcategories
				if (jArray != null) {
					categories = processCategories(jArray, lang);
					
					if (activity instanceof CategoryListActivity) {
						CategoryListActivity categoryListActivity = (CategoryListActivity) activity;
						categoryListActivity.categoriesReceived(categories);
					}
				}
				// GET <URLbase>/categories/{idCategory}/subcategories?count=true
				if (jObject != null) {
					CategoryListActivity categoryListActivity = (CategoryListActivity) activity;
					
					if (jObject.has("categoriesCount")) {
						int idCategory = jObject.getInt("idCategory");
						int count = jObject.getInt("categoriesCount");
						categoryListActivity.countReceived(idCategory, count, false);
					} else if (jObject.has("productsCount")) {
						int idCategory = jObject.getInt("idCategory");
						int count = jObject.getInt("productsCount");
						categoryListActivity.countReceived(idCategory, count, true);
					}
				}
				
			}
			
		} catch (Exception e) {
			if (!RESTClient.isOnline(activity)) {
				Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_internetconnection), Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_request), Toast.LENGTH_SHORT).show();
			}
			activity.finish();
		}
	}
	
	

	/**
	 * Get categories
	 * @param level
	 */
	public void getCategories(int level) {
		// GET <URLbase>/categories?level={level}
		client.getCategories(this, level);
	}
	
	
	
	/**
	 * Get categories
	 * @param idProduct
	 */
	public void getProductCategories(int idProduct) {
		// GET <URLbase>/categories?idProduct={idProduct}
		client.getProductCategories(this, idProduct);
	}
	
	
	
	/**
	 * Get subcategories
	 * @param idCategory
	 */
	public void getSubategories(int idCategory) {
		// GET <URLbase>/categories/{idCategory}/subcategories
		client.getSubategories(this, idCategory);
	}
	
	
	
	/**
	 * Get subcategories/products count for the given category
	 * @param idCategory
	 */
	public void getSubategoriesCount(int idCategory) {
		// GET <URLbase>/categories/{idCategory}/subcategories?count=true
		client.getSubategoriesCount(this, idCategory);
	}
	
	
	
	/**
	 * Converts an array of JSON data into an array of categories
	 * @param jCategories
	 * @param lang
	 * @return
	 * @throws JSONException
	 */
	private List<Category> processCategories(JSONArray jCategories, String lang) throws JSONException {
		List<Category> categories = new ArrayList<Category>(jCategories.length());
		
		// for each category JSON object
		for (int i = 0; i < jCategories.length(); i++) {
			// create Category model from JSON object
			JSONObject jCategory = jCategories.getJSONObject(i);
			Category category = new Category(jCategory, lang);
			categories.add(category);
		}
		
		return categories;
	}
	
	
	
	/**
	 * Converts an array of JSON data into an array of ProductBelongsCategory model
	 * @param jProductBelongsCategories
	 * @return
	 * @throws JSONException
	 */
	private List<ProductBelongsCategory> processProductBelongsCategory(JSONArray jProductBelongsCategories) throws JSONException {
		List<ProductBelongsCategory> productBelongsCategories = new ArrayList<ProductBelongsCategory>(jProductBelongsCategories.length());
		
		// for each product belongs category JSON object
		for (int i = 0; i < jProductBelongsCategories.length(); i++) {
			// create productBelongsCategory model from JSON object
			JSONObject jProductBelongsCategory = jProductBelongsCategories.getJSONObject(i);
			ProductBelongsCategory productBelongsCategory = new ProductBelongsCategory(jProductBelongsCategory);
			productBelongsCategories.add(productBelongsCategory);
		}
		
		return productBelongsCategories;
	}
	
	
}
