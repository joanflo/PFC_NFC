package com.joanflo.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Purchase {

	
	// status constants
	public static final char STATUS_FINISHED = 'f';
	public static final char STATUS_PENDING = 'p';
	
	
	// primary key
	private int idPurchase;
	
	// foreign key
	private User user;
	private List<PurchaseDetail> purchaseDetails;
	
	// purchase info
	private char status;
	private Timestamp date;
	
	
	
	// purchase from database
	public Purchase(int idPurchase, User user, char status, Timestamp date) {
		this.idPurchase = idPurchase;
		this.user = user;
		this.purchaseDetails = new ArrayList<PurchaseDetail>();
		this.status = status;
		this.date = date;
	}
	
	
	
	// purchase from database and purchase detail
	public Purchase(int idPurchase, User user, List<PurchaseDetail> purchaseDetails, char status, Timestamp date) {
		this.idPurchase = idPurchase;
		this.user = user;
		this.purchaseDetails = purchaseDetails;
		this.status = status;
		this.date = date;
	}
	
	
	
	// new purchase
	public Purchase(int idPurchase, User user, char status) {
		this.idPurchase = idPurchase;
		this.user = user;
		this.purchaseDetails = new ArrayList<PurchaseDetail>();
		this.status = status;
		long time = System.currentTimeMillis();
		this.date = new java.sql.Timestamp(time);
	}



	public int getIdPurchase() {
		return idPurchase;
	}
	
	
	public User getUser() {
		return user;
	}
	
	
	public List<PurchaseDetail> getPurchaseDetails() {
		return purchaseDetails;
	}
	
	public void addPurchaseDetail(PurchaseDetail purchaseDetail) {
		purchaseDetails.add(purchaseDetail);
	}
	

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	
	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	
}