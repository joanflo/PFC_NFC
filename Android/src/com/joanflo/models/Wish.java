package com.joanflo.models;

import java.sql.Timestamp;

public class Wish {

	
	// primary & foreign keys
	private Product product;
	private User user;
	
	// wish info
	private Timestamp date;
	
	
	
	// wish from database
	public Wish(Product product, User user, Timestamp date) {
		this.product = product;
		this.user = user;
		this.date = date;
	}
	
	
	
	// new wish
	public Wish(Product product, User user) {
		this.product = product;
		this.user = user;
		long time = System.currentTimeMillis();
		this.date = new java.sql.Timestamp(time);
	}
	
	
	
	public Product getProduct() {
		return product;
	}
	
	
	public User getUser() {
		return user;
	}
	

	public Timestamp getDate() {
		return date;
	}
	
	
}