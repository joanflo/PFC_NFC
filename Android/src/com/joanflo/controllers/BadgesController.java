package com.joanflo.controllers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.Toast;
import com.joanflo.models.Badge;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.BadgeActivity;
import com.joanflo.tagit.BadgeListActivity;
import com.joanflo.tagit.R;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.Regex;

public class BadgesController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public BadgesController(Activity activity) {
		this.activity = activity;
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(String route, int statusCode, JSONObject jObject, JSONArray jArray) {
		// get language code
		String lang = LocalStorage.getInstance().getLocaleLanguage(activity);
		
		try {
			
			if (route.matches("badges")) {
				// GET <URLbase>/badges
				if (jArray != null) {
					// list of badges
					List<Badge> badges = processBadges(jArray, lang);
					
					if (activity instanceof BadgeListActivity) {
						BadgeListActivity badgeListActivity = (BadgeListActivity) activity;
						badgeListActivity.badgesReceived(badges);
					}
				}
				
			} else if (route.matches("badges/" + Regex.SPECIAL_TEXT)) {
				// GET <URLbase>/badges/{badgeName}
				if (jObject != null) {
					// badge
					Badge badge = new Badge(jObject, lang);
					
					if (activity instanceof BadgeActivity) {
						BadgeActivity badgeActivity = (BadgeActivity) activity;
						badgeActivity.badgeReceived(badge);
					}
				}
			}
			
		} catch (JSONException e) {
			Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_request), Toast.LENGTH_SHORT).show();
		}
	}
	
	
	
	/**
	 * Get all database badges
	 */
	public void getBadges() {
		// GET <URLbase>/badges
		client.getBadges(this);
	}
	
	
	
	/**
	 * Get a single badge
	 * @param badgeName
	 */
	public void getBadge(CharSequence badgeName) {
		// GET <URLbase>/badges/{badgeName}
		client.getBadge(this, badgeName);
	}
	
	
	
	private List<Badge> processBadges(JSONArray jBadges, String lang) throws JSONException {
		List<Badge> badges = new ArrayList<Badge>(jBadges.length());
		
		// for each badge JSON object
		for (int i = 0; i < jBadges.length(); i++) {
			// create Badge model from JSON object
			JSONObject jBadge = jBadges.getJSONObject(i);
			Badge badge = new Badge(jBadge, lang);
			badges.add(badge);
		}
		
		return badges;
	}
	
	
}
