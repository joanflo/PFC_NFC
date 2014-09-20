package com.joanflo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;

public class AssetsUtils {

	
	public static final String BADGES_DIRECTORY = "badges/";
	public static final String NFC_DIRECTORY = "nfc/";
	public static final String CATEGORIES_DIRECTORY = "categories/";
	
	private static final String HOST_PATH_IMAGES = "http://alumnes-ltim.uib.es/~jflorit/";
	
	
	
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
	
	
	
	public static Bitmap getImageFromPath(String path) {
		return BitmapFactory.decodeFile(path);
	}
	
	
	public static int convertPixelsToDips(Context context, int pixels) {
		Resources resources = context.getResources();
        float dipValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, resources.getDisplayMetrics());
        resources = null;
        return (int) dipValue;
    }
	
	

    public static int convertDipsToPixels(Context context, int dip) {
        Resources resources = context.getResources();
        float pixelValue = (dip * resources.getDisplayMetrics().density);
        resources = null;
        return (int) pixelValue;
    }
    
    
    public static URL getUrlFromPath(String imagePath) throws MalformedURLException {
    	return new URL(HOST_PATH_IMAGES + imagePath);
    }
	
	
}