package com.joanflo.utils;

import java.util.List;

import com.joanflo.models.Category;
import com.joanflo.models.Product;
import com.joanflo.models.ProductImage;

public class SearchUtils {
	
	
	public static Category searchCategoryById(int id, List<Category> categories) {
		if (id == 0) {
			return null;
		}
		
		Category cat = null;
		int i = 0;
		boolean found = false;
		while (i < categories.size() && !found) {
			cat = categories.get(i);
			if (cat.getIdCategory() == id) {
				found = true;
			}
			i++;
		}
		return cat;
	}
	
	
	
	public static Product searchProductById(int id, List<Product> products) {
		if (id == 0) {
			return null;
		}
		
		Product prod = null;
		int i = 0;
		boolean found = false;
		while (i < products.size() && !found) {
			prod = products.get(i);
			if (prod.getIdProduct() == id) {
				found = true;
			}
			i++;
		}
		return prod;
	}
	
	
	
	public static ProductImage searchFrontImage(Product product) {
		List<ProductImage> images = product.getImages();
		
		ProductImage img = null;
		int i = 0;
		boolean found = false;
		while (i < images.size() && !found) {
			img = images.get(i);
			if (img.getType() == ProductImage.TYPE_FRONT) {
				found = true;
			}
			i++;
		}
		return img;
	}
	
	
}