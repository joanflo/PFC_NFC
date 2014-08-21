package com.joanflo.adapters;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import com.joanflo.models.Product;
import com.joanflo.models.ProductImage;
import com.joanflo.models.User;
import com.joanflo.network.RESTClient;
import com.joanflo.utils.Time;

public class ReviewListItem {


	private URL urlThumb;
	private CharSequence description;
	private CharSequence date;
	private CharSequence rating;
	private CharSequence comment;
	
	
	
	public ReviewListItem(Product product, Timestamp date, float rating, CharSequence comment) {
		this(date, rating, comment);
		this.description = product.getName();
		ProductImage img = product.getFrontImage();
		this.urlThumb = img.getUrl();
	}
	
	public ReviewListItem(User user, Timestamp date, float rating, CharSequence comment) {
		this(date, rating, comment);
		this.description = user.getName();
		try {
			this.urlThumb = new URL(RESTClient.HOST + "profile_images/" + user.getNick() + ".jpg");
		} catch (MalformedURLException e) {
			this.urlThumb = null;
		}
	}

	public ReviewListItem(Timestamp date, float rating, CharSequence comment) {
		this.date = Time.convertTimestampToString(date);
		setRating(rating);
		this.comment = comment;
	}
	
	
	
	public URL getUrlThumb() {
		return urlThumb;
	}

	public void setUrlThumb(URL urlThumb) {
		this.urlThumb = urlThumb;
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