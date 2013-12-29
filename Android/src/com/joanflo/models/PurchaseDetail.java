package com.joanflo.models;

public class PurchaseDetail {

	
	// primary & foreign keys
	private Purchase purchase;
	private Batch batch;
	
	// purchase detail info
	private int units;
	
	
	
	public PurchaseDetail(Purchase purchase, Batch batch, int units) {
		this.purchase = purchase;
		this.batch = batch;
		this.units = units;
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