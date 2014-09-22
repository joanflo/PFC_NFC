package com.joanflo.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.Toast;
import com.joanflo.models.Collection;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.ProductActivity;
import com.joanflo.tagit.R;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.Regex;

public class CollectionsController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public CollectionsController(Activity activity) {
		this.activity = activity;
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(String route, int statusCode, JSONObject jObject, JSONArray jArray) {
		try {
			
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
			
		} catch (JSONException e) {
			Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_request), Toast.LENGTH_SHORT).show();
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
