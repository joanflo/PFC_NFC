package com.joanflo.adapters;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import com.joanflo.models.Product;
import com.joanflo.utils.AssetsUtils;
import com.joanflo.utils.Time;

public class ReviewListItem {


	private URL url;
	private CharSequence description;
	private CharSequence date;
	private CharSequence rating;
	private CharSequence comment;
	
	
	
	public ReviewListItem(Product product, Timestamp date, float rating, CharSequence comment) {
		this(date, rating, comment);
		this.description = product.getName();
		this.url = product.getFrontImage().getUrl();
	}
	
	public ReviewListItem(CharSequence userEmail, CharSequence nick, Timestamp date, float rating, CharSequence comment) {
		this(date, rating, comment);
		this.description = userEmail;
		try {
			String imagePath = "profiles/" + nick + ".jpg";
			this.url = AssetsUtils.getUrlFromPath(imagePath);
		} catch (MalformedURLException e) {
			this.url = null;
		}
		this.comment = "@" + nick + ": " + this.comment;
	}

	public ReviewListItem(Timestamp date, float rating, CharSequence comment) {
		this.date = Time.convertTimestampToString(date);
		setRating(rating);
		this.comment = comment;
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
	
	
	public CharSequence getDate() {
		return date;
	}
	
	public void setDate(Timestamp date) {
		this.date = date.toString();
	}
	
	
	public CharSequence getRating() {
		return rating;
	}
	
	public void setRating(float rating) {
		DecimalFormat df = new DecimalFormat("0.00");
    	this.rating = df.format(rating);
	}
	
	
	public CharSequence getComment() {
		return comment;
	}
	
	public void setComment(CharSequence comment) {
		this.comment = comment;
	}
	
	
}