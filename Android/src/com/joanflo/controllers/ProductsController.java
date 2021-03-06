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
import com.joanflo.network.HttpStatusCode;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.NewReviewActivity;
import com.joanflo.tagit.ProductActivity;
import com.joanflo.tagit.ProductListActivity;
import com.joanflo.tagit.PurchaseDetailListActivity;
import com.joanflo.tagit.R;
import com.joanflo.tagit.ReviewListActivity;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.Regex;

/**
 * Products controller class
 * @author Joanflo
 */
public class ProductsController {

	
	private RESTClient client;
	private Activity activity;
	
	
	
	/**
	 * Products controller constructor
	 * @param activity
	 */
	public ProductsController(Activity activity) {
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
		// get language code
		String lang = LocalStorage.getInstance().getLocaleLanguage(activity);
		
		try {
			if (statusCode == HttpStatusCode.REQUEST_TIMEOUT) {
				throw new Exception();
			}
			
			if (route.matches("products")) {
				// GET <URLbase>/products?queryName={queryName}
				// GET <URLbase>/products?queryName={queryName}&priceFrom={priceFrom}&priceSince={priceSince}&coin={coin}&brandName={brandName}&idCategory={idCategory}&rating={rating}
				// GET <URLbase>/products?idCategory={idCategory}
				if (jArray != null) {
					// list of products
					List<Product> products = processProducts(jArray, lang);
					
					if (activity instanceof ProductListActivity) {
						ProductListActivity productListActivity = (ProductListActivity) activity;
						productListActivity.productsReceived(products);
					}
					
				} else if (statusCode == HttpStatusCode.NOT_FOUND) {
					// 404
					if (activity instanceof ProductListActivity) {
						ProductListActivity productListActivity = (ProductListActivity) activity;
						productListActivity.productsReceived(new ArrayList<Product>());
					}
				}
				
			} else if (route.matches("products/" + Regex.INTEGER)) {
				// GET <URLbase>/products/{idProduct}
				if (jObject != null) {
					Product product = new Product(jObject, lang);
					
					if (activity instanceof ProductActivity) {
						ProductActivity productActivity = (ProductActivity) activity;
						productActivity.productReceived(product);
					} else if (activity instanceof PurchaseDetailListActivity) {
						PurchaseDetailListActivity purchaseDetailListActivity = (PurchaseDetailListActivity) activity;
						purchaseDetailListActivity.productReceived(product);
					}
				}
				
			} else if (route.matches("products/" + Regex.INTEGER + "/reviews")) {
				// GET <URLbase>/products/{idProduct}/reviews
				if (jArray != null) {
					// list of reviews
					List<Review> reviews = processReviews(jArray);
					
					if (activity instanceof ProductListActivity) {
						ProductListActivity productListActivity = (ProductListActivity) activity;
						productListActivity.reviewsReceived(reviews);
					} else if (activity instanceof ProductActivity) {
						ProductActivity productActivity = (ProductActivity) activity;
						productActivity.reviewsReceived(reviews);
					} else if (activity instanceof ReviewListActivity) {
						ReviewListActivity reviewListActivity = (ReviewListActivity) activity;
						reviewListActivity.reviewsReceived(reviews);
					}
					
				} else if (statusCode == HttpStatusCode.NOT_FOUND) {
					// 404
					if (activity instanceof ProductListActivity) {
						ProductListActivity productListActivity = (ProductListActivity) activity;
						productListActivity.reviewsReceived(new ArrayList<Review>());
					} else if (activity instanceof ProductActivity) {
						ProductActivity productActivity = (ProductActivity) activity;
						productActivity.reviewsReceived(new ArrayList<Review>());
					} else if (activity instanceof ReviewListActivity) {
						ReviewListActivity reviewListActivity = (ReviewListActivity) activity;
						reviewListActivity.reviewsReceived(new ArrayList<Review>());
					}
				}
				
			} else if (route.matches("products/" + Regex.INTEGER + "/related_products")) {
				// GET <URLbase>/products/{idProduct}/related_products
				if (jArray != null) {
					// list of (related) products
					List<Product> products = processProducts(jArray, lang);
					
					if (activity instanceof ProductActivity) {
						ProductActivity productActivity = (ProductActivity) activity;
						productActivity.relatedProductsReceived(products);
					}
					
				} else if (statusCode == HttpStatusCode.NOT_FOUND) {
					// 404
					
					if (activity instanceof ProductActivity) {
						ProductActivity productActivity = (ProductActivity) activity;
						productActivity.relatedProductsReceived(new ArrayList<Product>());
					}
				}
				
			} else if (route.matches("products/" + Regex.INTEGER + "/product_images")) {
				// GET <URLbase>/products/{idProduct}/product_images
				// GET <URLbase>/products/{idProduct}/product_images?type={ProductImage.TYPE_FRONT}
				if (jArray != null) {
					// list of product images
					List<ProductImage> productImages = processProductImages(jArray, lang);
					
					if (activity instanceof ProductListActivity) {
						ProductListActivity productListActivity = (ProductListActivity) activity;
						productListActivity.frontImageReceived(productImages.get(0));
					} else if (activity instanceof ProductActivity) {
						ProductActivity productActivity = (ProductActivity) activity;
						productActivity.productImagesReceived(productImages);
					} else if (activity instanceof PurchaseDetailListActivity) {
						PurchaseDetailListActivity purchaseDetailListActivity = (PurchaseDetailListActivity) activity;
						purchaseDetailListActivity.frontImageReceived(productImages.get(0));
					} else if (activity instanceof ReviewListActivity) {
						ReviewListActivity reviewListActivity = (ReviewListActivity) activity;
						reviewListActivity.frontImageReceived(productImages.get(0));
					} else if (activity instanceof NewReviewActivity) {
						NewReviewActivity newReviewActivity = (NewReviewActivity) activity;
						newReviewActivity.frontImageReceived(productImages.get(0));
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
	 * Get products by name
	 * @param queryName
	 */
	public void getProducts(CharSequence queryName) {
		// GET <URLbase>/products?queryName={queryName}
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
		// GET <URLbase>/products?queryName={queryName}&priceFrom={priceFrom}&priceSince={priceSince}&coin={coin}&brandName={brandName}&idCategory={idCategory}&rating={rating}
		client.getProducts(this, queryName, priceFrom, priceSince, coin, brandName, idCategory, rating);
	}
	
	
	
	/**
	 * Get products by category id
	 * @param idCategory
	 */
	public void getProducts(int idCategory) {
		// GET <URLbase>/products?idCategory={idCategory}
		client.getProducts(this, idCategory);
	}
	
	
	
	/**
	 * Get product reviews
	 * @param idProduct
	 */
	public void getReviews(int idProduct) {
		// GET <URLbase>/products/{idProduct}/reviews
		client.getReviews(this, idProduct);
	}
	
	
	
	/**
	 * Get product
	 * @param idProduct
	 */
	public void getProduct(int idProduct) {
		// GET <URLbase>/products/{idProduct}
		client.getProduct(this, idProduct);
	}	
	
	
	
	/**
	 * Get product images
	 * @param idProduct
	 */
	public void getProductImages(int idProduct) {
		// GET <URLbase>/products/{idProduct}/product_images
		client.getProductImages(this, idProduct);
	}
	
	
	
	/**
	 * Get product's front image
	 * @param idProduct
	 */
	public void getProductFrontImage(int idProduct) {
		// GET <URLbase>/products/{idProduct}/product_images?type={ProductImage.TYPE_FRONT}
		client.getProductFrontImage(this, idProduct);
	}
	
	
	
	/**
	 * Get related products
	 * @param idProduct
	 */
	public void getRelatedProducts(int idProduct) {
		// GET <URLbase>/products/{idProduct}/related_products
		client.getRelatedProducts(this, idProduct);
	}
	
	
	
	/**
	 * Converts an array of JSON data into an array of products
	 * @param jProducts
	 * @param lang
	 * @return
	 * @throws JSONException
	 */
	private List<Product> processProducts(JSONArray jProducts, String lang) throws JSONException {
		List<Product> products = new ArrayList<Product>(jProducts.length());
		
		// for each product JSON object
		for (int i = 0; i < jProducts.length(); i++) {
			// create Product model from JSON object
			JSONObject jProduct = jProducts.getJSONObject(i);
			Product product = new Product(jProduct, lang);
			products.add(product);
		}
		
		return products;
	}
	
	
	
	/**
	 * Converts an array of JSON data into an array of ProductImage model
	 * @param jProductImages
	 * @param lang
	 * @return
	 * @throws JSONException
	 */
	private List<ProductImage> processProductImages(JSONArray jProductImages, String lang) throws JSONException {
		List<ProductImage> productImages = new ArrayList<ProductImage>(jProductImages.length());
		
		// for each product image JSON object
		for (int i = 0; i < jProductImages.length(); i++) {
			// create ProductImage model from JSON object
			JSONObject jProductImage = jProductImages.getJSONObject(i);
			ProductImage productImage = new ProductImage(jProductImage, lang);
			productImages.add(productImage);
		}
		
		return productImages;
	}
	
	
	
	/**
	 * Converts an array of JSON data into an array of reviews
	 * @param jReviews
	 * @return
	 * @throws JSONException
	 */
	private List<Review> processReviews(JSONArray jReviews) throws JSONException {
		List<Review> reviews = new ArrayList<Review>(jReviews.length());
		
		// for each review JSON object
		for (int i = 0; i < jReviews.length(); i++) {
			// create Review model from JSON object
			JSONObject jReview = jReviews.getJSONObject(i);
			Review review = new Review(jReview);
			reviews.add(review);
		}
		
		return reviews;
	}
	
	
}
