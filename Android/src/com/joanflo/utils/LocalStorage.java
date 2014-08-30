package com.joanflo.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import com.joanflo.models.User;

public class LocalStorage {

	
	private static final String FILE_NAME = "user.tagit";
	
	
	
	// Singleton pattern
	private static LocalStorage instance = null;
	
	private LocalStorage() {
		// Exists only to defeat instantiation from any other classes.
	}
	
	public static LocalStorage getInstance() {
		if (instance == null) {
			instance = new LocalStorage();
		}
		return instance;
	}
	
	
	
	
	public boolean isUserLoged(Activity activity) {
		SharedPreferences prefs = activity.getPreferences(Context.MODE_PRIVATE);
		return prefs.getBoolean("userLoged", false);
	}
	
	
	
	public void setUserLoged(Activity activity, boolean loged) {
		SharedPreferences prefs = activity.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("userLoged", loged);
		editor.commit();
	}
	
	
	
	public User getUser(Activity activity) {
		SharedPreferences prefs = activity.getPreferences(Context.MODE_PRIVATE);
		String strUser = prefs.getString("user", null);
		User user = null;
		try {
			JSONObject jUser = new JSONObject(strUser);
			user = new User(jUser);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	
	
	public void saveUser(Activity activity, User user) {
		SharedPreferences prefs = activity.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		// save user as JSON object
		JSONObject jUser = user.convertToJSON();
		String strUser = jUser.toString();
		editor.putString("user", strUser);
		editor.commit();
	}
	

	
	

	public void writeFile(Activity activity, User user) {
		if (isExternalStorageWritable()) {
			try {
				FileOutputStream fos = activity.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
				ObjectOutputStream os = new ObjectOutputStream(fos);
				
				String str = new String("holaa");
				
				os.writeObject(str);
				os.close();
			} catch (FileNotFoundException e) {
				Log.i("excepció", "FileNotFoundException");
			} catch (IOException e) {
				Log.i("excepció", "IOException");
			}
		}
	}
	
	
	
	public String readFile(Activity activity) {
		String str = "";
		if (isExternalStorageReadable()) {
			try {
				FileInputStream fis = activity.openFileInput(FILE_NAME);
				ObjectInputStream is = new ObjectInputStream(fis);
				str = (String) is.readObject();
				is.close();
			} catch (FileNotFoundException e) {
				Log.i("excepció", "FileNotFoundException");
			} catch (IOException e) {
				Log.i("excepció", "IOException");
			} catch (ClassNotFoundException e) {
				Log.i("excepció", "ClassNotFoundException");
			}
		}
		return str;
	}
	
	
	/**
	 * Checks if external storage is available for read and write
	 * @return availability
	 */
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	
	
	/**
	 * Checks if external storage is available to at least read
	 * @return availability
	 */
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	
}
