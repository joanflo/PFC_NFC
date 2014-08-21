package com.joanflo.network;

import java.sql.Timestamp;
import java.util.ArrayList;
import org.json.JSONObject;
import android.app.Activity;
import android.util.Log;

import com.joanflo.models.*;
import com.joanflo.tagit.*;

public class RESTClient implements AsyncResponse {
	
	
	//public static final CharSequence HOST = "http://alumnes-ltim.uib.es/~jflorit/";
	//public static final CharSequence HOST = "http://79.152.31.226/api/server.php/";
	public static final CharSequence HOST = "http://192.168.56.1/api/server.php/";
	//public static final CharSequence HOST = "http://localhost/api/server.php/";

	
	// Singleton pattern
	private static RESTClient instance = null;
	
	private RESTClient() {
		// Exists only to defeat instantiation from any other classes.
	}
	
	public static RESTClient getInstance() {
		if (instance == null) {
			instance = new RESTClient();
		}
		return instance;
	}
	
	
	
	public boolean logInUser(Activity activity, CharSequence userEmail, CharSequence password) {
		// GET <URLbase>/users/{userEmail}?fields=password
		InfoRequest info = new InfoRequest(activity, HttpMethod.GET, HOST + "users/" + userEmail + "?fields=password");
		Log.i("test", "1");
		new AsyncRequest(this).execute(info);
		Log.i("test", "2");
		
		// comparar el que ens torna la consulta amb els paràmetres del mètode
		return true;
	}
	
	
	
	public User signInUser(Activity activity, User user) {
		CharSequence userEmail = user.getUserEmail();
		CharSequence cityName = user.getCity().getCityName();
		CharSequence languageName = user.getLanguage().getLanguageName();
		CharSequence nick = user.getNick();
		CharSequence name = user.getName();
		CharSequence surname = user.getSurname();
		int age = user.getAge();
		CharSequence password = user.getPassword();
		CharSequence phone = user.getPhone();
		CharSequence direction = user.getDirection();
		// PUT <URLbase>/users/{userEmail}?cityName={cityName}&languageName={languageName}&nick={nick}&name={name}&surname={surname}&age={age}&phone={phone}&direction={direction}
		return null;
	}
	
	
	
	public ArrayList<Language> getLanguages(Activity activity) {
		// GET <URLbase>/languages
		InfoRequest info = new InfoRequest(activity, HttpMethod.GET, HOST + "languages");
		new AsyncRequest(this).execute(info);
		return null;
	}
	
	
	
	public ArrayList<Country> getCountries(Activity activity) {
		// GET <URLbase>/countries
		return null;
	}
	
	
	
	public Country getCountry(Activity activity, CharSequence cityName) {
		// GET <URLbase>/countries?cityName={cityName}
		return null;
	}
	
	
	
	public ArrayList<Region> getRegions(Activity activity, CharSequence countryName) {
		// GET <URLbase>/countries/{countryName}/regions
		return null;
	}
	
	
	
	public ArrayList<City> getCities(Activity activity, CharSequence countryName, CharSequence regionName) {
		// GET <URLbase>/countries/{countryName}/regions/{regionName}/cities
		return null;
	}
	
	
	
	public ArrayList<Product> getProducts(Activity activity, CharSequence queryName) {
		// GET <URLbase>/products?queryName={queryName}
		return null;
	}
	
	
	
	public ArrayList<Product> getProducts(Activity activity, CharSequence queryName, float priceFrom, float priceSince, char coin, CharSequence brandName, int idCategory, float rating) {
		if (queryName != null) {
			// afegir a la URL
		}
		if (priceFrom != 0) {
			// afegir a la URL
		}
		if (priceSince != 0) {
			// afegir a la URL
		}
		if (brandName != null) {
			// afegir a la URL
		}
		if (idCategory != -1) {
			// afegir a la URL
		}
		
		// GET <URLbase>/products?queryName={queryName}&priceFrom={priceFrom}&priceSince={priceSince}&coin={coin}&brandName={brandName}&idCategory={idCategory}&rating={rating}
		return null;
	}
	
	
	
	public ArrayList<Category> getCategories(Activity activity, int level) {
		// (includes subcategories/products for each category)
		// GET <URLbase>/categories?level={level}
		return null;
	}
	
	
	
	public ArrayList<Category> getSubategories(Activity activity, int idCategory) {
		// (includes sub-subcategories/products for each category)
		// GET <URLbase>/categories/{idCategory}/subcategories
		return null;
	}
	
	
	
