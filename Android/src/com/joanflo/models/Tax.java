package com.joanflo.models;

public class Tax {

	
	// discount type constants
	public static final char DISCOUNT_PERCENT = '%';
	public static final char DISCOUNT_MONEY = '§';
	
	
	// primary & foreign keys
	private Product product;
	private Country country;
	
	// tax info
	private double basePrice;
	private int iva;
	private double discount;
	private char discountType;
	
	
	
	public Tax(Product product, Country country, double basePrice, int iva, double discount, char discountType) {
		this.product = product;
		this.country = country;
		this.basePrice = basePrice;
		this.iva = iva;
		this.discount = discount;
		this.discountType = discountType;
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
	
	
}