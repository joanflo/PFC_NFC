package com.joanflo.adapters;

import java.net.URL;

/**
 * Purchase detail item class
 * @author Joanflo
 * @see PurchaseDetailListAdapter
 */
public class PurchaseDetailListItem {

	
	private boolean viewingCart;
	private int units;
	private int size;
	private CharSequence colorCode;
	private CharSequence colorName;
	private int productId;
	private CharSequence productName;
	private URL url;
	private CharSequence description;
	private CharSequence totalPrice;
	private CharSequence coin;
	
	
	
	/**
	 * Purchase detail list item constructor
	 * @param units
	 * @param productId
	 * @param productName
	 * @param url
	 * @param description
	 * @param totalPrice
	 * @param coin
	 * @param size
	 * @param colorCode
	 * @param colorName
	 */
	public PurchaseDetailListItem(int units, int productId, CharSequence productName,
			URL url, CharSequence description, CharSequence totalPrice, char coin,
			int size, CharSequence colorCode, CharSequence colorName) {
		viewingCart = false;
		
		this.units = units;
		this.productId = productId;
		this.productName = productName;
		this.url = url;
		this.description = description;
		this.totalPrice = totalPrice;
		this.coin = String.valueOf(coin);
		this.size = size;
		this.colorCode = colorCode;
		this.colorName = colorName;
	}
	
	
	/**
	 * Purchase detail list item constructor
	 * @param units
	 * @param productId
	 * @param productName
	 * @param url
	 * @param description
	 * @param totalPrice
	 * @param coin
	 */
	public PurchaseDetailListItem(int units, int productId, CharSequence productName,
			URL url, CharSequence description, CharSequence totalPrice, char coin) {
		viewingCart = true;
		
		this.units = units;
		this.productId = productId;
		this.productName = productName;
		this.url = url;
		this.description = description;
		this.totalPrice = totalPrice;
		this.coin = String.valueOf(coin);
	}
	
	
	
	public boolean isViewingCart() {
		return viewingCart;
	}

	public void setViewingCart(boolean viewingCart) {
		this.viewingCart = viewingCart;
	}


	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}


	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}


	public CharSequence getColorCode() {
		return colorCode;
	}

	public void setColorCode(CharSequence colorCode) {
		this.colorCode = colorCode;
	}


	public CharSequence getColorName() {
		return colorName;
	}

	public void setColorName(CharSequence colorName) {
		this.colorName = colorName;
	}


	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}


	public CharSequence getProductName() {
		return productName;
	}

	public void setProductName(CharSequence productName) {
		this.productName = productName;
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


	public CharSequence getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(CharSequence totalPrice) {
		this.totalPrice = totalPrice;
	}


	public CharSequence getCoin() {
		return coin;
	}

	public void setCoin(char coin) {
		this.coin = String.valueOf(coin);
	}
	
	
}