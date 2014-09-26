package com.joanflo.controllers;

import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.Toast;
import com.joanflo.models.Country;
import com.joanflo.models.Product;
import com.joanflo.models.PurchaseDetail;
import com.joanflo.models.Tax;
import com.joanflo.network.HttpStatusCode;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.ProductActivity;
import com.joanflo.tagit.ProductListActivity;
import com.joanflo.tagit.PurchaseDetailListActivity;
import com.joanflo.tagit.PurchaseListActivity;
import com.joanflo.tagit.R;

/**
 * Taxes controller class
 * @author Joanflo
 */
public class TaxesController {

	
	private RESTClient client;
	private Activity activity;
	
	
	
	/**
	 * Taxes controller constructor
	 * @param activity
	 */
	public TaxesController(Activity activity) {
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
			
			if (route.matches("taxes")) {
				// GET <URLbase>/taxes?idProduct={idProduct}&countryName={countryName}
				if (jObject != null) {
					// tax
					Tax tax = new Tax(jObject);
					
					if (activity instanceof ProductListActivity) {
						ProductListActivity productListActivity = (ProductListActivity) activity;
						productListActivity.taxReceived(tax);
						
					} else if (activity instanceof ProductActivity) {
						ProductActivity productActivity = (ProductActivity) activity;
						productActivity.taxReceived(tax);
						
					} else if (activity instanceof PurchaseDetailListActivity) {
						PurchaseDetailListActivity purchaseDetailListActivity = (PurchaseDetailListActivity) activity;
						purchaseDetailListActivity.taxReceived(tax);
						
					} else if (activity instanceof PurchaseListActivity) {
						PurchaseListActivity purchaseListActivity = (PurchaseListActivity) activity;
						purchaseListActivity.taxReceived(tax);
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
