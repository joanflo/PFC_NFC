package com.joanflo.adapters;

import com.joanflo.utils.AssetsUtils;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class BadgeListItem {

	
	private Drawable thumb;
	private CharSequence description;
	
	
	
	public BadgeListItem(Context context, CharSequence badgeName, CharSequence description) {
		this.thumb = AssetsUtils.getImageFromAssets(context, AssetsUtils.BADGES_DIRECTORY, String.valueOf(badgeName));
		this.description = description;
	}

	
	
	public Drawable getThumb() {
		return thumb;
	}
	
	public void setThumb(Drawable thumb) {
		this.thumb = thumb;
	}
	
	public CharSequence getDescription() {
		return description;
	}
	
	public void setDescription(CharSequence description) {
		this.description = description;
	}
	
}
