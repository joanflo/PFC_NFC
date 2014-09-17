package com.joanflo.utils;

import java.util.List;

import com.joanflo.models.Batch;
import com.joanflo.models.Category;
import com.joanflo.models.Product;
import com.joanflo.models.ProductImage;
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
	
	
	
	public static Batch searchBatchByIdSize(int idSize, List<Batch> batches) {
		Batch batch = null;
		int i = 0;
		boolean found = false;
		while (i < batches.size() && !found) {
			batch = batches.get(i);
			if (batch.getSize().getIdSize() == idSize && batch.getSize().getSize() == 0) {
				found = true;
			}
			i++;
		}
		return batch;
	}
	
	
	
	public static Batch searchBatchByColorCode(CharSequence colorCode, List<Batch> batches) {
		Batch batch = null;
		int i = 0;
		boolean found = false;
		while (i < batches.size() && !found) {
			batch = batches.get(i);
			if (batch.getColor().getColorCode().equals(colorCode) && batch.getColor().getName() == null) {
				found = true;
			}
			i++;
		}
		return batch;
	}
	
	
}