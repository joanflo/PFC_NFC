package com.joanflo.models;

/**
 * RelatedProduct model
 * @author Joanflo
 */
public class RelatedProduct {

	
	// primary & foreign keys
	private Product productA;
	private Product productB;
	
	
	
	/**
	 * RelatedProduct model constructor
	 * @param productA
	 * @param productB
	 */
	public RelatedProduct(Product productA, Product productB) {
		this.productA = productA;
		this.productB = productB;
	}



	public Product getProductA() {
		return productA;
	}


	public Product getProductB() {
		return productB;
	}
	
	
}