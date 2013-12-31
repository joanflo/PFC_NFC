package com.joanflo.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class AssetsUtils {

	
	public static final String BADGES_DIRECTORY = "badges/";
	public static final String BRANDS_DIRECTORY = "brands/";
	public static final String FLAGS_DIRECTORY = "flags/";
	public static final String CATEGORIES_DIRECTORY = "categories/";
	
	
	
	public static Drawable getImageFromAssets(Context context, String imageType, String imageName){
	    Drawable d = null;
	    InputStream myInput = null;
	    
	    try {
	    	if (imageName == null){
	    		return null;
	    	}
	    	Log.i("DEBUG", imageName);
	    	String srcName = imageType + imageName + ".png";
	        myInput = context.getAssets().open(srcName);
	        d = Drawable.createFromStream(myInput, srcName);
	        myInput.close();
	        myInput = null;
	    } catch (IOException ex) {
	    	myInput = null;
	    }
	    
	    return d;
	}
	
	
	
}