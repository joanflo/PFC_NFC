package com.joanflo.controllers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.Toast;
import com.joanflo.models.Achievement;
import com.joanflo.models.Purchase;
import com.joanflo.models.PurchaseDetail;
import com.joanflo.models.Review;
import com.joanflo.models.User;
import com.joanflo.models.Wish;
import com.joanflo.network.HttpStatusCode;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.BaseActivity;
import com.joanflo.tagit.FollowsListActivity;
import com.joanflo.tagit.HomeActivity;
import com.joanflo.tagit.LoginActivity;
import com.joanflo.tagit.NewReviewActivity;
import com.joanflo.tagit.PasswordActivity;
import com.joanflo.tagit.ProductActivity;
import com.joanflo.tagit.PurchaseDetailListActivity;
import com.joanflo.tagit.PurchaseListActivity;
import com.joanflo.tagit.R;
import com.joanflo.tagit.RegistrationActivity;
import com.joanflo.tagit.ReviewListActivity;
import com.joanflo.tagit.UpdateUserDataActivity;
import com.joanflo.tagit.UserProfileActivity;
import com.joanflo.tagit.WishListActivity;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.Regex;
import com.joanflo.utils.SimpleCrypto;

public class UsersController {

	
	private RESTClient client;
	private Activity activity;
	
	
	public UsersController(Activity activity) {
		this.activity = activity;
		client = RESTClient.getInstance();
	}
	
	
	
