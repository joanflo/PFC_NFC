package com.joanflo.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.joanflo.utils.Time;

/**
 * Purchase model
 * @author Joanflo
 */
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
	
	
	
	/**
	 * Purchase model constructor
	 * @param idPurchase
	 * @param user
	 * @param status
	 * @param date
	 */
	public Purchase(int idPurchase, User user, char status, Timestamp date) {
		this.idPurchase = idPurchase;
		this.user = user;
		this.purchaseDetails = new ArrayList<PurchaseDetail>();
		this.status = status;
		this.date = date;
	}
	
	/**
	 * Purchase model constructor
	 * @param idPurchase
	 * @param user
	 * @param purchaseDetails
	 * @param status
	 * @param date
	 */
	public Purchase(int idPurchase, User user, List<PurchaseDetail> purchaseDetails, char status, Timestamp date) {
		this.idPurchase = idPurchase;
		this.user = user;
		this.purchaseDetails = purchaseDetails;
		this.status = status;
		this.date = date;
	}
	
	/**
	 * Purchase model constructor
	 * @param idPurchase
	 * @param user
	 * @param status
	 */
	public Purchase(int idPurchase, User user, char status) {
		this.idPurchase = idPurchase;
		this.user = user;
		this.purchaseDetails = new ArrayList<PurchaseDetail>();
		this.status = status;
		long time = System.currentTimeMillis();
		this.date = new java.sql.Timestamp(time);
	}
	
	/**
	 * Purchase model constructor
	 * @param jPurchase
	 * @throws JSONException
	 */
	public Purchase(JSONObject jPurchase) throws JSONException {
		this.idPurchase = jPurchase.getInt("idPurchase");
		this.user = new User(jPurchase);
		this.purchaseDetails = new ArrayList<PurchaseDetail>();
		this.status = jPurchase.getString("status").charAt(0);
		this.date = Time.convertStringToTimestamp(jPurchase.getString("date"));
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