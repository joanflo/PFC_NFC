package com.joanflo.utils;

import java.util.List;

import com.joanflo.models.Category;
import com.joanflo.models.Product;
import com.joanflo.models.ProductImage;
import com.joanflo.models.Review;
import com.joanflo.models.User;

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
		
		if (found) {
			return cat;
		} else {
			return null;
		}
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
		
		if (found) {
			return prod;
		} else {
			return null;
		}
	}
	
	
	
	public static Product searchProductReviewById(int id, List<Review> reviews) {
		if (id == 0) {
			return null;
		}
		
		Product prod = null;
		int i = 0;
		boolean found = false;
		while (i < reviews.size() && !found) {
			prod = reviews.get(i).getProduct();
			if (prod.getIdProduct() == id) {
				found = true;
			}
			i++;
		}
		
		if (found) {
			return prod;
		} else {
			return null;
		}
	}
	
	
	
	public static User searchUserByEmail(CharSequence email, List<User> users) {
		if (email == null) {
			return null;
		}
		
		User user = null;
		int i = 0;
		boolean found = false;
		while (i < users.size() && !found) {
			user = users.get(i);
			if (user.getUserEmail().equals(email)) {
				found = true;
			}
			i++;
		}
		
		if (found) {
			return user;
		} else {
			return null;
		}
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
		
		if (found) {
			return img;
		} else {
			return null;
		}
	}
	
	
}