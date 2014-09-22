package com.joanflo.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	public Product(JSONObject jProduct, String lang) throws JSONException {
		// id product
		this.idProduct = jProduct.getInt("idProduct");
		// brand
		if (jProduct.has("brandName")) {
			this.brand = new Brand(jProduct.getString("brandName"));
		}
		// collection
		if (jProduct.has("idCollection")) {
			this.collection = new Collection(jProduct.getInt("idCollection"));
		}
		// reviews
		this.reviews = new ArrayList<Review>();
		// taxes
		this.taxes = new ArrayList<Tax>();
		// batches
		this.batches = new ArrayList<Batch>();
		// related products
		this.relatedProducts = new ArrayList<Product>();
		// product images
		this.images = new ArrayList<ProductImage>();
		// categories
		this.categories = new ArrayList<Category>();
		// name
		this.name = jProduct.getString("name_" + lang);
		// description
		if (jProduct.has("description_" + lang)) {
			this.description = jProduct.getString("description_" + lang);
		}
		// reference
		if (jProduct.has("reference")) {
			this.reference = jProduct.getString("reference");
		}
		// composition
		if (jProduct.has("composition_" + lang)) {
			this.composition = jProduct.getString("composition_" + lang);
		}
		// url
		if (jProduct.has("url")) {
			this.addImage(new ProductImage(jProduct.getString("url"), ProductImage.TYPE_FRONT));
		}
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
	
	public List<Batch> getBatches(Shop shop) {
		if (shop == null) {
			return batches;
		}
		
		List<Batch> shopBatches = new ArrayList<Batch>();
		Iterator<Batch> it = batches.iterator();
		while (it.hasNext()) {
			Batch batch = it.next();
			if (shop.equals(batch.getShop())) {
				shopBatches.add(batch);
			}
		}
		return shopBatches;
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
	
	public ProductImage getFrontImage() {
		boolean found = false;
		ProductImage frontImage = null;
		Iterator<ProductImage> it = images.iterator();
		while (it.hasNext() && !found) {
			ProductImage img = (ProductImage) it.next();
			if (img.getType() == ProductImage.TYPE_FRONT) {
				found = true;
				frontImage = img;
			}
		}
		return frontImage;
	}
	
	public List<ProductImage> getRegularImages() {
		List<ProductImage> regularImages = new ArrayList<ProductImage>();
		Iterator<ProductImage> it = images.iterator();
		while (it.hasNext()) {
			ProductImage img = (ProductImage) it.next();
			if (img.getType() == ProductImage.TYPE_REGULAR) {
				regularImages.add(img);
			}
		}
		return regularImages;
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
	
	
	public Tax searchTax(Country country) {
		int i = 0;
		boolean found = false;
		Tax tax = null;
		
		while (i < taxes.size() && !found) {
			tax = taxes.get(i);
			if (tax.getCountry().equals(country)) {
				found = true;
			}
			i++;
		}
		
		return tax;
	}
	
	
	public double calculatePrice(Tax tax) {
		double basePrice = tax.getBasePrice();
		int iva = tax.getIva();
		double discount = tax.getDiscount();
		char discountType = tax.getDiscountType();
		
		double ivaMoney = basePrice * (iva / 100);
		double discountMoney = 0;
		if (discountType == Tax.DISCOUNT_MONEY) {
			discountMoney = discount;
		} else if (discountType == Tax.DISCOUNT_PERCENT) {
			discountMoney = (basePrice + ivaMoney) * (discount / 100);
		}
		
		// return final price (round to 2 decimals)
		double finalPrice = basePrice + ivaMoney - discountMoney;
		return Math.rint(finalPrice * 100) / 100; 
	}
	
	
	public double calculateAverageRating() {
		int n = reviews.size();
		if (n == 0) {
			// there's no reviews
			return -1;
		}
		
		double accumulated = 0;
		for (int i = 0; i < n; i++) {
			accumulated += reviews.get(i).getRating();
		}
		
		// round to 2 decimals
		double finalAverage = accumulated / n;
		return Math.rint(finalAverage * 100) / 100;
	}
	
	
	public boolean equals(Object obj) {
		if (obj instanceof Product) {
			Product product = (Product) obj;
			return idProduct == product.getIdProduct(); 
		} else {
			return false;
		}
	}
	
	
}