	public ArrayList<Shop> getShops(Activity activity, CharSequence cityName) {
		// returns all the shops in the city
		// GET <URLbase>/shops?cityName={cityName}
		return null;
	}
	
	
	
	public ArrayList<Shop> getShops(Activity activity, double latitude, double longitude) {
		// returns the most near shops from current location
		// GET <URLbase>/shops?latitude={latitude}&longitude={longitude}
		return null;
	}
	
	
	
	public Shop getShop(Activity activity, int idShop) {
		// GET <URLbase>/shops/{idShop}
		return null;
	}
	
	
	
	public User getCurrentUser(Activity activity, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}
		return null;
	}
	
	
	
	public User getAnotherUser(Activity activity, CharSequence userEmail) {
		// doesn't returns personal information
		// GET <URLbase>/users/{userEmail}?fields=nick,points
		return null;
	}
	
	
	
	public User updateUserData(Activity activity, User user) {
		// same request
		return signInUser(activity, user);
	}
	
	
	
	public boolean changeUserPassword(Activity activity, CharSequence userEmail, CharSequence password) {
		// PUT <URLbase>/users/{userEmail}?password={password}
		return true;
	}
	
	
	
	public ArrayList<User> getFollowers(Activity activity, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/friendships?following=false
		return null;
	}
	
	
	
	public ArrayList<User> getFollowing(Activity activity, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/friendships?following=true
		return null;
	}
	
	
	
	public Friendship followUser(Activity activity, CharSequence userEmailFollowing, CharSequence userEmailFollowed) {
		// POST <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
		return null;
	}
	
	
	
	public Friendship unfollowUser(Activity activity, CharSequence userEmailFollowing, CharSequence userEmailFollowed) {
		// DELETE <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
		return null;
	}
	
	
	
	public ArrayList<Achievement> getAchievements(Activity activity, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/achievements
		return null;
	}
	
	
	
	public Achievement createAchievement(Activity activity, Achievement achievement) {
		CharSequence userEmail = achievement.getUser().getUserEmail();
		CharSequence badgeName = achievement.getBadge().getBadgeName();
		Timestamp date = achievement.getDate();
		// POST <URLbase>/users/{userEmail}/achievements?badgeName={badgeName}
		return null;
	}
	
	
	
	public Achievement getAchievement(Activity activity, CharSequence userEmail, int idAchievement) {
		// GET <URLbase>/users/{userEmail}/achievements/{idAchievement}
		return null;
	}
	
	
	
	public ArrayList<Badge> getBadges(Activity activity) {
		// GET <URLbase>/badges
		return null;
	}
	
	
	
	public Badge getBadge(Activity activity, CharSequence badgeName) {
		// GET <URLbase>/badges/{badgeName}
		return null;
	}
	
	
	
	public ArrayList<Review> getReviews(Activity activity, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/reviews
		return null;
	}
	
	
	
	public ArrayList<Review> getReviews(Activity activity, int idProduct) {
		// GET <URLbase>/products/{idProduct}/reviews
		return null;
	}
	
	
	
	public Review createReview(Activity activity, Review review) {
		CharSequence userEmail = review.getUser().getUserEmail();
		int idProduct = review.getProduct().getIdProduct();
		float rating = review.getRating();
		CharSequence comment = review.getComment();
		Timestamp date = review.getDate();
		// POST <URLbase>/users/{userEmail}/reviews?idProduct={idProduct}&rating={rating}&comment={comment}&date={date}
		// equivalent:
		// POST <URLbase>/products/{idProduct}/reviews?userEmail={userEmail}&rating={rating}&comment={comment}&date={date}
		return null;
	}
	
	
	
	public Review getReview(Activity activity, CharSequence userEmail, int idComment) {
		// GET <URLbase>/users/{userEmail}/reviews/{idComment}
		return null;
	}
	
	
	
	public ArrayList<Wish> getWishes(Activity activity, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/wishes
		return null;
	}
	
	
	
	public Wish createWish(Activity activity, Wish wish) {
		CharSequence userEmail = wish.getUser().getUserEmail();
		int idProduct = wish.getProduct().getIdProduct();
		Timestamp date = wish.getDate();
		// POST <URLbase>/users/{userEmail}/wishes?idProduct={idProduct}&date={date}
		// equivalent:
		// POST <URLbase>/products/{idProduct}/wishes?userEmail={userEmail}&date={date}
		return null;
	}
	
	
	
	public Wish getWish(Activity activity, CharSequence userEmail, int idWish) {
		// GET <URLbase>/users/{userEmail}/wishes/{idWish}
		return null;
	}
	
	
	
	public Wish deleteWish(Activity activity, CharSequence userEmail, int idWish) {
		// DELETE <URLbase>/users/{userEmail}/wishes/{idWish}
		return null;
	}
	
	
	
	public ArrayList<Purchase> getPurchases(Activity activity, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/purchases
		return null;
	}
	
	
	
	public Purchase getPurchase(Activity activity, CharSequence userEmail, int idPurchase) {
		// GET <URLbase>/users/{userEmail}/purchases/{idPurchase}
		return null;
	}
	
	
	
	public Purchase getCartPurchase(Activity activity, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/purchases?status={Purchase.STATUS_PENDING}
		return null;
	}
	
	
	
	public ArrayList<PurchaseDetail> getPurchaseDetails(Activity activity, CharSequence userEmail, int idPurchase) {
		// GET <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details
		return null;
	}
	
	
	
	public PurchaseDetail getPurchaseDetail(Activity activity, CharSequence userEmail, int idPurchase, int idPurchaseDetail) {
		// GET <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}
		return null;
	}
	
	
	
	public PurchaseDetail createPurchaseDetail(Activity activity, CharSequence userEmail, PurchaseDetail detail) {
		// new purchase detail added to the current purchase (STATUS_PENDING). If doesn't exists, it's created.
		int idPurchase = detail.getPurchase().getIdPurchase();
		int idBatch = detail.getBatch().getIdBatch();
		int units = detail.getUnits();
		// POST <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details?idBatch={idBatch}&units={units}
		return null;
	}
	
	
	
	public PurchaseDetail updatePurchaseDetail(Activity activity, CharSequence userEmail, PurchaseDetail detail) {
		int idPurchaseDetail = detail.getIdPurchaseDetail();
		int idPurchase = detail.getPurchase().getIdPurchase();
		int idBatch = detail.getBatch().getIdBatch();
		int units = detail.getUnits();
		// PUT <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}?idBatch={idBatch}&units={units}
		return null;
	}
	
	
	
	public PurchaseDetail deletePurchaseDetail(Activity activity, CharSequence userEmail, int idPurchase, int idPurchaseDetail) {
		// DELETE <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}
		return null;
	}
	
	
	
	public Batch getBatch(Activity activity, int idBatch) {
		// GET <URLbase>/batches/{idBatch}
		return null;
	}
	
	
	
	public Batch updateBatch(Activity activity, Batch batch) {
		int idBatch = batch.getIdBatch();
		int units = batch.getUnits();
		// PUT <URLbase>/batches/{idBatch}?units={units}
		return null;
	}
	
	
	
	public Tax getTax(Activity activity, int idProduct, CharSequence countryName) {
		// GET <URLbase>/taxes?idProduct={idProduct}&countryName={countryName}
		return null;
	}
	
	
	
	public Size getSize(Activity activity, int idSize) {
		// GET <URLbase>/sizes/{idSize}
		return null;
	}
	
	
	
	public Color getColor(Activity activity, CharSequence colorCode) {
		// GET <URLbase>/colors/{colorCode}
		return null;
	}
	
	
	
	public Product getProduct(Activity activity, int idProduct) {
		// GET <URLbase>/products/{idProduct}
		return null;
	}
	
	
	
	public Brand getBrand(Activity activity, CharSequence brandName) {
		// GET <URLbase>/brands/{brandName}
		return null;
	}
	
	
	
	public Collection getCollection(Activity activity, int idCollection) {
		// GET <URLbase>/collections/{idCollection}
		return null;
	}
	
	
	
	public ArrayList<ProductImage> getProductImages(Activity activity, int idProduct) {
		// GET <URLbase>/products/{idProduct}/product_images
		return null;
	}
	
	
	
	public ProductImage getProductFrontImage(Activity activity, int idProduct) {
		// GET <URLbase>/products/{idProduct}/product_images?type={ProductImage.TYPE_FRONT}
		return null;
	}
	
	
	
	public ArrayList<Batch> getBatches(Activity activity, int idProduct) {
		// GET <URLbase>/batches?idProduct={idProduct}
		return null;
	}
	
	
	
	public ArrayList<Batch> getBatches(Activity activity, int idProduct, int idShop) {
		// GET <URLbase>/batches?idProduct={idProduct}&idShop={idShop}
		return null;
	}
	
	
	
	public ArrayList<Product> getRelatedProducts(Activity activity, int idProduct) {
		// GET <URLbase>/products/{idProduct}/related_products
		return null;
	}
	
	

	@Override
	public void requestFinished(InfoResponse[] infoResponses) {
		Log.i("test", "i");
		// for each response
		for (int i = 0; i < infoResponses.length; i++) {
			
			// get response fields
			Activity activity = infoResponses[i].getActivity();
			JSONObject jObject = infoResponses[i].getJObject();
			
			// identify activity subclass to delegate it the JSON data
			if (activity instanceof BadgeActivity) {
				BadgeActivity badgeActivity = (BadgeActivity) activity;
				badgeActivity.requestFinished(jObject);
				
			} else if (activity instanceof BadgeListActivity) {
				BadgeListActivity badgeListActivity = (BadgeListActivity) activity;
				badgeListActivity.requestFinished(jObject);
				
			} else if (activity instanceof BrandActivity) {
				BrandActivity brandActivity = (BrandActivity) activity;
				brandActivity.requestFinished(jObject);
				
			} else if (activity instanceof CategoryListActivity) {
				CategoryListActivity categoryListActivity = (CategoryListActivity) activity;
				categoryListActivity.requestFinished(jObject);
				
			} else if (activity instanceof FollowsListActivity) {
				FollowsListActivity followsListActivity = (FollowsListActivity) activity;
				followsListActivity.requestFinished(jObject);
				
			} else if (activity instanceof HomeActivity) {
				HomeActivity homeActivity = (HomeActivity) activity;
				homeActivity.requestFinished(jObject);
				
			} else if (activity instanceof LoginActivity) {
				LoginActivity loginActivity = (LoginActivity) activity;
				loginActivity.requestFinished(jObject);
				
			} else if (activity instanceof NewReviewActivity) {
				NewReviewActivity newReviewActivity = (NewReviewActivity) activity;
				newReviewActivity.requestFinished(jObject);
				
			} else if (activity instanceof PasswordActivity) {
				PasswordActivity passwordActivity = (PasswordActivity) activity;
				passwordActivity.requestFinished(jObject);
				
			} else if (activity instanceof ProductActivity) {
				ProductActivity productActivity = (ProductActivity) activity;
				productActivity.requestFinished(jObject);
				
			} else if (activity instanceof ProductListActivity) {
				ProductListActivity productListActivity = (ProductListActivity) activity;
				productListActivity.requestFinished(jObject);
				
			} else if (activity instanceof ProductSearchActivity) {
				ProductSearchActivity productSearchActivity = (ProductSearchActivity) activity;
				productSearchActivity.requestFinished(jObject);
				
			} else if (activity instanceof PurchaseDetailListActivity) {
				PurchaseDetailListActivity purchaseDetailListActivity = (PurchaseDetailListActivity) activity;
				purchaseDetailListActivity.requestFinished(jObject);
				
			} else if (activity instanceof PurchaseListActivity) {
				PurchaseListActivity purchaseListActivity = (PurchaseListActivity) activity;
				purchaseListActivity.requestFinished(jObject);
				
			} else if (activity instanceof RegistrationActivity) {
				RegistrationActivity registrationActivity = (RegistrationActivity) activity;
				registrationActivity.requestFinished(jObject);
				
			} else if (activity instanceof ReviewListActivity) {
				ReviewListActivity reviewListActivity = (ReviewListActivity) activity;
				reviewListActivity.requestFinished(jObject);
				
			} else if (activity instanceof ShopActivity) {
				ShopActivity shopActivity = (ShopActivity) activity;
				shopActivity.requestFinished(jObject);
				
			} else if (activity instanceof ShopSelectionActivity) {
				ShopSelectionActivity shopSelectionActivity = (ShopSelectionActivity) activity;
				shopSelectionActivity.requestFinished(jObject);
				
			} else if (activity instanceof UpdateUserDataActivity) {
				UpdateUserDataActivity updateUserDataActivity = (UpdateUserDataActivity) activity;
				updateUserDataActivity.requestFinished(jObject);
				
			} else if (activity instanceof UserProfileActivity) {
				UserProfileActivity userProfileActivity = (UserProfileActivity) activity;
				userProfileActivity.requestFinished(jObject);
				
			} else if (activity instanceof WishListActivity) {
				WishListActivity wishListActivity = (WishListActivity) activity;
				wishListActivity.requestFinished(jObject);
			}
		}
	}
	
	
}
