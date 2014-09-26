package com.joanflo.models;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import com.joanflo.utils.Time;

/**
 * Achievement model
 * @author Joanflo
 */
public class Achievement {

	
	// primary key
	int idAchievement;
	
	// foreign keys
	private Badge badge;
	private User user;
	
	// achievement info
	private Timestamp date;
	
	
	
	/**
	 * Achievement model constructor
	 * @param badge
	 * @param user
	 * @param date
	 */
	public Achievement(Badge badge, User user, Timestamp date) {
		this.badge = badge;
		this.user = user;
		this.date = date;
	}
	
	/**
	 * Achievement model constructor
	 * @param badge
	 * @param user
	 */
	public Achievement(Badge badge, User user) {
		this.badge = badge;
		this.user = user;
		long time = System.currentTimeMillis();
		this.date = new java.sql.Timestamp(time);
	}
	
	/**
	 * Achievement model constructor
	 * @param jAchievement
	 * @param lang
	 * @throws JSONException
	 */
	public Achievement(JSONObject jAchievement, String lang) throws JSONException {
		// id achievement
		this.idAchievement = jAchievement.getInt("idAchievement");
		// badge
		String badgeName = jAchievement.getString("badgeName");
		this.badge = new Badge(badgeName, "");
		if (jAchievement.has("description_" + lang)) {
			String description = jAchievement.getString("description_" + lang);
			this.badge.setDescription(description);
		}
		// user
		String userEmail = jAchievement.getString("userEmail");
		this.user = new User(userEmail, null, null, "", "", "", 0, "", "", "");
		// date
		this.date = Time.convertStringToTimestamp(jAchievement.getString("date"));
	}
	
	
	
	public Badge getBadge() {
		return badge;
	}
	
	
	public User getUser() {
		return user;
	}
	

	public Timestamp getDate() {
		return date;
	}
	
	
}