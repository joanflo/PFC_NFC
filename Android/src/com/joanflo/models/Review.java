package com.joanflo.models;

import java.sql.Timestamp;

public class Review {

	
	// primary key
	private int idComment;
	
	// foreign keys
	private Product product;
	private User user;
	
	// review info
	private int rating; // [0..5]
	private CharSequence comment;
	private Timestamp date;
	
	
	
	// review from database
	public Review(int idComment, Product product, User user, int rating, CharSequence comment, Timestamp date) {
		this.idComment = idComment;
		this.product = product;
		this.user = user;
		this.rating = rating;
		this.comment = comment;
		this.date = date;
	}
	
	
	
	// new review
	public Review(int idComment, Product product, User user, int rating, CharSequence comment) {
		this.idComment = idComment;
		this.product = product;
		this.user = user;
		this.rating = rating;
		this.comment = comment;
		long time = System.currentTimeMillis();
		this.date = new java.sql.Timestamp(time);
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


	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
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