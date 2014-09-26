package com.joanflo.controllers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.Toast;
import com.joanflo.models.Brand;
import com.joanflo.network.HttpStatusCode;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.BrandActivity;
import com.joanflo.tagit.ProductSearchActivity;
import com.joanflo.tagit.R;
import com.joanflo.utils.Regex;

/**
 * Brands controller class
 * @author Joanflo
 */
public class BrandsController {

	
	private RESTClient client;
	private Activity activity;
	
	
	
	/**
	 * Brands controller constructor
	 * @param activity
	 */
	public BrandsController(Activity activity) {
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
		try {
			if (statusCode == HttpStatusCode.REQUEST_TIMEOUT) {
				throw new Exception();
			}
			
			if (route.matches("brands")) {
				// GET <URLbase>/brands
				if (jArray != null) {
					// list of brands
					List<Brand> brands = processBrands(jArray);
					
					if (activity instanceof ProductSearchActivity) {
						ProductSearchActivity productSearchActivity = (ProductSearchActivity) activity;
						productSearchActivity.brandsReceived(brands);
					}
				}
				
			} else if (route.matches("brands/" + Regex.TEXT)) {
				// GET <URLbase>/brands/{brandName}
				if (jObject != null) {
					// brand
					Brand brand = new Brand(jObject);
					
					if (activity instanceof BrandActivity) {
						BrandActivity brandActivity = (BrandActivity) activity;
						brandActivity.brandReceived(brand);
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
	 * Get brands
	 */
	public void getBrands() {
		// GET <URLbase>/brands
		client.getBrands(this);
	}
	
	
	
	/**
	 * Get brand
	 * @param brandName
	 */
	public void getBrand(CharSequence brandName) {
		// GET <URLbase>/brands/{brandName}
		client.getBrand(this, brandName);
	}
	
	
	
	/**
	 * Converts an array of JSON data into an array of brands
	 * @param jBrands
	 * @return
	 * @throws JSONException
	 */
	private List<Brand> processBrands(JSONArray jBrands) throws JSONException {
		List<Brand> brands = new ArrayList<Brand>(jBrands.length());
		
		// for each brand JSON object
		for (int i = 0; i < jBrands.length(); i++) {
			// create Brand model from JSON object
			JSONObject jBrand = jBrands.getJSONObject(i);
			Brand brand = new Brand(jBrand);
			brands.add(brand);
		}
		
		return brands;
	}
	
	
}
