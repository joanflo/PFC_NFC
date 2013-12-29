package com.joanflo.models;

import java.util.ArrayList;
import java.util.List;

public class Product {

	
	// primary keys
	private int idProduct;
	
	// foreign keys
	private Brand brand;
	private Collection collection;
	private List<Review> reviews;
	private List<Tax> taxes;
	private List<Batch> batches;
	private List<Product> relatedProducts;
	private List<ProductImage> images;
	private List<Category> categories;
	
	// product info
	private CharSequence name;
	private CharSequence description;
	private CharSequence reference;
	private CharSequence composition;
	
	
	
	public Product(int idProduct, Brand brand, Collection collection, List<Review> reviews, 
			List<Tax> taxes, List<Batch> batches, List<Product> relatedProducts, 
			List<ProductImage> images, List<Category> categories, CharSequence name,
			CharSequence description, CharSequence reference, CharSequence composition) {
		
		this.idProduct = idProduct;
		this.brand = brand;
		this.collection = collection;
		this.reviews = reviews;
		this.taxes = taxes;
		this.batches = batches;
		this.relatedProducts = relatedProducts;
		this.images = images;
		this.categories = categories;
		this.name = name;
		this.description = description;
		this.reference = reference;
		this.composition = composition;
	}
	
	
	
	public Product(int idProduct, Brand brand, Collection collection, CharSequence name,
			CharSequence description, CharSequence reference, CharSequence composition) {
		
		this.idProduct = idProduct;
		this.brand = brand;
		this.collection = collection;
		this.reviews = new ArrayList<Review>();
		this.taxes = new ArrayList<Tax>();
		this.batches = new ArrayList<Batch>();
		this.relatedProducts = new ArrayList<Product>();
		this.images = new ArrayList<ProductImage>();
		this.categories = new ArrayList<Category>();
		this.name = name;
		this.description = description;
		this.reference = reference;
		this.composition = composition;
	}
	
	
	
	public int getIdProduct() {
		return idProduct;
	}
	
	
	public Brand getBrand() {
		return brand;
	}
	
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	
	public Collection getCollection() {
		return collection;
	}
	
	public void setCollection(Collection collection) {
		this.collection = collection;
	}
	
	
	public List<Review> getReviews() {
		return reviews;
	}
	
	public void addReview(Review review) {
		reviews.add(review);
	}
	
	
	public List<Tax> getTaxes() {
		return taxes;
	}
	
	public void addTax(Tax tax) {
		taxes.add(tax);
	}
	
	
	public List<Batch> getBatches() {
		return batches;
	}
	
	public void addBatch(Batch batch) {
		batches.add(batch);
	}
	
	
	public List<Product> getRelatedProducts() {
		return relatedProducts;
	}
	
	public void addRelatedProduct(Product relatedProduct) {
		relatedProducts.add(relatedProduct);
	}
	
	
	public List<ProductImage> getImages() {
		return images;
	}
	
	public void addImage(ProductImage image) {
		images.add(image);
	}
	
	
	public List<Category> getCategories() {
		return categories;
	}
	
	public void addCategory(Category category) {
		categories.add(category);
	}
	
	
	public CharSequence getName() {
		return name;
	}
	
	public void setName(CharSequence name) {
		this.name = name;
	}
	
	
	public CharSequence getDescription() {
		return description;
	}
	
	public void setDescription(CharSequence description) {
		this.description = description;
	}
	
	
	public CharSequence getReference() {
		return reference;
	}
	
	public void setReference(CharSequence reference) {
		this.reference = reference;
	}
	
	
	public CharSequence getComposition() {
		return composition;
	}
	
	public void setComposition(CharSequence composition) {
		this.composition = composition;
	}
	
	
}