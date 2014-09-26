package com.joanflo.adapters;

import java.sql.Timestamp;

import com.joanflo.utils.Time;

/**
 * Purchase item class
 * @author Joanflo
 * @see PurchaseListAdapter
 */
public class PurchaseListItem {

	
	private int purchaseId;
	private CharSequence date;
	private CharSequence totalPrice;
	private char coin;
	private CharSequence totalItems;
	private CharSequence shopDirection;
	
	
	
	/**
	 * Purchase list item constructor
	 * @param purchaseId
	 * @param date
	 * @param totalPrice
	 * @param coin
	 * @param totalItems
	 * @param shopDirection
	 */
	public PurchaseListItem(int purchaseId, Timestamp date, CharSequence totalPrice, char coin, CharSequence totalItems, CharSequence shopDirection) {
		this.purchaseId = purchaseId;
		this.date = Time.convertTimestampToString(date);
		this.totalPrice = totalPrice;
		this.coin = coin;
		this.totalItems = totalItems;
		this.shopDirection = shopDirection;
	}
	
	
	
	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}
	
	
	public CharSequence getDate() {
		return date;
	}

	public void setDate(CharSequence date) {
		this.date = date;
	}


	public CharSequence getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(CharSequence totalPrice) {
		this.totalPrice = totalPrice;
	}


	public char getCoin() {
		return coin;
	}

	public void setCoin(char coin) {
		this.coin = coin;
	}


	public CharSequence getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(CharSequence totalItems) {
		this.totalItems = totalItems;
	}


	public CharSequence getShopDirection() {
		return shopDirection;
	}

	public void setShopDirection(CharSequence shopDirection) {
		this.shopDirection = shopDirection;
	}
	
	
}