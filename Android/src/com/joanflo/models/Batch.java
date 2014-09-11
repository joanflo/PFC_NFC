package com.joanflo.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Batch {
	
	
	// primary key
	private int idBatch;
	
	// foreign keys
	private Product product;
	private Size size;
	private Color color;
	private Shop shop;
	
	// batch info
	private int units;
	
	
	
	public Batch(int idBatch, Product product, Size size, Color color, Shop shop, int units) {
		this.idBatch = idBatch;
		this.product = product;
		this.size = size;
		this.color = color;
		this.shop = shop;
		this.units = units;
	}
	
	public Batch(JSONObject jBatch) throws JSONException {
		// id batch
		this.idBatch = jBatch.getInt("idBatch");
		// product
		int idProduct = jBatch.getInt("idProduct");
		this.product = new Product(idProduct, null, null, "", "", "", "");
		// size
		int idSize = jBatch.getInt("idSize");
		this.size = new Size(idSize, 0, 'u', 'o');
		// color
		CharSequence colorCode = jBatch.getString("colorCode");
		this.color = new Color(colorCode, "");;
		// shop
		int idShop = jBatch.getInt("idShop");
		this.shop = new Shop(idShop, null, "", "", "", "", 0, 0);
		// units
		this.units = jBatch.getInt("units");
	}



	public int getIdBatch() {
		return idBatch;
	}


	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}


	public Size getSize() {
		return size;
	}
	
	public void setSize(Size size) {
		this.size = size;
	}


	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}


	public Shop getShop() {
		return shop;
	}
	
	public void setShop(Shop shop) {
		this.shop = shop;
	}


	public int getUnits() {
		return units;
	}
	
	public void setUnits(int units) {
		this.units = units;
	}
	
	
}