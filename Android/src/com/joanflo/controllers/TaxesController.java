package com.joanflo.controllers;

import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.Toast;
import com.joanflo.models.Country;
import com.joanflo.models.Product;
import com.joanflo.models.PurchaseDetail;
import com.joanflo.models.Tax;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.ProductListActivity;
import com.joanflo.tagit.R;

public class TaxesController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public TaxesController(Activity activity) {
		this.activity = activity;
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(String route, int statusCode, JSONObject jObject, JSONArray jArray) {
		try {
			
			if (route.matches("taxes")) {
				// GET <URLbase>/taxes?idProduct={idProduct}&countryName={countryName}
				if (jObject != null) {
					// tax
					Tax tax = new Tax(jObject);
					
					if (activity instanceof ProductListActivity) {
						ProductListActivity productListActivity = (ProductListActivity) activity;
						productListActivity.taxReceived(tax);
					}
				}
			}
			
		} catch (JSONException e) {
			Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_request), Toast.LENGTH_SHORT).show();
		}
	}
	
	
	
	/**
	 * Get tax
	 * @param idProduct
	 * @param countryName
	 */
	public void getTax(int idProduct, CharSequence countryName) {
		// GET <URLbase>/taxes?idProduct={idProduct}&countryName={countryName}
		client.getTax(this, idProduct, countryName);
	}
	


	/**
	 * Calculates the total price for the given purchase
	 * @return purchase price
	 */
	public double calculateTotalPrice(List<PurchaseDetail> purchaseDetails, Country country) {
		double totalPrice = 0;
		
    	Iterator<PurchaseDetail> it = purchaseDetails.iterator();
    	while (it.hasNext()) {
    		PurchaseDetail detail = it.next();
    		
    		// get units
    		int units = detail.getUnits();
    		// get product price
    		Product product = detail.getBatch().getProduct();
    		Tax tax = product.searchTax(country);
    		double productPrice = product.calculatePrice(tax);
    		
    		totalPrice += units * productPrice;
    	}
		
		return totalPrice;
	}
	
	
}
