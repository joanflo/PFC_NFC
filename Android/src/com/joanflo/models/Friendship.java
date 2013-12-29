package com.joanflo.models;

import java.sql.Timestamp;

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
