package com.joanflo.models;

public class PurchaseDetail {

	
	// primary & foreign keys
	private int idPurchaseDetail;
	private Purchase purchase;
	private Batch batch;
	
	// purchase detail info
	private int units;
	
	
	
	// new purchase detail from database
	public PurchaseDetail(int idPurchaseDetail, Purchase purchase, Batch batch, int units) {
		this.idPurchaseDetail = idPurchaseDetail;
		this.purchase = purchase;
		this.batch = batch;
		this.units = units;
	}
	
	
	
	// new purchase detail
	public PurchaseDetail(Purchase purchase, Batch batch, int units) {
		this.purchase = purchase;
		this.batch = batch;
		this.units = units;
	}


	
	public int getIdPurchaseDetail() {
		return idPurchaseDetail;
	}
	

	public Purchase getPurchase() {
		return purchase;
	}


	public Batch getBatch() {
		return batch;
	}


	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}
	
	
}