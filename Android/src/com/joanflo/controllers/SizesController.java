package com.joanflo.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.Toast;
import com.joanflo.models.Size;
import com.joanflo.network.HttpStatusCode;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.ProductActivity;
import com.joanflo.tagit.PurchaseDetailListActivity;
import com.joanflo.tagit.R;
import com.joanflo.utils.Regex;

public class SizesController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public SizesController(Activity activity) {
		this.activity = activity;
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(String route, int statusCode, JSONObject jObject, JSONArray jArray) {
		try {
			if (statusCode == HttpStatusCode.REQUEST_TIMEOUT) {
				throw new Exception();
			}
			
			if (route.matches("sizes/" + Regex.INTEGER)) {
				// GET <URLbase>/sizes/{idSize}
				if (jObject != null) {
					// size
					Size size = new Size(jObject);
					
					if (activity instanceof ProductActivity) {
						ProductActivity productActivity = (ProductActivity) activity;
						productActivity.sizeReceived(size);
					} else if (activity instanceof PurchaseDetailListActivity) {
						PurchaseDetailListActivity purchaseDetailListActivity = (PurchaseDetailListActivity) activity;
						purchaseDetailListActivity.sizeReceived(size);
					}
				}
			}
			
		} catch (Exception e) {
			Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_request), Toast.LENGTH_SHORT).show();
			activity.finish();
		}
	}
	

	
	/**
	 * Get size
	 * @param idSize
	 */
	public void getSize(int idSize) {
		// GET <URLbase>/sizes/{idSize}
		client.getSize(this, idSize);
	}
	
}
