package com.joanflo.utils;

import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.joanflo.models.Category;
import com.joanflo.models.Country;
import com.joanflo.models.Shop;
import com.joanflo.models.User;

public class LocalStorage {

	
	private static final String FILE_NAME = "data.tagit";
	private User user;
	private Shop shop;
	private Category currentCategory;
	
	
	
	// Singleton pattern
	private static LocalStorage instance = null;
	
	private LocalStorage() {
		user = null;
		// Exists only to defeat instantiation from any other classes.
	}
	
	public static LocalStorage getInstance() {
		if (instance == null) {
			instance = new LocalStorage();
		}
		return instance;
	}
	
	
	
	
	public Uri getProfileImage(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		String uri = prefs.getString("profileImage", null);
		if (uri == null) {
			return null;
		} else {
			return Uri.parse(uri);
		}
	}
	
	public void setProfileImage(Activity activity, Uri uri) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("profileImage", uri.toString());
		editor.commit();
	}
	
	
	
	public boolean isUserLoged(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getBoolean("userLoged", false);
	}
	
	public void setUserLoged(Activity activity, boolean loged) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("userLoged", loged);
		editor.commit();
		if (!loged) {
			user = null;
		}
	}
	
	
	
	public User getUser(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		String strUser = prefs.getString("user", null);
		if (strUser == null) {
			return null;
		}
		user = null;
		try {
			JSONObject jUser = new JSONObject(strUser);
			user = new User(jUser);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public void saveUser(Activity activity, User user) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		// save user as JSON object
		JSONObject jUser = user.convertToJSON();
		String strUser = jUser.toString();
		editor.putString("user", strUser);
		editor.commit();
		this.user = user;
	}
	
	public CharSequence getUserNick(Activity activity) {
		if (user == null) {
			user = getUser(activity);
		}
		return user.getNick();
	}
	
	public int getUserPoints(Activity activity) {
		if (user == null) {
			user = getUser(activity);
		}
		return user.getPoints();
	}
	
	public CharSequence getUserEmail(Activity activity) {
		if (user == null) {
			user = getUser(activity);
		}
		return user.getUserEmail();
	}
	
	
	
	public int getFollowersCount(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getInt("followersCount", 0);
	}
	
	public void setFollowersCount(Activity activity, int followersCount) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("followersCount", followersCount);
		editor.commit();
	}
	
	public void updateFollowersCount(Activity activity, boolean increase) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		int followersCount = getFollowersCount(activity);
		if (increase) {
			followersCount++;
		} else {
			followersCount--;
		}
		editor.putInt("followersCount", followersCount);
		editor.commit();
	}
	
	
	
	public int getFollowingCount(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getInt("followingCount", 0);
	}
	
	public void setFollowingCount(Activity activity, int followingCount) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("followingCount", followingCount);
		editor.commit();
	}
	
	public void updateFollowingCount(Activity activity, boolean increase) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		int followingCount = getFollowingCount(activity);
		if (increase) {
			followingCount++;
		} else {
			followingCount--;
		}
		editor.putInt("followingCount", followingCount);
		editor.commit();
	}
	
	
	
	public boolean isShopPicked(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getBoolean("shopPicked", false);
	}
	
	public void setShopPicked(Activity activity, boolean picked) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("shopPicked", picked);
		editor.commit();
		if (!picked) {
			shop = null;
		}
	}
	
	
	
	public Shop getShop(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		String strShop = prefs.getString("shop", null);
		shop = null;
		try {
			JSONObject jShop = new JSONObject(strShop);
			shop = new Shop(jShop);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return shop;
	}
	
	public void deleteShop(Activity activity) {
		setShopPicked(activity, false);
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		// delete shop
		editor.remove("shop");
		editor.commit();
		this.shop = null;
	}
	
	public void saveShop(Activity activity, Shop shop) {
		setShopPicked(activity, true);
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		// save shop as JSON object
		JSONObject jShop = shop.convertToJSON();
		String strShop = jShop.toString();
		editor.putString("shop", strShop);
		editor.commit();
		this.shop = shop;
	}
	
	public CharSequence getShopLocation(Activity activity) {
		if (shop == null) {
			shop = getShop(activity);
		}
		return shop.getDirection() + "\n" + shop.getCity().getCityName();
	}
	
	
	
	public int getCartItemsCount(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getInt("cartItemsCount", 0);
	}
	
	public void setCartItemsCount(Activity activity, int cartItemsCount) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("cartItemsCount", cartItemsCount);
		editor.commit();
	}
	
	public void updateCartItemsCount(Activity activity, boolean increase) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		int cartItemsCount = getCartItemsCount(activity);
		if (increase) {
			cartItemsCount++;
		} else {
			cartItemsCount--;
		}
		editor.putInt("cartItemsCount", cartItemsCount);
		editor.commit();
	}
	
	
	
	public int getWishlistItemsCount(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getInt("wishlistItemsCount", 0);
	}
	
	public void setWishlistItemsCount(Activity activity, int wishlistItemsCount) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("wishlistItemsCount", wishlistItemsCount);
		editor.commit();
	}
	
	public void updateWishlistItemsCount(Activity activity, boolean increase) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		int wishlistItemsCount = getWishlistItemsCount(activity);
		if (increase) {
			wishlistItemsCount++;
		} else {
			wishlistItemsCount--;
		}
		editor.putInt("wishlistItemsCount", wishlistItemsCount);
		editor.commit();
	}
	
	
	
	public Category getCurrentCategory() {
		return currentCategory;
	}
	
	public void setCurrentCategory(Category currentCategory) {
		this.currentCategory = currentCategory;
	}
	
	
	
	@SuppressLint("CommitPrefEdits")
	public void deleteStorage(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.remove("profileImage");
		editor.remove("userLoged");
		editor.remove("user");
		editor.remove("followersCount");
		editor.remove("followingCount");
		editor.remove("shopPicked");
		editor.remove("shop");
		editor.remove("cartItemsCount");
		editor.remove("wishlistItemsCount");
		editor.commit();
	}
	
	
	
	public Country getLocaleCountry(Activity activity) {
		// default values
		CharSequence countryName = "Espanya";
		int code = 34;
		char coin = '€';
		// get country code
		String strCode = activity.getResources().getConfiguration().locale.getCountry();
		if (strCode.equals("ES")) {
			countryName = "Espanya";
			code = 34;
			coin = Country.EURO;
		} else if (strCode.equals("GB")) {
			countryName = "England";
			code = 44;
			coin = Country.POUND;
		}
		return new Country(countryName, null, code, coin);
	}
	
	
	
	@SuppressLint("DefaultLocale")
	public String getLocaleLanguage(Activity activity) {
		if (user == null) {
			user = getUser(activity);
		}
		if (user != null) {
			CharSequence languageName = user.getLanguage().getLanguageName();
			// "Català" ----> "ca"
			// "English" ---> "en"
			return languageName.subSequence(0, 2).toString().toLowerCase();
		}
		String lang = activity.getResources().getConfiguration().locale.getLanguage();
		// available language?
		if (!lang.equals("en") && !lang.equals("ca")) {
			// by default
			lang = "en";
		}
		return lang;
	}
	
	
}
