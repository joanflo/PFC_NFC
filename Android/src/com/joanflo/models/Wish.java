package com.joanflo.models;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import com.joanflo.utils.Time;

public class Wish {

	
	// primary key
	private int idWish;
	
	// foreign keys
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
	
	public Wish(JSONObject jWish) throws JSONException {
		// id wish
		this.idWish = jWish.getInt("idWish");
		// product
		int idProduct = jWish.getInt("idProduct");
		this.product = new Product(idProduct, null, null, "", "", "", "");
		// user
		String userEmail = jWish.getString("userEmail");
		this.user = new User(userEmail, null, null, "", "", "", 0, "", "", "");
		// date
		this.date = Time.convertStringToTimestamp(jWish.getString("date"));
	}
	
	
	
	public int getIdWish() {
		return idWish;
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