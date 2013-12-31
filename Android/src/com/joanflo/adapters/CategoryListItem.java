package com.joanflo.adapters;

import com.joanflo.utils.AssetsUtils;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class CategoryListItem {

	
	private int idCategory;
	private Drawable thumb;
	private CharSequence name;
	private CharSequence itemsNumber;
	
	
	
	public CategoryListItem(Context context, int idCategory, CharSequence name, int itemsNumber) {
		this.idCategory = idCategory;
		this.thumb = AssetsUtils.getImageFromAssets(context, AssetsUtils.CATEGORIES_DIRECTORY, String.valueOf(idCategory));
		this.name = name;
		this.itemsNumber = String.valueOf(itemsNumber);
	}


	
	public int getIdCategory() {
		return idCategory;
	}
	

	public Drawable getThumb() {
		return thumb;
	}

	public void setThumb(Drawable thumb) {
		this.thumb = thumb;
	}


	public CharSequence getName() {
		return name;
	}

	public void setName(CharSequence name) {
		this.name = name;
	}


	public CharSequence getItemsNumber() {
		return itemsNumber;
	}

	public void setItemsNumber(CharSequence itemsNumber) {
		this.itemsNumber = itemsNumber;
	}
	

}
