package com.joanflo.controllers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.Toast;
import com.joanflo.models.Shop;
import com.joanflo.network.HttpStatusCode;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.PurchaseDetailListActivity;
import com.joanflo.tagit.PurchaseListActivity;
import com.joanflo.tagit.R;
import com.joanflo.tagit.ShopSelectionActivity;
import com.joanflo.utils.Regex;

/**
 * Shops controller class
 * @author Joanflo
 */
public class ShopsController {

	
	private RESTClient client;
	private Activity activity;
	
	
	
	/**
	 * Shops controller constructor
	 * @param activity
	 */
	public ShopsController(Activity activity) {
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
			
			if (route.matches("shops")) {
				// GET <URLbase>/shops?cityName={cityName}
				// GET <URLbase>/shops?latitude={latitude}&longitude={longitude}
				if (jArray != null) {
					// list of shops
					List<Shop> shops = processShops(jArray);
					
					if (activity instanceof ShopSelectionActivity) {
						ShopSelectionActivity shopSelectionActivity = (ShopSelectionActivity) activity;
						shopSelectionActivity.shopsReceived(shops);
					}
					
				} else if (statusCode == HttpStatusCode.NOT_FOUND) {
					
					if (activity instanceof ShopSelectionActivity) {
						ShopSelectionActivity shopSelectionActivity = (ShopSelectionActivity) activity;
						shopSelectionActivity.shopsReceived(null);
					}
					
				}
				
			} else if (route.matches("shops/" + Regex.INTEGER)) {
				// GET <URLbase>/shops/{idShop}
				if (jObject != null) {
					// shop
					Shop shop = new Shop(jObject);
					
					if (activity instanceof PurchaseDetailListActivity) {
						PurchaseDetailListActivity purchaseDetailListActivity = (PurchaseDetailListActivity) activity;
						purchaseDetailListActivity.shopReceived(shop);
						
					} else if (activity instanceof PurchaseListActivity) {
						PurchaseListActivity purchaseListActivity = (PurchaseListActivity) activity;
						purchaseListActivity.shopReceived(shop);
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
	 * Get all the shops in the city
	 * @param cityName
	 */
	public void getShops(CharSequence cityName) {
		// GET <URLbase>/shops?cityName={cityName}
		client.getShops(this, cityName);
	}
	
	
	
	/**
	 * Get the nearest shops from current location
	 * @param latitude
	 * @param longitude
	 */
	public void getShops(double latitude, double longitude) {
		// GET <URLbase>/shops?latitude={latitude}&longitude={longitude}
		client.getShops(this, latitude, longitude);
	}
	
	
	
	/**
	 * Get shop
	 * @param idShop
	 */
	public void getShop(int idShop) {
		// GET <URLbase>/shops/{idShop}
		client.getShop(this, idShop);
	}
	
	
	
	/**
	 * Converts an array of JSON data into an array of shops
	 * @param jShops
	 * @return
	 * @throws JSONException
	 */
	private List<Shop> processShops(JSONArray jShops) throws JSONException {
		List<Shop> shops = new ArrayList<Shop>(jShops.length());
		
		// for each shop JSON object
		for (int i = 0; i < jShops.length(); i++) {
			// create Shop model from JSON object
			JSONObject jShop = jShops.getJSONObject(i);
			Shop shop = new Shop(jShop);
			shops.add(shop);
		}
		
		return shops;
	}
	
	
}
