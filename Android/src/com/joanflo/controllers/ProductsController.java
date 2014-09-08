package com.joanflo.controllers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.Toast;

import com.joanflo.models.Product;
import com.joanflo.models.ProductImage;
import com.joanflo.models.Review;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.ProductListActivity;
import com.joanflo.tagit.R;
import com.joanflo.utils.LocalStorage;

public class ProductsController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public ProductsController(Activity activity) {
		this.activity = activity;
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(String route, int statusCode, JSONObject jObject, JSONArray jArray) {
		/*try {
			
			if (route.equals("")) {
				// 
				
				
			} else if (route.equals("")) {
				// 
				
				
			}
			
			Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_request), Toast.LENGTH_SHORT).show();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
		
		
		try {
		
		
			
			
			if (activity instanceof ProductListActivity) {
				ProductListActivity productListActivity = (ProductListActivity) activity;
				// get language
				String lang = LocalStorage.getInstance().getLocaleLanguage(activity);
				
				if (jArray != null && jArray.getJSONObject(0).has("idComment")) {
					// received a list of reviews
					List<Review> reviews = new ArrayList<Review>();
					for (int i = 0; i < jArray.length(); i++) {
						// create ProductImage model from JSON object
						JSONObject jReview = (JSONObject) jArray.get(i);
						Review review = new Review(jReview);
						reviews.add(review);
					}
					productListActivity.reviewsReceived(reviews);
					
					
				} else if (jArray.getJSONObject(0).has("url")) {
					// received a list of images
					List<ProductImage> productImages = new ArrayList<ProductImage>();
					for (int i = 0; i < jArray.length(); i++) {
						// create ProductImage model from JSON object
						JSONObject jProductImage = (JSONObject) jArray.get(i);
						ProductImage productImage = new ProductImage(jProductImage, lang);
						productImages.add(productImage);
					}
					productListActivity.frontImageReceived(productImages.get(0));
					
				} else {
					// received a list of products
					
					// for each product JSON object
					List<Product> products = new ArrayList<Product>();
					for (int i = 0; i < jArray.length(); i++) {
						// create Product model from JSON object
						JSONObject jProduct = (JSONObject) jArray.get(i);
						Product product = new Product(jProduct, lang);
						products.add(product);
					}
					productListActivity.productsReceived(products);
				}
				
			}
			
	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	/**
	 * Get products by name
	 * @param queryName
	 */
	public void getProducts(CharSequence queryName) {
		client.getProducts(this, queryName);
	}
	
	
	
	/**
	 * Get products by advanced search
	 * @param queryName
	 * @param priceFrom
	 * @param priceSince
	 * @param coin
	 * @param brandName
	 * @param idCategory
	 * @param rating
	 */
	public void getProducts(CharSequence queryName, float priceFrom, float priceSince, char coin, CharSequence brandName, int idCategory, float rating) {
		client.getProducts(this, queryName, priceFrom, priceSince, coin, brandName, idCategory, rating);
	}
	
	
	
	/**
	 * Get products by category id
	 * @param idCategory
	 */
	public void getProducts(int idCategory) {
		client.getProducts(this, idCategory);
	}
	
	
	
	/**
	 * Get product reviews
	 * @param idProduct
	 */
	public void getReviews(int idProduct) {
		client.getReviews(this, idProduct);
	}
	
	
	
	/**
	 * Get product
	 * @param idProduct
	 */
	public void getProduct(int idProduct) {
		client.getProduct(this, idProduct);
	}	
	
	
	
	/**
	 * Get product images
	 * @param idProduct
	 */
	public void getProductImages(int idProduct) {
		client.getProductImages(this, idProduct);
	}
	
	
	
	/**
	 * Get product's front image
	 * @param idProduct
	 */
	public void getProductFrontImage(int idProduct) {
		client.getProductFrontImage(this, idProduct);
	}
	
	
	
	/**
	 * Get related products
	 * @param idProduct
	 */
	public void getRelatedProducts(int idProduct) {
		client.getRelatedProducts(this, idProduct);
	}
	
	
}
