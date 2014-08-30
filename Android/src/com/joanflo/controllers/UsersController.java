package com.joanflo.controllers;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import com.joanflo.models.Achievement;
import com.joanflo.models.PurchaseDetail;
import com.joanflo.models.Review;
import com.joanflo.models.User;
import com.joanflo.models.Wish;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.LoginActivity;
import com.joanflo.tagit.RegistrationActivity;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.SimpleCrypto;

public class UsersController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public UsersController(Activity activity) {
		this.activity = activity;
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(JSONObject jObject) {
		try {
		
		
			if (activity instanceof LoginActivity) {
				LoginActivity loginActivity = (LoginActivity) activity;
				Boolean successful = jObject.getBoolean("successful");
				if (successful) {
					LocalStorage.getInstance().setUserLoged(activity, successful);
				}
				loginActivity.loginReceived(successful);
				
			} else if (activity instanceof RegistrationActivity) {
				RegistrationActivity registrationActivity = (RegistrationActivity) activity;
				User user = new User(jObject);
				registrationActivity.userCreated(user);
				
			}
			
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Login user
	 * @param userEmail
	 * @param password
	 */
	public void logInUser(CharSequence userEmail, String password) {
		try {
			// encrypt password
			password = SimpleCrypto.encrypt(SimpleCrypto.MASTER_KEY, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		client.logInUser(this, userEmail, password);
	}
	
	
	
	/**
	 * Create a new user
	 * @param userEmail
	 * @param cityName
	 * @param languageName
	 * @param nick
	 * @param name
	 * @param surname
	 * @param age
	 * @param password
	 * @param phone
	 * @param direction
	 */
	public void signInUser(CharSequence userEmail, CharSequence cityName, CharSequence languageName, CharSequence nick,
			CharSequence name, CharSequence surname, int age, CharSequence password, CharSequence phone, CharSequence direction) {
		client.signInUser(this, userEmail, cityName, languageName, nick, name, surname, age, password, phone, direction);
	}
	
	
	
	/**
	 * Update user's data
	 * @param user
	 */
	public void updateUserData(User user) {
		client.updateUserData(this, user);
	}
	
	
	
	/**
	 * Change user's password
	 * @param userEmail
	 * @param password
	 */
	public void changeUserPassword(CharSequence userEmail, CharSequence password) {
		client.changeUserPassword(this, userEmail, password);
	}
	
	
	
	/**
	 * Get current user
	 * @param userEmail
	 */
	public void getCurrentUser(CharSequence userEmail) {
		client.getCurrentUser(this, userEmail);
	}
	
	
	
	/**
	 * Get user without returning any personal information
	 * @param userEmail
	 */
	public void getAnotherUser(CharSequence userEmail) {
		client.getAnotherUser(this, userEmail);
	}
	
	
	
	/**
	 * Get followers
	 * @param userEmail
	 */
	public void getFollowers(CharSequence userEmail) {
		client.getFollowers(this, userEmail);
	}
	
	
	
	/**
	 * Get users being followed
	 * @param userEmail
	 */
	public void getFollowing(CharSequence userEmail) {
		client.getFollowing(this, userEmail);
	}
	
	
	
	/**
	 * Follow user
	 * @param userEmailFollowing
	 * @param userEmailFollowed
	 */
	public void followUser(CharSequence userEmailFollowing, CharSequence userEmailFollowed) {
		client.followUser(this, userEmailFollowing, userEmailFollowed);
	}
	
	
	
	/**
	 * Unfollow user
	 * @param userEmailFollowing
	 * @param userEmailFollowed
	 */
	public void unfollowUser(CharSequence userEmailFollowing, CharSequence userEmailFollowed) {
		client.unfollowUser(this, userEmailFollowing, userEmailFollowed);
	}
	
	
	
	/**
	 * Get user's achievements
	 * @param userEmail
	 */
	public void getAchievements(CharSequence userEmail) {
		client.getAchievements(this, userEmail);
	}
	
	
	
	/**
	 * Create a new achievement
	 * @param achievement
	 */
	public void createAchievement(Achievement achievement) {
		client.createAchievement(this, achievement);
	}
	
	
	
	/**
	 * Get user's achievement by id
	 * @param userEmail
	 * @param idAchievement
	 */
	public void getAchievement(CharSequence userEmail, int idAchievement) {
		client.getAchievement(this, userEmail, idAchievement);
	}
	
	
	
	/**
	 * Get user's reviews
	 * @param userEmail
	 */
	public void getReviews(CharSequence userEmail) {
		client.getReviews(this, userEmail);
	}
	
	
	
	/**
	 * Create a new review
	 * @param review
	 */
	public void createReview(Review review) {
		client.createReview(this, review);
	}
	
	
	
	/**
	 * Get review
	 * @param userEmail
	 * @param idComment
	 */
	public void getReview(CharSequence userEmail, int idComment) {
		client.getReview(this, userEmail, idComment);
	}
	
	
	
	/**
	 * Get user's wishes
	 * @param userEmail
	 */
	public void getWishes(CharSequence userEmail) {
		client.getWishes(this, userEmail);
	}
	
	
	
	/**
	 * Create wish
	 * @param wish
	 */
	public void createWish(Wish wish) {
		client.createWish(this, wish);
	}
	
	
	
	/**
	 * Get user's wish
	 * @param userEmail
	 * @param idWish
	 */
	public void getWish(CharSequence userEmail, int idWish) {
		client.getWish(this, userEmail, idWish);
	}
	
	
	
	/**
	 * Delete user's wish
	 * @param userEmail
	 * @param idWish
	 */
	public void deleteWish(CharSequence userEmail, int idWish) {
		client.deleteWish(this, userEmail, idWish);
	}
	
	
	
	/**
	 * Get user's purchases
	 * @param userEmail
	 */
	public void getPurchases(CharSequence userEmail) {
		client.getPurchases(this, userEmail);
	}
	
	
	
	/**
	 * Get user's purchase
	 * @param userEmail
	 * @param idPurchase
	 */
	public void getPurchase(CharSequence userEmail, int idPurchase) {
		client.getPurchase(this, userEmail, idPurchase);
	}
	
	
	
	/**
	 * Get user's cart
	 * @param userEmail
	 */
	public void getCartPurchase(CharSequence userEmail) {
		client.getCartPurchase(this, userEmail);
	}
	
	
	
	/**
	 * Get purchase details
	 * @param userEmail
	 * @param idPurchase
	 */
	public void getPurchaseDetails(CharSequence userEmail, int idPurchase) {
		client.getPurchaseDetails(this, userEmail, idPurchase);
	}
	
	
	
	/**
	 * Get purchase detail
	 * @param userEmail
	 * @param idPurchase
	 * @param idPurchaseDetail
	 */
	public void getPurchaseDetail(CharSequence userEmail, int idPurchase, int idPurchaseDetail) {
		client.getPurchaseDetail(this, userEmail, idPurchase, idPurchaseDetail);
	}
	
	
	
	/**
	 * Create a new purchase detail added to the current purchase (STATUS_PENDING).
	 * If doesn't exists, it's created.
	 * @param userEmail
	 * @param detail
	 */
	public void createPurchaseDetail(CharSequence userEmail, PurchaseDetail detail) {
		client.createPurchaseDetail(this, userEmail, detail);
	}
	
	
	
	/**
	 * Update purchase detail
	 * @param userEmail
	 * @param detail
	 */
	public void updatePurchaseDetail(CharSequence userEmail, PurchaseDetail detail) {
		client.updatePurchaseDetail(this, userEmail, detail);
	}
	
	
	
	/**
	 * Delete the given purchase detail from to the current purchase (STATUS_PENDING).
	 * If the current purchase is empty, it's deleted.
	 * @param userEmail
	 * @param idPurchase
	 * @param idPurchaseDetail
	 */
	public void deletePurchaseDetail(CharSequence userEmail, int idPurchase, int idPurchaseDetail) {
		client.deletePurchaseDetail(this, userEmail, idPurchase, idPurchaseDetail);
	}
	
	
}
