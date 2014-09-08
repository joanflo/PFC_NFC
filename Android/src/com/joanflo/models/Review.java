package com.joanflo.models;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import com.joanflo.utils.Time;

public class Review {

	
	// primary key
	private int idComment;
	
	// foreign keys
	private Product product;
	private User user;
	
	// review info
	private float rating; // [0..5]
	private CharSequence comment;
	private Timestamp date;
	
	
	
	// review from database
	public Review(int idComment, Product product, User user, float rating, CharSequence comment, Timestamp date) {
		this.idComment = idComment;
		this.product = product;
		this.user = user;
		this.rating = rating;
		this.comment = comment;
		this.date = date;
	}
	
	// new review
	public Review(int idComment, Product product, User user, float rating, CharSequence comment) {
		this.idComment = idComment;
		this.product = product;
		this.user = user;
		this.rating = rating;
		this.comment = comment;
		long time = System.currentTimeMillis();
		this.date = new java.sql.Timestamp(time);
	}
	
	public Review(JSONObject jReview) throws JSONException {
		// id comment
		this.idComment = jReview.getInt("idComment");
		// product
		int idProduct = jReview.getInt("idProduct");
		this.product = new Product(idProduct, null, null, "", "", "", "");
		// user
		String userEmail = jReview.getString("userEmail");
		this.user = new User(userEmail, null, null, "", "", "", 0, "", "", "");
		// rating
		this.rating = (float) jReview.getDouble("rating");
		// comment
		this.comment = jReview.getString("comment");
		// date
		this.date = Time.convertStringToTimestamp(jReview.getString("date"));
	}



	public int getIdComment() {
		return idComment;
	}


	public Product getProduct() {
		return product;
	}


	public User getUser() {
		return user;
	}


	public float getRating() {
		return rating;
	}
	
	public void setRating(float rating) {
		this.rating = rating;
	}


	public CharSequence getComment() {
		return comment;
	}
	
	public void setComment(CharSequence comment) {
		this.comment = comment;
	}


	public Timestamp getDate() {
		return date;
	}
	
	
}