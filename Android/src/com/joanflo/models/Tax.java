package com.joanflo.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Tax {

	
	// discount type constants
	public static final char DISCOUNT_PERCENT = '%';
	public static final char DISCOUNT_MONEY = '§';
	
	// primary key
	private int idTax;
	
	// foreign keys
	private Product product;
	private Country country;
	
	// tax info
	private double basePrice;
	private int iva;
	private double discount;
	private char discountType;
	
	
	
	public Tax(int idTax, Product product, Country country, double basePrice, int iva, double discount, char discountType) {
		this.idTax = idTax;
		this.product = product;
		this.country = country;
		this.basePrice = basePrice;
		this.iva = iva;
		this.discount = discount;
		this.discountType = discountType;
	}
	
	public Tax(JSONObject jTax) throws JSONException {
		// tax id
		this.idTax = jTax.getInt("idTax");
		// product
		int idProduct = jTax.getInt("idProduct");
		this.product = new Product(idProduct, null, null, "", "", "", "");
		// country
		String countryName = jTax.getString("countryName");
		this.country = new Country(countryName, null, 0, ' ');
		// base price
		this.basePrice = jTax.getDouble("basePrice");
		// IVA
		this.iva = jTax.getInt("IVA");
		// discount
		this.discount = jTax.getDouble("discount");
		this.discountType = jTax.getString("discountType").charAt(0);
	}



	public int getIdTax() {
		return idTax;
	}



	public Product getProduct() {
		return product;
	}


	public Country getCountry() {
		return country;
	}


	public double getBasePrice() {
		return basePrice;
	}
	
	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}


	public int getIva() {
		return iva;
	}

	public void setIva(int iva) {
		this.iva = iva;
	}


	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}


	public char getDiscountType() {
		return discountType;
	}
	
	public void setDiscountType(char discountType) {
		this.discountType = discountType;
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tax) {
			Tax tax = (Tax) obj;
			return idTax == tax.getIdTax();
		} else {
			return false;
		}
	}
	
	
}