package com.joanflo.models;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;
import com.joanflo.utils.Time;

/**
 * Wish model
 * @author Joanflo
 */
public class Wish {

	
	// primary key
	private int idWish;
	
	// foreign keys
	private Product product;
	private User user;
	
	// wish info
	private Timestamp date;
	
	
	
	/**
	 * Wish model constructor
	 * @param product
	 * @param user
	 * @param date
	 */
	public Wish(Product product, User user, Timestamp date) {
		this.product = product;
		this.user = user;
		this.date = date;
	}
	
	/**
	 * Wish model constructor
	 * @param product
	 * @param user
	 */
	public Wish(Product product, User user) {
		this.product = product;
		this.user = user;
		long time = System.currentTimeMillis();
		this.date = new java.sql.Timestamp(time);
	}
	
	/**
	 * Wish model constructor
	 * @param jWish
	 * @param lang
	 * @throws JSONException
	 */
	public Wish(JSONObject jWish, String lang) throws JSONException {
		// id wish
		this.idWish = jWish.getInt("idWish");
		// product
		int idProduct = jWish.getInt("idProduct");
		if (jWish.has("name_" + lang)) {
			CharSequence name = jWish.getString("name_" + lang);
			this.product = new Product(idProduct, null, null, name, "", "", "");
			// front image
			if (jWish.has("url")) {
				String url = jWish.getString("url");
				CharSequence description = jWish.getString("description_" + lang);
				ProductImage frontImage = new ProductImage(url, ProductImage.TYPE_FRONT);
				frontImage.setProduct(this.product);
				frontImage.setDescription(description);
				// add front image to product
				product.addImage(frontImage);
			}
		} else {
			this.product = new Product(idProduct, null, null, "", "", "", "");
		}
		// user
		if (jWish.has("userEmail")) {
			String userEmail = jWish.getString("userEmail");
			this.user = new User(userEmail, null, null, "", "", "", 0, "", "", "");
		}
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