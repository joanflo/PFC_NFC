package com.joanflo.models;

import java.sql.Timestamp;


public class Achievement {

	
	// primary & foreign keys
	private Badge badge;
	private User user;
	
	// achievement info
	private Timestamp date;
	
	
	
	// achievement from database
	public Achievement(Badge badge, User user, Timestamp date) {
		this.badge = badge;
		this.user = user;
		this.date = date;
	}
	
	
	
	// new achievement
	public Achievement(Badge badge, User user) {
		this.badge = badge;
		this.user = user;
		long time = System.currentTimeMillis();
		this.date = new java.sql.Timestamp(time);
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