package com.joanflo.adapters;

import java.net.URL;
import android.content.Context;


public class ProductListItem implements Comparable<ProductListItem> {

	
	private URL url;
	private CharSequence description;
	private CharSequence productName;
	private CharSequence brandName;
	private CharSequence categoryName;
	private CharSequence price;
	private CharSequence coin;
	private CharSequence rating;
	
	// product sort
	public static final int SORTBY_NAME = 0;
	public static final int SORTBY_PRICEASCENDING = 1;
	public static final int SORTBY_PRICEDESCENDING = 2;
	public static final int SORTBY_BRAND = 3;
	public static final int SORTBY_RATING = 4;
	private static int sortCriteria = SORTBY_NAME;
	
	
	
	public ProductListItem(Context context, URL url, CharSequence description, 
			CharSequence productName, CharSequence brandName, CharSequence categoryName, 
			CharSequence price, CharSequence coin, CharSequence rating) {
		
		this.url = url;
		this.description = description;
		this.productName = productName;
		this.brandName = brandName;
		this.categoryName = categoryName;
		this.price = price;
		this.coin = coin;
		this.rating = rating;
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


	public CharSequence getProductName() {
		return productName;
	}

	public void setProductName(CharSequence productName) {
		this.productName = productName;
	}


	public CharSequence getBrandName() {
		return brandName;
	}

	public void setBrandName(CharSequence brandName) {
		this.brandName = brandName;
	}


	public CharSequence getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(CharSequence categoryName) {
		this.categoryName = categoryName;
	}


	public CharSequence getPrice() {
		return price;
	}

	public void setPrice(CharSequence price) {
		this.price = price;
	}


	public CharSequence getCoin() {
		return coin;
	}

	public void setCoin(CharSequence coin) {
		this.coin = coin;
	}


	public CharSequence getRating() {
		return rating;
	}

	public void setRating(CharSequence rating) {
		this.rating = rating;
	}

	
	public static void setSortCriteria(int sortCriteria) {
		ProductListItem.sortCriteria = sortCriteria;
	}


	@Override
	public int compareTo(ProductListItem another) {
		String str1, str2;
		
		switch (ProductListItem.sortCriteria) {
		case SORTBY_NAME:
			str1 = (String) this.productName;
			str2 = (String) another.getProductName();
			return str1.compareTo(str2);
			
		case SORTBY_PRICEASCENDING:
			str1 = (String) this.price;
			str2 = (String) another.getPrice();
			return str1.compareTo(str2);
			
		case SORTBY_PRICEDESCENDING:
			str1 = (String) this.price;
			str2 = (String) another.getPrice();
			return str2.compareTo(str1);
			
		case SORTBY_BRAND:
			str1 = (String) this.brandName;
			str2 = (String) another.getBrandName();
			return str1.compareTo(str2);
			
		case SORTBY_RATING:
			str1 = (String) this.rating;
			str2 = (String) another.getRating();
			return str2.compareTo(str1);
			
		default:
			return 0;
		}
	}
	
	
}