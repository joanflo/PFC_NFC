package com.joanflo.adapters;

import java.net.URL;
import java.sql.Timestamp;

import com.joanflo.utils.Time;

/**
 * Wish item class
 * @author Joanflo
 * @see WishListAdapter
 */
public class WishListItem {

	
	private CharSequence date;
	private CharSequence productName;
	private int productId;
	private URL url;
	private CharSequence description;
	
	
	
	/**
	 * Wish list item constructor
	 * @param date
	 * @param productName
	 * @param productId
	 * @param url
	 * @param description
	 */
	public WishListItem(Timestamp date, CharSequence productName, int productId, URL url, CharSequence description) {
		this.date = Time.convertTimestampToString(date);
		this.productName = productName;
		this.productId = productId;
		this.url = url;
		this.description = description;
	}



	public CharSequence getDate() {
		return date;
	}

	public void setDate(CharSequence date) {
		this.date = date;
	}


	public CharSequence getProductName() {
		return productName;
	}

	public void setProductName(CharSequence productName) {
		this.productName = productName;
	}


	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}


	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}


	public CharSequence getDescription() {
		return description;
	}

	public void setDescription(CharSequence description) {
		this.description = description;
	}
	

}