package com.joanflo.models;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import com.joanflo.utils.Time;

public class Friendship {

	// primary & foreign keys
	private User userFollowing;
	private User userFollower;
	
	// friendship info
	private Timestamp date;
	
	

	// friendship from database
	public Friendship(User userFollowing, User userFollower, Timestamp date) {
		this.userFollowing = userFollowing;
		this.userFollower = userFollower;
		this.date = date;
	}
	
	// new friendship
	public Friendship(User userFollowing, User userFollower) {
		this.userFollowing = userFollowing;
		this.userFollower = userFollower;
		long time = System.currentTimeMillis();
		this.date = new java.sql.Timestamp(time);
	}
	
	public Friendship(JSONObject jFriendship) throws JSONException {
		// emails
		String emailFollowing = jFriendship.getString("emailFollowed");
		this.userFollowing = new User(emailFollowing, null, null, "", "", "", 0, "", "", "");
		String emailFollower = jFriendship.getString("emailFollower");
		this.userFollower = new User(emailFollower, null, null, "", "", "", 0, "", "", "");
		// date
		this.date = Time.convertStringToTimestamp(jFriendship.getString("date"));
	}

	

	public User getUserFollowing() {
		return userFollowing;
	}

	
	public User getUserFollower() {
		return userFollower;
	}

	
	public Timestamp getDate() {
		return date;
	}
	
	
}