	public synchronized void requestFinished(String route, int statusCode, JSONObject jObject, JSONArray jArray) {
		try {
			if (statusCode == HttpStatusCode.REQUEST_TIMEOUT) {
				throw new Exception();
			}
			
			// get language code
			String lang = LocalStorage.getInstance().getLocaleLanguage(activity);
			
			if (route.matches("users/" + Regex.SPECIAL_TEXT)) {
				// GET <URLbase>/users/{userEmail}
				// GET <URLbase>/users/{userEmail}?fields=password
				// GET <URLbase>/users/{userEmail}?fields=userEmail,nick,points
				// PUT <URLbase>/users/{userEmail}?password={password}&cityName={cityName}&languageName={languageName}&nick={nick}&name={name}&surname={surname}&age={age}&phone={phone}&direction={direction}
				// PUT <URLbase>/users/{userEmail}?cityName={cityName}&languageName={languageName}&nick={nick}&name={name}&surname={surname}&age={age}&phone={phone}&direction={direction}
				// PUT <URLbase>/users/{userEmail}?password={password}
				// PUT <URLbase>/users/{userEmail}?points={points}
				if (jObject != null) {
					// user
					User user;
					if (jObject.has("points")) {
						user = new User(jObject.getJSONObject("points"));
					} else {
						user = new User(jObject);
					}
					
					if (jObject.has("points")) {
						BaseActivity baseActivity = (BaseActivity) activity;
						baseActivity.userPointsUpdated(user.getPoints());
						
					} else if (activity instanceof LoginActivity) {
						LoginActivity loginActivity = (LoginActivity) activity;
						boolean successful = loginActivity.loginReceived(decryptPassword(user));
						// successful login?
						if (successful) {
							LocalStorage.getInstance().setUserLoged(activity, true);
							loginActivity.goToHomeActivity(true);
						}
						
					} else if (activity instanceof HomeActivity) {
						HomeActivity homeActivity = (HomeActivity) activity;
						homeActivity.userReceived(user);
						
					} else if (activity instanceof RegistrationActivity) {
						RegistrationActivity registrationActivity = (RegistrationActivity) activity;
						registrationActivity.userCreated(user);
						
					} else if (activity instanceof PasswordActivity) {
						PasswordActivity passwordActivity = (PasswordActivity) activity;
						passwordActivity.passwordChanged(true);
						
					} else if (activity instanceof UserProfileActivity) {
						UserProfileActivity userProfileActivity = (UserProfileActivity) activity;
						userProfileActivity.userReceived(user);
						
					} else if (activity instanceof UpdateUserDataActivity) {
						UpdateUserDataActivity updateUserDataActivity = (UpdateUserDataActivity) activity;
						updateUserDataActivity.userUpdated(user);
						
					}
					
				} else if (statusCode == HttpStatusCode.NOT_FOUND) {
					// 404
					
					if (activity instanceof LoginActivity) {
						LoginActivity loginActivity = (LoginActivity) activity;
						loginActivity.loginReceived(null);
						
					} else if (activity instanceof RegistrationActivity) {
						RegistrationActivity registrationActivity = (RegistrationActivity) activity;
						registrationActivity.userCreated(null);
						
					} else if (activity instanceof PasswordActivity) {
						PasswordActivity passwordActivity = (PasswordActivity) activity;
						passwordActivity.passwordChanged(false);
						
					} else if (activity instanceof UpdateUserDataActivity) {
						UpdateUserDataActivity updateUserDataActivity = (UpdateUserDataActivity) activity;
						updateUserDataActivity.userUpdated(null);
					}
				}
				
			} else if (route.matches("users/" + Regex.SPECIAL_TEXT + "/reviews")) {
				// GET <URLbase>/users/{userEmail}/reviews
				if (jArray != null) {
					// list of reviews
					List<Review> reviews = processReviews(jArray);
					
					if (activity instanceof ReviewListActivity) {
						ReviewListActivity reviewListActivity = (ReviewListActivity) activity;
						reviewListActivity.reviewsReceived(reviews);
					}
					
				} else if (statusCode == HttpStatusCode.NOT_FOUND) {
					// 404
					
					if (activity instanceof ReviewListActivity) {
						ReviewListActivity reviewListActivity = (ReviewListActivity) activity;
						reviewListActivity.reviewsReceived(new ArrayList<Review>());
					}
				}
				// POST <URLbase>/users/{userEmail}/reviews?idProduct={idProduct}&rating={rating}&comment={comment}
				if (jObject != null) {
					// review
					Review review = new Review(jObject);
					
					if (activity instanceof NewReviewActivity) {
						NewReviewActivity newReviewActivity = (NewReviewActivity) activity;
						newReviewActivity.reviewCreated(review);
					}
				}
				
			} else if (route.matches("users/" + Regex.SPECIAL_TEXT + "/reviews/" + Regex.INTEGER)) {
				// GET <URLbase>/users/{userEmail}/reviews/{idComment}
				if (jObject != null) {
					// review
					//Review review = new Review(jObject);
					
					// TODO
				}
				
			} else if (route.matches("users/" + Regex.SPECIAL_TEXT + "/wishes")) {
				// GET <URLbase>/users/{userEmail}/wishes
				
				if (jArray != null) {
					// list of wishes
					List<Wish> wishes = processWishes(jArray, lang);
					
					if (activity instanceof HomeActivity) {
						HomeActivity homeActivity = (HomeActivity) activity;
						homeActivity.wishlistItemsCountReceived(wishes.size());
					
					} else if (activity instanceof WishListActivity) {
						WishListActivity wishListActivity = (WishListActivity) activity;
						wishListActivity.wishesReceived(wishes);
					}
					
				} else if (statusCode == HttpStatusCode.NOT_FOUND) {
					// 404
					
					if (activity instanceof HomeActivity) {
						HomeActivity homeActivity = (HomeActivity) activity;
						homeActivity.zeroCountReceived();
					
					} else if (activity instanceof WishListActivity) {
						WishListActivity wishListActivity = (WishListActivity) activity;
						wishListActivity.wishesReceived(new ArrayList<Wish>());
					}
				}
				
				// POST <URLbase>/users/{userEmail}/wishes?idProduct={idProduct}
				if (jObject != null) {
					// wish
					Wish wish = new Wish(jObject, lang);
					
					if (activity instanceof ProductActivity) {
						ProductActivity productActivity = (ProductActivity) activity;
						productActivity.productAddedToWishList(wish);
					}
				}
				
			} else if (route.matches("users/" + Regex.SPECIAL_TEXT + "/wishes/" + Regex.INTEGER)) {
				// GET <URLbase>/users/{userEmail}/wishes/{idWish}
				// DELETE <URLbase>/users/{userEmail}/wishes/{idWish}
				if (jObject != null) {
					// wish
					//Wish wish = new Wish(jObject, lang);
					
					// TODO
				} else if (statusCode == HttpStatusCode.NO_CONTENT) {
					// 204
					
					if (activity instanceof WishListActivity) {
						WishListActivity wishListActivity = (WishListActivity) activity;
						wishListActivity.wishRemoved(true);
					}
					
				} else {
					if (activity instanceof WishListActivity) {
						WishListActivity wishListActivity = (WishListActivity) activity;
						wishListActivity.wishRemoved(false);
					}
				}
				
			} else if (route.matches("users/" + Regex.SPECIAL_TEXT + "/purchases")) {
				// GET <URLbase>/users/{userEmail}/purchases
				// GET <URLbase>/users/{userEmail}/purchases?status={Purchase.STATUS_PENDING}
				if (jArray != null) {
					// list of purchases
					List<Purchase> purchases = processPurchases(jArray);
					
					if (activity instanceof HomeActivity) {
						HomeActivity homeActivity = (HomeActivity) activity;
						homeActivity.cartPurchaseReceived(purchases.get(0));
					
					} else if (activity instanceof WishListActivity) {
						WishListActivity wishListActivity = (WishListActivity) activity;
						wishListActivity.cartPurchaseReceived(purchases.get(0));
					
					} else if (activity instanceof PurchaseDetailListActivity) {
						PurchaseDetailListActivity purchaseDetailListActivity = (PurchaseDetailListActivity) activity;
						purchaseDetailListActivity.purchaseReceived(purchases.get(0));
					
					} else if (activity instanceof PurchaseListActivity) {
						PurchaseListActivity purchaseListActivity = (PurchaseListActivity) activity;
						purchaseListActivity.purchasesReceived(purchases);
					
					} else if (activity instanceof ProductActivity) {
						ProductActivity productActivity = (ProductActivity) activity;
						productActivity.cartPurchaseReceived(purchases.get(0));
					}
				} else if (statusCode == HttpStatusCode.NOT_FOUND) {
					// 404
					
					if (activity instanceof HomeActivity) {
						HomeActivity homeActivity = (HomeActivity) activity;
						homeActivity.zeroCountReceived();
					
					} else if (activity instanceof WishListActivity) {
						WishListActivity wishListActivity = (WishListActivity) activity;
						wishListActivity.cartPurchaseReceived(null);
					
					} else if (activity instanceof PurchaseDetailListActivity) {
						PurchaseDetailListActivity purchaseDetailListActivity = (PurchaseDetailListActivity) activity;
						purchaseDetailListActivity.purchaseReceived(null);
					
					} else if (activity instanceof PurchaseListActivity) {
						PurchaseListActivity purchaseListActivity = (PurchaseListActivity) activity;
						purchaseListActivity.purchasesReceived(new ArrayList<Purchase>());
					
					} else if (activity instanceof ProductActivity) {
						ProductActivity productActivity = (ProductActivity) activity;
						productActivity.cartPurchaseReceived(null);
					}
				}
				
			} else if (route.matches("users/" + Regex.SPECIAL_TEXT + "/purchases/" + Regex.INTEGER)) {
				// GET <URLbase>/users/{userEmail}/purchases/{idPurchase}
				if (jObject != null) {
					// purchase
					Purchase purchase = new Purchase(jObject);
					
					 if (activity instanceof PurchaseDetailListActivity) {
						PurchaseDetailListActivity purchaseDetailListActivity = (PurchaseDetailListActivity) activity;
						purchaseDetailListActivity.purchaseReceived(purchase);
					 }
				} else if (statusCode == HttpStatusCode.NOT_FOUND) {
					// 404
					
					if (activity instanceof PurchaseDetailListActivity) {
						PurchaseDetailListActivity purchaseDetailListActivity = (PurchaseDetailListActivity) activity;
						purchaseDetailListActivity.purchaseReceived(null);
					}
				}
				
			} else if (route.matches("users/" + Regex.SPECIAL_TEXT + "/purchases/" + Regex.INTEGER + "/purchase_details")) {
				// GET <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details
				// POST <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details?idBatch={idBatch}&units={units}
				if (jArray != null) {
					if (statusCode == HttpStatusCode.OK) {
						// list of purchase details
						List<PurchaseDetail> purchaseDetails = processPurchaseDetails(jArray);
						
						if (activity instanceof HomeActivity) {
							HomeActivity homeActivity = (HomeActivity) activity;
							homeActivity.cartItemsCountReceived(purchaseDetails.size());
						
						} else if (activity instanceof PurchaseDetailListActivity) {
							PurchaseDetailListActivity purchaseDetailListActivity = (PurchaseDetailListActivity) activity;
							purchaseDetailListActivity.purchaseDetailsReceived(purchaseDetails);
						
						} else if (activity instanceof PurchaseListActivity) {
							PurchaseListActivity purchaseListActivity = (PurchaseListActivity) activity;
							purchaseListActivity.purchaseDetailsReceived(purchaseDetails);
						}
					} else if (statusCode == HttpStatusCode.CREATED) {
						// purchase detail
						jObject = jArray.getJSONObject(0);
						//PurchaseDetail purchaseDetail = new PurchaseDetail(jObject);
						
						if (activity instanceof WishListActivity) {
							WishListActivity wishListActivity = (WishListActivity) activity;
							wishListActivity.wishAddedToCart(true);
						
						} else if (activity instanceof ProductActivity) {
							ProductActivity productActivity = (ProductActivity) activity;
							productActivity.productAddedToCart(true);
						}
					}
				} else if (statusCode == HttpStatusCode.NO_CONTENT) {
					// 204
					
					// TODO
				} else if (statusCode == HttpStatusCode.NOT_FOUND) {
					// 404
					
					if (activity instanceof HomeActivity) {
						HomeActivity homeActivity = (HomeActivity) activity;
						homeActivity.zeroCountReceived();
					
					} else if (activity instanceof WishListActivity) {
						WishListActivity wishListActivity = (WishListActivity) activity;
						wishListActivity.wishAddedToCart(false);
					
					} else if (activity instanceof PurchaseDetailListActivity) {
						PurchaseDetailListActivity purchaseDetailListActivity = (PurchaseDetailListActivity) activity;
						purchaseDetailListActivity.purchaseDetailsReceived(new ArrayList<PurchaseDetail>());
					
					} else if (activity instanceof PurchaseListActivity) {
						PurchaseListActivity purchaseListActivity = (PurchaseListActivity) activity;
						purchaseListActivity.purchaseDetailsReceived(new ArrayList<PurchaseDetail>());
					
					} else if (activity instanceof ProductActivity) {
						ProductActivity productActivity = (ProductActivity) activity;
						productActivity.productAddedToCart(false);
					}
				}
				
			} else if (route.matches("users/" + Regex.SPECIAL_TEXT + "/purchases/" + Regex.INTEGER + "/purchase_details/" + Regex.INTEGER)) {
				// GET <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}
				// PUT <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}?idBatch={idBatch}&units={units}
				// DELETE <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}
				if (jArray != null) {
					jObject = jArray.getJSONObject(0);
					// purchase detail
					//PurchaseDetail purchaseDetail = new PurchaseDetail(jObject);
					
					// TODO
				} else if (statusCode == HttpStatusCode.NO_CONTENT) {
					// 204
					
					if (activity instanceof PurchaseDetailListActivity) {
						PurchaseDetailListActivity purchaseDetailListActivity = (PurchaseDetailListActivity) activity;
						purchaseDetailListActivity.purchaseDetailDeleted(true);
					}
				} else {
					
					if (activity instanceof PurchaseDetailListActivity) {
						PurchaseDetailListActivity purchaseDetailListActivity = (PurchaseDetailListActivity) activity;
						purchaseDetailListActivity.purchaseDetailDeleted(false);
					}
				}
				
			} else if (route.matches("users/" + Regex.SPECIAL_TEXT + "/friendships")) {
				// GET <URLbase>/users/{userEmail}/friendships?following=false
				// GET <URLbase>/users/{userEmail}/friendships?following=true
				// POST <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
				// DELETE <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
				if (jObject != null) {
					if (statusCode == HttpStatusCode.OK) {
						if (jObject.has("following") || jObject.has("followers")) {
							// get list of users
							if (jObject.has("following")) {
								jArray = jObject.getJSONArray("following");
							} else if (jObject.has("followers")) {
								jArray = jObject.getJSONArray("followers");
							}
							List<User> users = processUsers(jArray);
							
							if (activity instanceof HomeActivity) {
								HomeActivity homeActivity = (HomeActivity) activity;
								if (jObject.has("following")) {
									homeActivity.followingCountReceived(users.size());
								} else if (jObject.has("followers")) {
									homeActivity.followersCountReceived(users.size());
								}
								
							} else if (activity instanceof FollowsListActivity) {
								FollowsListActivity followsListActivity = (FollowsListActivity) activity;
								if (jObject.has("following")) {
									followsListActivity.friendshipsReceived(users, false);
								} else if (jObject.has("followers")) {
									followsListActivity.friendshipsReceived(users, true);
								}
								
							} else if (activity instanceof UserProfileActivity) {
								UserProfileActivity userProfileActivity = (UserProfileActivity) activity;
								if (jObject.has("following")) {
									userProfileActivity.followingCountReceived(users.size());
								} else if (jObject.has("followers")) {
									userProfileActivity.followersCountReceived(users.size());
								}
							}
							
						} else {
							// get user
							//User user = new User(jObject);
						
							// TODO
						}
						
					} else if (statusCode == HttpStatusCode.CREATED) {
						// friendship
						//Friendship friendship = new Friendship(jObject);
						
						if (activity instanceof FollowsListActivity) {
							FollowsListActivity followsListActivity = (FollowsListActivity) activity;
							followsListActivity.friendshipCreated();
						}
					}
					
				} else if (statusCode == HttpStatusCode.NO_CONTENT) {
					// 204
					
					if (activity instanceof FollowsListActivity) {
						FollowsListActivity followsListActivity = (FollowsListActivity) activity;
						followsListActivity.friendshipRemoved(true);
					}
					
				} else if (statusCode == HttpStatusCode.NOT_FOUND) {
					// 404
					
					if (activity instanceof HomeActivity) {
						HomeActivity homeActivity = (HomeActivity) activity;
						homeActivity.zeroCountReceived();
						
					} else if (activity instanceof FollowsListActivity) {
						FollowsListActivity followsListActivity = (FollowsListActivity) activity;
						followsListActivity.friendshipsReceived(new ArrayList<User>(), null);
						
					} else if (activity instanceof UserProfileActivity) {
						UserProfileActivity userProfileActivity = (UserProfileActivity) activity;
						userProfileActivity.zeroCountReceived();
					}
				}
				
			} else if (route.matches("users/" + Regex.SPECIAL_TEXT + "/achievements")) {
				// GET <URLbase>/users/{userEmail}/achievements
				if (jArray != null) {
					// list of achievements
					List<Achievement> achievements = processAchievements(jArray, lang);
					
					if (activity instanceof UserProfileActivity) {
						UserProfileActivity userProfileActivity = (UserProfileActivity) activity;
						userProfileActivity.achievementsReceived(achievements);
					}
				}
				// POST <URLbase>/users/{userEmail}/achievements?badgeName={badgeName}
				if (jObject != null) {
					// achievement
					Achievement achievement = new Achievement(jObject, lang);
					
					if (activity instanceof BaseActivity) {
						BaseActivity baseActivity = (BaseActivity) activity;
						baseActivity.achievementCreated(achievement);
					}
				}
				
			} else if (route.matches("users/" + Regex.SPECIAL_TEXT + "/achievements/" + Regex.INTEGER)) {
				// GET <URLbase>/users/{userEmail}/achievements/{idAchievement}
				if (jObject != null) {
					// achievement
					//Achievement achievement = new Achievement(jObject);
					
					// TODO
				}
			}
			
		} catch (Exception e) {
			if (!RESTClient.isOnline(activity)) {
				Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_internetconnection), Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(activity, activity.getResources().getString(R.string.toast_problem_request), Toast.LENGTH_SHORT).show();
			}
			activity.finish();
		}
	}
	
	
	
	/**
	 * Login user
	 * @param userEmail
	 * @param password
	 */
	public void logInUser(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}?fields=password
		client.logInUser(this, userEmail);
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
		// PUT <URLbase>/users/{userEmail}?password={password}&cityName={cityName}&languageName={languageName}&nick={nick}&name={name}&surname={surname}&age={age}&phone={phone}&direction={direction}
		client.signInUser(this, userEmail, cityName, languageName, nick, name, surname, age, encryptPassword(String.valueOf(password)), phone, direction);
	}
	
	
	
	/**
	 * Update user's data
	 * @param user
	 */
	public void updateUserData(User user) {
		// PUT <URLbase>/users/{userEmail}?cityName={cityName}&languageName={languageName}&nick={nick}&name={name}&surname={surname}&age={age}&phone={phone}&direction={direction}
		client.updateUserData(this, user);
	}
	
	
	
	/**
	 * Update points
	 * @param userEmail
	 * @param points
	 */
	public void updatePoints(CharSequence userEmail, int points) {
		// PUT <URLbase>/users/{userEmail}?points={points}
		client.updatePoints(this, userEmail, points);
	}
	
	
	
	/**
	 * Change user's password
	 * @param userEmail
	 * @param newPassword
	 * @param oldPassword
	 */
	public void changeUserPassword(CharSequence userEmail, CharSequence newPassword, CharSequence oldPassword) {
		// PUT <URLbase>/users/{userEmail}?password={password}&oldPassword={oldPassword}
		client.changeUserPassword(this, userEmail, encryptPassword((String) newPassword), encryptPassword((String) oldPassword));
	}
	
	
	
	/**
	 * Get current user
	 * @param userEmail
	 */
	public void getCurrentUser(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}
		client.getCurrentUser(this, userEmail);
	}
	
	
	
	/**
	 * Get user without returning any personal information
	 * @param userEmail
	 */
	public void getAnotherUser(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}?fields=userEmail,nick,points
		client.getAnotherUser(this, userEmail);
	}
	
	
	
	/**
	 * Get followers
	 * @param userEmail
	 */
	public void getFollowers(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/friendships?following=false
		client.getFollowers(this, userEmail);
	}
	
	
	
	/**
	 * Get users being followed
	 * @param userEmail
	 */
	public void getFollowing(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/friendships?following=true
		client.getFollowing(this, userEmail);
	}
	
	
	
	/**
	 * Follow user
	 * @param userEmailFollowing
	 * @param userEmailFollowed
	 */
	public void followUser(CharSequence userEmailFollowing, CharSequence userEmailFollowed) {
		// POST <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
		client.followUser(this, userEmailFollowing, userEmailFollowed);
	}
	
	
	
	/**
	 * Unfollow user
	 * @param userEmailFollowing
	 * @param userEmailFollowed
	 */
	public void unfollowUser(CharSequence userEmailFollowing, CharSequence userEmailFollowed) {
		// DELETE <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
		client.unfollowUser(this, userEmailFollowing, userEmailFollowed);
	}
	
	
	
	/**
	 * Get user's achievements
	 * @param userEmail
	 */
	public void getAchievements(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/achievements
		client.getAchievements(this, userEmail);
	}
	
	
	
	/**
	 * Create a new achievement
	 * @param achievement
	 */
	public void createAchievement(Achievement achievement) {
		// POST <URLbase>/users/{userEmail}/achievements?badgeName={badgeName}
		client.createAchievement(this, achievement);
	}
	
	
	
	/**
	 * Get user's achievement by id
	 * @param userEmail
	 * @param idAchievement
	 */
	public void getAchievement(CharSequence userEmail, int idAchievement) {
		// GET <URLbase>/users/{userEmail}/achievements/{idAchievement}
		client.getAchievement(this, userEmail, idAchievement);
	}
	
	
	
	/**
	 * Get user's reviews
	 * @param userEmail
	 */
	public void getReviews(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/reviews
		client.getReviews(this, userEmail);
	}
	
	
	
	/**
	 * Create a new review
	 * @param review
	 */
	public void createReview(Review review) {
		// POST <URLbase>/users/{userEmail}/reviews?idProduct={idProduct}&rating={rating}&comment={comment}
		client.createReview(this, review);
	}
	
	
	
	/**
	 * Get review
	 * @param userEmail
	 * @param idComment
	 */
	public void getReview(CharSequence userEmail, int idComment) {
		// GET <URLbase>/users/{userEmail}/reviews/{idComment}
		client.getReview(this, userEmail, idComment);
	}
	
	
	
	/**
	 * Get user's wishes
	 * @param userEmail
	 */
	public void getWishes(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/wishes
		client.getWishes(this, userEmail);
	}
	
	
	
	/**
	 * Create wish
	 * @param wish
	 */
	public void createWish(Wish wish) {
		// POST <URLbase>/users/{userEmail}/wishes?idProduct={idProduct}
		client.createWish(this, wish);
	}
	
	
	
	/**
	 * Get user's wish
	 * @param userEmail
	 * @param idWish
	 */
	public void getWish(CharSequence userEmail, int idWish) {
		// GET <URLbase>/users/{userEmail}/wishes/{idWish}
		client.getWish(this, userEmail, idWish);
	}
	
	
	
	/**
	 * Delete user's wish
	 * @param userEmail
	 * @param idWish
	 */
	public void deleteWish(CharSequence userEmail, int idWish) {
		// DELETE <URLbase>/users/{userEmail}/wishes/{idWish}
		client.deleteWish(this, userEmail, idWish);
	}
	
	
	
	/**
	 * Get user's purchases
	 * @param userEmail
	 */
	public void getPurchases(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/purchases
		client.getPurchases(this, userEmail);
	}
	
	
	
	/**
	 * Get user's purchase
	 * @param userEmail
	 * @param idPurchase
	 */
	public void getPurchase(CharSequence userEmail, int idPurchase) {
		// GET <URLbase>/users/{userEmail}/purchases/{idPurchase}
		client.getPurchase(this, userEmail, idPurchase);
	}
	
	
	
	/**
	 * Get user's cart
	 * @param userEmail
	 */
	public void getCartPurchase(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/purchases?status={Purchase.STATUS_PENDING}
		client.getCartPurchase(this, userEmail);
	}
	
	
	
	/**
	 * Get purchase details
	 * @param userEmail
	 * @param idPurchase
	 */
	public void getPurchaseDetails(CharSequence userEmail, int idPurchase) {
		// GET <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details
		client.getPurchaseDetails(this, userEmail, idPurchase);
	}
	
	
	
	/**
	 * Get purchase detail
	 * @param userEmail
	 * @param idPurchase
	 * @param idPurchaseDetail
	 */
	public void getPurchaseDetail(CharSequence userEmail, int idPurchase, int idPurchaseDetail) {
		// GET <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}
		client.getPurchaseDetail(this, userEmail, idPurchase, idPurchaseDetail);
	}
	
	
	
	/**
	 * Create a new purchase detail added to the current purchase (STATUS_PENDING).
	 * If doesn't exists, it's created.
	 * @param userEmail
	 * @param detail
	 */
	public void createPurchaseDetail(CharSequence userEmail, PurchaseDetail detail) {
		// POST <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details?idBatch={idBatch}&units={units}
		client.createPurchaseDetail(this, userEmail, detail);
	}
	
	
	
	/**
	 * Update purchase detail
	 * @param userEmail
	 * @param detail
	 */
	public void updatePurchaseDetail(CharSequence userEmail, PurchaseDetail detail) {
		// PUT <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}?idBatch={idBatch}&units={units}
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
		// DELETE <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}
		client.deletePurchaseDetail(this, userEmail, idPurchase, idPurchaseDetail);
	}
	
	
	
	private List<Review> processReviews(JSONArray jReviews) throws JSONException {
		List<Review> reviews = new ArrayList<Review>(jReviews.length());
		
		// for each review JSON object
		for (int i = 0; i < jReviews.length(); i++) {
			// create Review model from JSON object
			JSONObject jReview = jReviews.getJSONObject(i);
			Review review = new Review(jReview);
			reviews.add(review);
		}
		
		return reviews;
	}
	
	
	
	private List<Wish> processWishes(JSONArray jWishes, String lang) throws JSONException {
		List<Wish> wishes = new ArrayList<Wish>(jWishes.length());
		
		// for each wish JSON object
		for (int i = 0; i < jWishes.length(); i++) {
			// create Wish model from JSON object
			JSONObject jWish = jWishes.getJSONObject(i);
			Wish wish = new Wish(jWish, lang);
			wishes.add(wish);
		}
		
		return wishes;
	}
	
	
	
	private List<Purchase> processPurchases(JSONArray jPurchases) throws JSONException {
		List<Purchase> purchases = new ArrayList<Purchase>(jPurchases.length());
		
		// for each purchase JSON object
		for (int i = 0; i < jPurchases.length(); i++) {
			// create Purchase model from JSON object
			JSONObject jPurchase = jPurchases.getJSONObject(i);
			Purchase purchase = new Purchase(jPurchase);
			purchases.add(purchase);
		}
		
		return purchases;
	}
	
	
	
	private List<PurchaseDetail> processPurchaseDetails(JSONArray jPurchaseDetails) throws JSONException {
		List<PurchaseDetail> purchaseDetails = new ArrayList<PurchaseDetail>(jPurchaseDetails.length());
		
		// for each purchase detail JSON object
		for (int i = 0; i < jPurchaseDetails.length(); i++) {
			// create PurchaseDetail model from JSON object
			JSONObject jPurchaseDetail = jPurchaseDetails.getJSONObject(i);
			PurchaseDetail purchaseDetail = new PurchaseDetail(jPurchaseDetail);
			purchaseDetails.add(purchaseDetail);
		}
		
		return purchaseDetails;
	}
	
	
	
	private List<Achievement> processAchievements(JSONArray jAchievements, String lang) throws JSONException {
		List<Achievement> achievements = new ArrayList<Achievement>(jAchievements.length());
		
		// for each achievement JSON object
		for (int i = 0; i < jAchievements.length(); i++) {
			// create Achievement model from JSON object
			JSONObject jAchievement = jAchievements.getJSONObject(i);
			Achievement achievement = new Achievement(jAchievement, lang);
			achievements.add(achievement);
		}
		
		return achievements;
	}
	
	
	
	private List<User> processUsers(JSONArray jUsers) throws JSONException {
		List<User> users = new ArrayList<User>(jUsers.length());
		
		// for each user JSON object
		for (int i = 0; i < jUsers.length(); i++) {
			// create User model from JSON object
			JSONObject jUser = jUsers.getJSONObject(i);
			User user = new User(jUser);
			users.add(user);
		}
		
		return users;
	}
	
	
	private String encryptPassword(String password) {
		try {
			return SimpleCrypto.encrypt(SimpleCrypto.MASTER_KEY, password);
		} catch (Exception e) {
			return "";
		}
	}
	
	
	private String decryptPassword(User user) {
		try {
			String dbPassword = (String) user.getPassword();
			return SimpleCrypto.decrypt(SimpleCrypto.MASTER_KEY, dbPassword);
		} catch (Exception e) {
			return "";
		}
	}
	
	
}
