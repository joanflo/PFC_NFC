package com.joanflo.models;

import org.json.JSONException;
import org.json.JSONObject;

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
	
	public PurchaseDetail(JSONObject jPurchaseDetail) throws JSONException {
		// id purchase detail
		this.idPurchaseDetail = jPurchaseDetail.getInt("idPurchaseDetail");
		// purchase
		int idPurchase = jPurchaseDetail.getInt("idPurchase");
		this.purchase = new Purchase(idPurchase, null, 'f');
		// batch
		Product product = null;
		Shop shop = null;
		if (jPurchaseDetail.has("idProduct") && jPurchaseDetail.has("idShop")) {
			int idProduct = jPurchaseDetail.getInt("idProduct");
			product = new Product(idProduct, null, null, "", "", "", "");
			int idShop = jPurchaseDetail.getInt("idShop");
			shop = new Shop(idShop, null, "", "", "", "", 0, 0);
		}
		int idBatch = jPurchaseDetail.getInt("idBatch");
		this.batch = new Batch(idBatch, product, null, null, shop, 0);
		// units
		this.units = jPurchaseDetail.getInt("units");
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