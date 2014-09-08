package com.joanflo.controllers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.Toast;

import com.joanflo.models.Brand;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.ProductSearchActivity;
import com.joanflo.tagit.R;

public class BrandsController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public BrandsController(Activity activity) {
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
			
			
			if (activity instanceof ProductSearchActivity) {
				ProductSearchActivity productSearchActivity = (ProductSearchActivity) activity;
				
				// for each brand JSON object
				List<Brand> brands = new ArrayList<Brand>();
				for (int i = 0; i < jArray.length(); i++) {
					// create Brand model from JSON object
					JSONObject jBrand = jArray.getJSONObject(i);
					Brand brand = new Brand(jBrand);
					brands.add(brand);
				}
				productSearchActivity.brandsReceived(brands);
				
			}
			
	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Get brands
	 */
	public void getBrands() {
		client.getBrands(this);
	}
	
	
	
	/**
	 * Get brand
	 * @param brandName
	 */
	public void getBrand(CharSequence brandName) {
		client.getBrand(this, brandName);
	}
	
}
