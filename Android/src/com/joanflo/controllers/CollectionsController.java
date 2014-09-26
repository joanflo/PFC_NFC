package com.joanflo.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.Toast;
import com.joanflo.models.Collection;
import com.joanflo.network.HttpStatusCode;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.ProductActivity;
import com.joanflo.tagit.R;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.Regex;

/**
 * Collections controller class
 * @author Joanflo
 */
public class CollectionsController {

	
	private RESTClient client;
	private Activity activity;
	
	
	
	/**
	 * Collections controller constructor
	 * @param activity
	 */
	public CollectionsController(Activity activity) {
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
			
			if (route.matches("collections/" + Regex.INTEGER)) {
				// GET <URLbase>/collections/{idCollection}
				if (jObject != null) {
					// get language code
					String lang = LocalStorage.getInstance().getLocaleLanguage(activity);
					// collection
					Collection collection = new Collection(jObject, lang);
					
					if (activity instanceof ProductActivity) {
						ProductActivity productActivity = (ProductActivity) activity;
						productActivity.collectionReceived(collection);
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
	 * Get collection
	 * @param idCollection
	 */
	public void getCollection(int idCollection) {
		// GET <URLbase>/collections/{idCollection}
		client.getCollection(this, idCollection);
	}
	
	
}
