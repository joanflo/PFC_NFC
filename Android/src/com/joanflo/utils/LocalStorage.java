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

/**
 * Class for managing the shared preferences for this app
 * @author Joanflo
 */
public class LocalStorage {

	
	private static final String FILE_NAME = "data.tagit";
	private User user;
	private Shop shop;
	private Category currentCategory;
	
	
	
	// Singleton pattern
	private static LocalStorage instance = null;
	
	/**
	 * Exists only to defeat instantiation from any other classes.
	 */
	private LocalStorage() {
		user = null;
		shop = null;
		currentCategory = null;
	}
	
	/**
	 * @return the singleton instance for this class
	 */
	public static LocalStorage getInstance() {
		if (instance == null) {
			instance = new LocalStorage();
		}
		return instance;
	}
	
	
	
	/**
	 * Get profile image URI or null if it isn't set
	 * @param activity
	 * @return
	 */
	public Uri getProfileImage(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		String uri = prefs.getString("profileImage", null);
		if (uri == null) {
			return null;
		} else {
			return Uri.parse(uri);
		}
	}
	
	/**
	 * Set profile image URI
	 * @param activity
	 * @param uri
	 */
	public void setProfileImage(Activity activity, Uri uri) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("profileImage", uri.toString());
		editor.commit();
	}
	
	
	
	/**
	 * @param activity
	 * @return true if user is logged; false otherwise
	 */
	public boolean isUserLogged(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getBoolean("userLoged", false);
	}
	
	/**
	 * Set if the user is logged
	 * @param activity
	 * @param loged
	 */
	public void setUserLogged(Activity activity, boolean loged) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("userLoged", loged);
		editor.commit();
		if (!loged) {
			user = null;
		}
	}
	
	
	
	/**
	 * Get user logged
	 * @param activity
	 * @return
	 */
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
	
	/**
	 * Save user logged
	 * @param activity
	 * @param user
	 */
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
	
	/**
	 * Get user logged nick
	 * @param activity
	 * @return
	 */
	public CharSequence getUserNick(Activity activity) {
		if (user == null) {
			user = getUser(activity);
		}
		return user.getNick();
	}
	
	/**
	 * Get user logged points number
	 * @param activity
	 * @return
	 */
	public int getUserPoints(Activity activity) {
		if (user == null) {
			user = getUser(activity);
		}
		return user.getPoints();
	}
	
	/**
	 * Get user logged email
	 * @param activity
	 * @return
	 */
	public CharSequence getUserEmail(Activity activity) {
		if (user == null) {
			user = getUser(activity);
		}
		return user.getUserEmail();
	}
	
	
	
	/**
	 * Get followers number
	 * @param activity
	 * @return
	 */
	public int getFollowersCount(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getInt("followersCount", 0);
	}
	
	/**
	 * Set followers number
	 * @param activity
	 * @param followersCount
	 */
	public void setFollowersCount(Activity activity, int followersCount) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("followersCount", followersCount);
		editor.commit();
	}
	
	/**
	 * Update followers number
	 * @param activity
	 * @param increase
	 */
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
	
	
	
	/**
	 * Get following number
	 * @param activity
	 * @return
	 */
	public int getFollowingCount(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getInt("followingCount", 0);
	}
	
	/**
	 * Set following number
	 * @param activity
	 * @param followingCount
	 */
	public void setFollowingCount(Activity activity, int followingCount) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("followingCount", followingCount);
		editor.commit();
	}
	
	/**
	 * Update following number
	 * @param activity
	 * @param increase
	 */
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
	
	
	
	/**
	 * true if shop is picked; false otherwise
	 * @param activity
	 * @return
	 */
	public boolean isShopPicked(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getBoolean("shopPicked", false);
	}
	
	/**
	 * Set current shop
	 * @param activity
	 * @param picked
	 */
	public void setShopPicked(Activity activity, boolean picked) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("shopPicked", picked);
		editor.commit();
		if (!picked) {
			shop = null;
		}
	}
	
	
	
	/**
	 * Get current shop
	 * @param activity
	 * @return
	 */
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
	
	/**
	 * Delete current shop
	 * @param activity
	 */
	public void deleteShop(Activity activity) {
		setShopPicked(activity, false);
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		// delete shop
		editor.remove("shop");
		editor.commit();
		this.shop = null;
	}
	
	/**
	 * Set current shop
	 * @param activity
	 * @param shop
	 */
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
	
	/**
	 * Get current shop location (direction, city name)
	 * @param activity
	 * @return
	 */
	public CharSequence getShopLocation(Activity activity) {
		if (shop == null) {
			shop = getShop(activity);
		}
		return shop.getDirection() + "\n" + shop.getCity().getCityName();
	}
	
	
	
	public String getLastDate(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getString("lastDate", null);
	}
	
	public void saveLastDate(Activity activity, String date) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("lastDate", date);
		editor.commit();
	}
	
	
	public int getConsecutiveDaysNumber(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getInt("consecutiveDaysNumber", 0);
	}
	
	public void setConsecutiveDaysNumber(Activity activity, int consecutiveDays) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("consecutiveDaysNumber", consecutiveDays);
		editor.commit();
	}
	
	
	public int getSharesNumber(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getInt("sharesNumber", 0);
	}
	
	public void setSharesNumber(Activity activity, int sharesNumber) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("sharesNumber", sharesNumber);
		editor.commit();
	}
	
	
	public int getReviewsNumber(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getInt("reviewsNumber", 0);
	}
	
	public void setReviewsNumber(Activity activity, int reviewsNumber) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("reviewsNumber", reviewsNumber);
		editor.commit();
	}
	
	
	public boolean productHasBeenSharedViaNFC(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getBoolean("sharedViaNFC", false);
	}
	
	public void setProductSharedViaNFC(Activity activity, boolean shared) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("sharedViaNFC", shared);
		editor.commit();
	}
	
	
	
	/**
	 * Get cart items number
	 * @param activity
	 * @return
	 */
	public int getCartItemsCount(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getInt("cartItemsCount", 0);
	}
	
	/**
	 * Set cart items number
	 * @param activity
	 * @param cartItemsCount
	 */
	public void setCartItemsCount(Activity activity, int cartItemsCount) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("cartItemsCount", cartItemsCount);
		editor.commit();
	}
	
	/**
	 * Update cart items number
	 * @param activity
	 * @param increase
	 */
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
	
	
	
	/**
	 * Get wish list items number
	 * @param activity
	 * @return
	 */
	public int getWishlistItemsCount(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		return prefs.getInt("wishlistItemsCount", 0);
	}
	
	/**
	 * Set wish list items number
	 * @param activity
	 * @param wishlistItemsCount
	 */
	public void setWishlistItemsCount(Activity activity, int wishlistItemsCount) {
		SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("wishlistItemsCount", wishlistItemsCount);
		editor.commit();
	}
	
	/**
	 * Update wish list items number
	 * @param activity
	 * @param increase
	 */
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
	
	
	
	/**
	 * Get current category
	 * @return
	 */
	public Category getCurrentCategory() {
		return currentCategory;
	}
	
	/**
	 * Set current category
	 * @param currentCategory
	 */
	public void setCurrentCategory(Category currentCategory) {
		this.currentCategory = currentCategory;
	}
	
	
	
	@SuppressLint("CommitPrefEdits")
	/**
	 * Delete all shared preferences (log out)
	 * @param activity
	 */
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
		editor.remove("lastDate");
		editor.remove("consecutiveDaysNumber");
		editor.remove("sharesNumber");
		editor.remove("reviewsNumber");
		editor.remove("sharedViaNFC");
		editor.commit();
	}
	
	
	
	/**
	 * Get locale country
	 * @param activity
	 * @return
	 */
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
	/**
	 * Get user's language; otherwise locale language
	 * @param activity
	 * @return
	 */
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
