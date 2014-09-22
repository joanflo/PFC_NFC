package com.joanflo.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import com.joanflo.controllers.BadgesController;
import com.joanflo.controllers.BatchesController;
import com.joanflo.controllers.BrandsController;
import com.joanflo.controllers.CategoriesController;
import com.joanflo.controllers.CollectionsController;
import com.joanflo.controllers.ColorsController;
import com.joanflo.controllers.CountriesController;
import com.joanflo.controllers.LanguagesController;
import com.joanflo.controllers.ProductsController;
import com.joanflo.controllers.ShopsController;
import com.joanflo.controllers.SizesController;
import com.joanflo.controllers.TaxesController;
import com.joanflo.controllers.UsersController;
import com.joanflo.models.Achievement;
import com.joanflo.models.Batch;
import com.joanflo.models.ProductImage;
import com.joanflo.models.Purchase;
import com.joanflo.models.PurchaseDetail;
import com.joanflo.models.Review;
import com.joanflo.models.User;
import com.joanflo.models.Wish;

public class RESTClient implements AsyncResponse {
	
	
	public static final CharSequence HOST = "http://alumnes-ltim.uib.es/~jflorit/index.php/";

	
	
	// Singleton pattern
	private static RESTClient instance = null;
	
	/**
	 * Exists only to defeat instantiation from any other classes.
	 */
	private RESTClient() { }
	
	/**
	 * Singleton pattern instance
	 * @return RESTClient instance
	 */
	public static RESTClient getInstance() {
		if (instance == null) {
			instance = new RESTClient();
		}
		return instance;
	}
	
	
	
	@Override
	public void requestFinished(InfoResponse[] infoResponses) {
		// for each response
		for (int i = 0; i < infoResponses.length; i++) {
			
			// get response fields
			Object controller = infoResponses[i].getController();
			String route = infoResponses[i].getRoute();
			int statusCode = infoResponses[i].getStatusCode();
			JSONObject jObject = infoResponses[i].getJObject();
			JSONArray jArray = infoResponses[i].getJArray();
			
			// identify controller to delegate it the JSON data
			if (controller instanceof BadgesController) {
				BadgesController badgesController = (BadgesController) controller;
				badgesController.requestFinished(route, statusCode, jObject, jArray);
				
			} else if (controller instanceof BatchesController) {
				BatchesController batchesController = (BatchesController) controller;
				batchesController.requestFinished(route, statusCode, jObject, jArray);
				
			} else if (controller instanceof BrandsController) {
				BrandsController brandsController = (BrandsController) controller;
				brandsController.requestFinished(route, statusCode, jObject, jArray);
				
			} else if (controller instanceof CategoriesController) {
				CategoriesController categoriesController = (CategoriesController) controller;
				categoriesController.requestFinished(route, statusCode, jObject, jArray);
				
			} else if (controller instanceof CollectionsController) {
				CollectionsController collectionsController = (CollectionsController) controller;
				collectionsController.requestFinished(route, statusCode, jObject, jArray);
				
			} else if (controller instanceof ColorsController) {
				ColorsController colorsController = (ColorsController) controller;
				colorsController.requestFinished(route, statusCode, jObject, jArray);
				
			} else if (controller instanceof CountriesController) {
				CountriesController countriesController = (CountriesController) controller;
				countriesController.requestFinished(route, statusCode, jObject, jArray);
				
			} else if (controller instanceof LanguagesController) {
				LanguagesController languagesController = (LanguagesController) controller;
				languagesController.requestFinished(route, statusCode, jObject, jArray);
				
			} else if (controller instanceof ProductsController) {
				ProductsController productsController = (ProductsController) controller;
				productsController.requestFinished(route, statusCode, jObject, jArray);
				
			} else if (controller instanceof ShopsController) {
				ShopsController shopsController = (ShopsController) controller;
				shopsController.requestFinished(route, statusCode, jObject, jArray);
				
			} else if (controller instanceof SizesController) {
				SizesController sizesController = (SizesController) controller;
				sizesController.requestFinished(route, statusCode, jObject, jArray);
				
			} else if (controller instanceof TaxesController) {
				TaxesController taxesController = (TaxesController) controller;
				taxesController.requestFinished(route, statusCode, jObject, jArray);
				
			} else if (controller instanceof UsersController) {
				UsersController usersController = (UsersController) controller;
				usersController.requestFinished(route, statusCode, jObject, jArray);
				
			}
		}
	}
	
	
	
	
	/* ================================================================================ *
	 * ===================================== BADGES =================================== *
	 * ================================================================================ */
	
	/**
	 * Get all database badges
	 * @param controller
	 */
	public void getBadges(BadgesController controller) {
		// GET <URLbase>/badges
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "badges");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get a single badge
	 * @param controller
	 * @param badgeName
	 */
	public void getBadge(BadgesController controller, CharSequence badgeName) {
		// GET <URLbase>/badges/{badgeName}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "badges/" + badgeName);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	
	/* ================================================================================ *
	 * ===================================== SHOPS ==================================== *
	 * ================================================================================ */

	/**
	 * Get all the shops in the city
	 * @param controller
	 * @param cityName
	 */
	public void getShops(ShopsController controller, CharSequence cityName) {
		// GET <URLbase>/shops?cityName={cityName}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "shops?cityName=" + cityName);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get the nearest shops from current location
	 * @param controller
	 * @param latitude
	 * @param longitude
	 */
	public void getShops(ShopsController controller, double latitude, double longitude) {
		// GET <URLbase>/shops?latitude={latitude}&longitude={longitude}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "shops?latitude=" + latitude + "&longitude=" + longitude);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get shop
	 * @param controller
	 * @param idShop
	 */
	public void getShop(ShopsController controller, int idShop) {
		// GET <URLbase>/shops/{idShop}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "shops/" + idShop);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	
	/* ================================================================================ *
	 * ===================================== CATEGORIES =============================== *
	 * ================================================================================ */

	/**
	 * Get categories
	 * @param controller
	 * @param level
	 */
	public void getCategories(CategoriesController controller, int level) {
		// GET <URLbase>/categories?level={level}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "categories?level=" + level);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get categories
	 * @param controller
	 * @param idProduct
	 */
	public void getProductCategories(CategoriesController controller, int idProduct) {
		// GET <URLbase>/categories?idProduct={idProduct}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "categories?idProduct=" + idProduct);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get subcategories
	 * @param controller
	 * @param idCategory
	 */
	public void getSubategories(CategoriesController controller, int idCategory) {
		// GET <URLbase>/categories/{idCategory}/subcategories
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "categories/" + idCategory + "/subcategories");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get subcategories/products count for the given category
	 * @param controller
	 * @param idCategory
	 */
	public void getSubategoriesCount(CategoriesController controller, int idCategory) {
		// GET <URLbase>/categories/{idCategory}/subcategories?count=true
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "categories/" + idCategory + "/subcategories?count=true");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	
	/* ================================================================================ *
	 * ===================================== COUNTRIES ================================ *
	 * ================================================================================ */
	
	/**
	 * Get countries
	 * @param controller
	 */
	public void getCountries(CountriesController controller) {
		// GET <URLbase>/countries
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "countries");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get country
	 * @param controller
	 * @param cityName
	 */
	public void getCountry(CountriesController controller, CharSequence cityName) {
		// GET <URLbase>/countries?cityName={cityName}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "countries?cityName=" + cityName);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get regions
	 * @param controller
	 * @param countryName
	 */
	public void getRegions(CountriesController controller, CharSequence countryName) {
		// GET <URLbase>/countries/{countryName}/regions
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "countries/" + countryName + "/regions");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get cities
	 * @param controller
	 * @param countryName
	 * @param regionName
	 */
	public void getCities(CountriesController controller, CharSequence countryName, CharSequence regionName) {
		// GET <URLbase>/countries/{countryName}/regions/{regionName}/cities
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "countries/" + countryName + "/regions/" + regionName + "/cities");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	
	/* ================================================================================ *
	 * ===================================== LANGUAGES ================================ *
	 * ================================================================================ */
	 
	/**
	 * Get languages
	 * @param controller
	 */
	public void getLanguages(LanguagesController controller) {
		// GET <URLbase>/languages
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "languages");
		new AsyncRequest(this).execute(info);
	}



	/* ================================================================================ *
	 * ===================================== BATCHES ================================== *
	 * ================================================================================ */
	
	/**
	 * Get batches from product id
	 * @param controller
	 * @param idProduct
	 */
	public void getBatches(BatchesController controller, int idProduct) {
		// GET <URLbase>/batches?idProduct={idProduct}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "batches?idProduct=" + idProduct);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get batches from product id & shop id
	 * @param controller
	 * @param idProduct
	 * @param idShop
	 */
	public void getBatches(BatchesController controller, int idProduct, int idShop) {
		// GET <URLbase>/batches?idProduct={idProduct}&idShop={idShop}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "batches?idProduct=" + idProduct + "&idShop=" + idShop);
		new AsyncRequest(this).execute(info);
	}
	

	
	/**
	 * Get batch
	 * @param controller
	 * @param idBatch
	 */
	public void getBatch(BatchesController controller, int idBatch) {
		// GET <URLbase>/batches/{idBatch}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "batches/" + idBatch);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Update batch
	 * @param controller
	 * @param batch
	 */
	public void updateBatch(BatchesController controller, Batch batch) {
		int idBatch = batch.getIdBatch();
		int units = batch.getUnits();
		// PUT <URLbase>/batches/{idBatch}?units={units}
		InfoRequest info = new InfoRequest(controller, HttpMethod.PUT, HOST + "batches/" + idBatch + "?units=" + units);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	
	/* ================================================================================ *
	 * ===================================== BRANDS =================================== *
	 * ================================================================================ */
	
	/**
	 * Get brands
	 * @param controller
	 */
	public void getBrands(BrandsController controller) {
		// GET <URLbase>/brands
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "brands");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get brand
	 * @param controller
	 * @param brandName
	 */
	public void getBrand(BrandsController controller, CharSequence brandName) {
		// GET <URLbase>/brands/{brandName}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "brands/" + brandName);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	
	/* ================================================================================ *
	 * ===================================== SIZES ==================================== *
	 * ================================================================================ */

	/**
	 * Get size
	 * @param controller
	 * @param idSize
	 */
	public void getSize(SizesController controller, int idSize) {
		// GET <URLbase>/sizes/{idSize}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "sizes/" + idSize);
		new AsyncRequest(this).execute(info);
	}
	 


	/* ================================================================================ *
	 * ===================================== USERS ==================================== *
	 * ================================================================================ */
	
	/**
	 * Login user
	 * @param controller
	 * @param userEmail
	 * @param password
	 */
	public void logInUser(UsersController controller, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}?fields=password
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail + "?fields=password");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Create a new user
	 * @param controller
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
	public void signInUser(UsersController controller, CharSequence userEmail, CharSequence cityName,
						   CharSequence languageName, CharSequence nick, CharSequence name, CharSequence surname,
						   int age, CharSequence password, CharSequence phone, CharSequence direction) {
		String query = "users/" + userEmail + "?password=" + password + "&cityName=" + cityName + "&languageName=" + languageName + "&nick=" + nick + "&name=" + name + "&surname=" + surname + "&age=" + age;
		if (!phone.equals("")) {
			query += "&phone=" + phone;
		}
		if (!direction.equals("")) {
			query += "&direction=" + direction;
		}
		// PUT <URLbase>/users/{userEmail}?password={password}&cityName={cityName}&languageName={languageName}&nick={nick}&name={name}&surname={surname}&age={age}&phone={phone}&direction={direction}
		InfoRequest info = new InfoRequest(controller, HttpMethod.PUT, HOST + query);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Update user's data
	 * @param controller
	 * @param user
	 */
	public void updateUserData(UsersController controller, User user) {
		CharSequence userEmail = user.getUserEmail();
		CharSequence cityName = user.getCity().getCityName();
		CharSequence languageName = user.getLanguage().getLanguageName();
		CharSequence nick = user.getNick();
		CharSequence name = user.getName();
		CharSequence surname = user.getSurname();
		int age = user.getAge();
		CharSequence phone = user.getPhone();
		CharSequence direction = user.getDirection();
		// PUT <URLbase>/users/{userEmail}?cityName={cityName}&languageName={languageName}&nick={nick}&name={name}&surname={surname}&age={age}&phone={phone}&direction={direction}
		InfoRequest info = new InfoRequest(controller, HttpMethod.PUT, HOST + "users/" + userEmail + "?cityName=" + cityName + "&languageName=" + languageName + "&nick=" + nick + "&name=" + name + "&surname=" + surname + "&age=" + age + "&phone=" + phone + "&direction=" + direction);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Change user's password
	 * @param controller
	 * @param userEmail
	 * @param newPassword
	 * @param oldPassword
	 */
	public void changeUserPassword(UsersController controller, CharSequence userEmail, CharSequence newPassword, CharSequence oldPassword) {
		// PUT <URLbase>/users/{userEmail}?password={password}&oldPassword={oldPassword}
		InfoRequest info = new InfoRequest(controller, HttpMethod.PUT, HOST + "users/" + userEmail + "?password=" + newPassword + "&oldPassword=" + oldPassword);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get current user
	 * @param controller
	 * @param userEmail
	 */
	public void getCurrentUser(UsersController controller, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get user without returning any personal information
	 * @param controller
	 * @param userEmail
	 */
	public void getAnotherUser(UsersController controller, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}?fields=userEmail,nick,points
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail + "?fields=userEmail,nick,points");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get followers
	 * @param controller
	 * @param userEmail
	 */
	public void getFollowers(UsersController controller, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/friendships?following=false
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail + "/friendships?following=false");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get users being followed
	 * @param controller
	 * @param userEmail
	 */
	public void getFollowing(UsersController controller, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/friendships?following=true
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail + "/friendships?following=true");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Follow user
	 * @param controller
	 * @param userEmailFollowing
	 * @param userEmailFollowed
	 */
	public void followUser(UsersController controller, CharSequence userEmailFollowing, CharSequence userEmailFollowed) {
		// POST <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
		InfoRequest info = new InfoRequest(controller, HttpMethod.POST, HOST + "users/" + userEmailFollowing + "/friendships?userEmailFollowed=" + userEmailFollowed);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Unfollow user
	 * @param controller
	 * @param userEmailFollowing
	 * @param userEmailFollowed
	 */
	public void unfollowUser(UsersController controller, CharSequence userEmailFollowing, CharSequence userEmailFollowed) {
		// DELETE <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
		InfoRequest info = new InfoRequest(controller, HttpMethod.DELETE, HOST + "users/" + userEmailFollowing + "/friendships?userEmailFollowed=" + userEmailFollowed);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get user's achievements
	 * @param controller
	 * @param userEmail
	 */
	public void getAchievements(UsersController controller, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/achievements
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail + "/achievements");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Create a new achievement
	 * @param controller
	 * @param achievement
	 */
	public void createAchievement(UsersController controller, Achievement achievement) {
		CharSequence userEmail = achievement.getUser().getUserEmail();
		CharSequence badgeName = achievement.getBadge().getBadgeName();
		// POST <URLbase>/users/{userEmail}/achievements?badgeName={badgeName}
		InfoRequest info = new InfoRequest(controller, HttpMethod.POST, HOST + "users/" + userEmail + "/achievements?badgeName=" + badgeName);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get user's achievement by id
	 * @param controller
	 * @param userEmail
	 * @param idAchievement
	 */
	public void getAchievement(UsersController controller, CharSequence userEmail, int idAchievement) {
		// GET <URLbase>/users/{userEmail}/achievements/{idAchievement}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail + "/achievements/" + idAchievement);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get user's reviews
	 * @param controller
	 * @param userEmail
	 */
	public void getReviews(UsersController controller, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/reviews
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail + "/reviews");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Create a new review
	 * @param controller
	 * @param review
	 */
	public void createReview(UsersController controller, Review review) {
		CharSequence userEmail = review.getUser().getUserEmail();
		int idProduct = review.getProduct().getIdProduct();
		float rating = review.getRating();
		CharSequence comment = review.getComment();
		// POST <URLbase>/users/{userEmail}/reviews?idProduct={idProduct}&rating={rating}&comment={comment}
		InfoRequest info = new InfoRequest(controller, HttpMethod.POST, HOST + "users/" + userEmail + "/reviews?idProduct=" + idProduct + "&rating=" + rating + "&comment=" + comment);
		new AsyncRequest(this).execute(info);
		// equivalent:
		// POST <URLbase>/products/{idProduct}/reviews?userEmail={userEmail}&rating={rating}&comment={comment}
	}
	
	
	
	/**
	 * Get review
	 * @param controller
	 * @param userEmail
	 * @param idComment
	 */
	public void getReview(UsersController controller, CharSequence userEmail, int idComment) {
		// GET <URLbase>/users/{userEmail}/reviews/{idComment}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail + "/reviews/" + idComment);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get user's wishes
	 * @param controller
	 * @param userEmail
	 */
	public void getWishes(UsersController controller, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/wishes
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail + "/wishes");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Create wish
	 * @param controller
	 * @param wish
	 */
	public void createWish(UsersController controller, Wish wish) {
		CharSequence userEmail = wish.getUser().getUserEmail();
		int idProduct = wish.getProduct().getIdProduct();
		// POST <URLbase>/users/{userEmail}/wishes?idProduct={idProduct}
		InfoRequest info = new InfoRequest(controller, HttpMethod.POST, HOST + "users/" + userEmail + "/wishes?idProduct=" + idProduct);
		new AsyncRequest(this).execute(info);
		// equivalent:
		// POST <URLbase>/products/{idProduct}/wishes?userEmail={userEmail}
	}
	
	
	
	/**
	 * Get user's wish
	 * @param controller
	 * @param userEmail
	 * @param idWish
	 */
	public void getWish(UsersController controller, CharSequence userEmail, int idWish) {
		// GET <URLbase>/users/{userEmail}/wishes/{idWish}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail + "/wishes/" + idWish);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Delete user's wish
	 * @param controller
	 * @param userEmail
	 * @param idWish
	 */
	public void deleteWish(UsersController controller, CharSequence userEmail, int idWish) {
		// DELETE <URLbase>/users/{userEmail}/wishes/{idWish}
		InfoRequest info = new InfoRequest(controller, HttpMethod.DELETE, HOST + "users/" + userEmail + "/wishes/" + idWish);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get user's purchases
	 * @param controller
	 * @param userEmail
	 */
	public void getPurchases(UsersController controller, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/purchases
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail + "/purchases");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get user's purchase
	 * @param controller
	 * @param userEmail
	 * @param idPurchase
	 */
	public void getPurchase(UsersController controller, CharSequence userEmail, int idPurchase) {
		// GET <URLbase>/users/{userEmail}/purchases/{idPurchase}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail + "/purchases/" + idPurchase);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get user's cart
	 * @param controller
	 * @param userEmail
	 */
	public void getCartPurchase(UsersController controller, CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/purchases?status={Purchase.STATUS_PENDING}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail + "/purchases?status=" + Purchase.STATUS_PENDING);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get purchase details
	 * @param controller
	 * @param userEmail
	 * @param idPurchase
	 */
	public void getPurchaseDetails(UsersController controller, CharSequence userEmail, int idPurchase) {
		// GET <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail + "/purchases/" + idPurchase + "/purchase_details");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get purchase detail
	 * @param controller
	 * @param userEmail
	 * @param idPurchase
	 * @param idPurchaseDetail
	 */
	public void getPurchaseDetail(UsersController controller, CharSequence userEmail, int idPurchase, int idPurchaseDetail) {
		// GET <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "users/" + userEmail + "/purchases/" + idPurchase + "/purchase_details/" + idPurchaseDetail);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Create a new purchase detail added to the current purchase (STATUS_PENDING).
	 * If doesn't exists, it's created.
	 * @param controller
	 * @param userEmail
	 * @param detail
	 */
	public void createPurchaseDetail(UsersController controller, CharSequence userEmail, PurchaseDetail detail) {
		int idPurchase = detail.getPurchase().getIdPurchase();
		int idBatch = detail.getBatch().getIdBatch();
		int units = detail.getUnits();
		// POST <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details?idBatch={idBatch}&units={units}
		InfoRequest info = new InfoRequest(controller, HttpMethod.POST, HOST + "users/" + userEmail + "/purchases/" + idPurchase + "/purchase_details?idBatch=" + idBatch + "&units=" + units);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Update purchase detail
	 * @param controller
	 * @param userEmail
	 * @param detail
	 */
	public void updatePurchaseDetail(UsersController controller, CharSequence userEmail, PurchaseDetail detail) {
		int idPurchaseDetail = detail.getIdPurchaseDetail();
		int idPurchase = detail.getPurchase().getIdPurchase();
		int idBatch = detail.getBatch().getIdBatch();
		int units = detail.getUnits();
		// PUT <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}?idBatch={idBatch}&units={units}
		InfoRequest info = new InfoRequest(controller, HttpMethod.PUT, HOST + "users/" + userEmail + "/purchases/" + idPurchase + "/purchase_details/" + idPurchaseDetail + "?idBatch=" + idBatch + "&units=" + units);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Delete the given purchase detail from to the current purchase (STATUS_PENDING).
	 * If the current purchase is empty, it's deleted.
	 * @param controller
	 * @param userEmail
	 * @param idPurchase
	 * @param idPurchaseDetail
	 */
	public void deletePurchaseDetail(UsersController controller, CharSequence userEmail, int idPurchase, int idPurchaseDetail) {
		// DELETE <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}
		InfoRequest info = new InfoRequest(controller, HttpMethod.DELETE, HOST + "users/" + userEmail + "/purchases/" + idPurchase + "/purchase_details/" + idPurchaseDetail);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	
	/* ================================================================================ *
	 * ===================================== TAXES ==================================== *
	 * ================================================================================ */
	
	/**
	 * Get tax
	 * @param controller
	 * @param idProduct
	 * @param countryName
	 */
	public void getTax(TaxesController controller, int idProduct, CharSequence countryName) {
		// GET <URLbase>/taxes?idProduct={idProduct}&countryName={countryName}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "taxes?idProduct=" + idProduct + "&countryName=" + countryName);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	
	/* ================================================================================ *
	 * ===================================== COLLECCIONS ============================== *
	 * ================================================================================ */
	
	/**
	 * Get collection
	 * @param controller
	 * @param idCollection
	 */
	public void getCollection(CollectionsController controller, int idCollection) {
		// GET <URLbase>/collections/{idCollection}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "collections/" + idCollection);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	
	/* ================================================================================ *
	 * ===================================== COLORS =================================== *
	 * ================================================================================ */
	
	/**
	 * Get color
	 * @param controller
	 * @param colorCode
	 */
	public void getColor(ColorsController controller, CharSequence colorCode) {
		// GET <URLbase>/colors/{colorCode}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "colors/" + colorCode);
		new AsyncRequest(this).execute(info);
	}
	 


	/* ================================================================================ *
	 * ===================================== PRODUCTS ================================= *
	 * ================================================================================ */
	
	/**
	 * Get products by name
	 * @param controller
	 * @param queryName
	 */
	public void getProducts(ProductsController controller, CharSequence queryName) {
		// GET <URLbase>/products?queryName={queryName}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "products?queryName=" + queryName);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	public void getProducts(ProductsController controller, int idCategory) {
		// GET <URLbase>/products?idCategory={idCategory}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "products?idCategory=" + idCategory);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get products by advanced search
	 * @param controller
	 * @param queryName
	 * @param priceFrom
	 * @param priceSince
	 * @param coin
	 * @param brandName
	 * @param idCategory
	 * @param rating
	 */
	public void getProducts(ProductsController controller, CharSequence queryName, float priceFrom, float priceSince, char coin, CharSequence brandName, int idCategory, float rating) {
		// add query params to array
		ArrayList<String> queryParams = new ArrayList<String>();
		if (queryName != null && !queryName.toString().equals("")) {
			// afegir a la URL
			queryParams.add("queryName=" + queryName);
		}
		if (priceFrom != 0) {
			// afegir a la URL
			queryParams.add("priceFrom=" + priceFrom);
		}
		if (priceSince != 0) {
			// afegir a la URL
			queryParams.add("priceSince=" + priceSince);
		}
		queryParams.add("coin=" + coin);
		if (!brandName.toString().equals("")) {
			// afegir a la URL
			queryParams.add("brandName=" + brandName);
		}
		if (idCategory != -1) {
			// afegir a la URL
			queryParams.add("idCategory=" + idCategory);
		}
		queryParams.add("rating=" + rating);
		// build query
		String query = "?" + queryParams.get(0);
		for (int i = 1; i < queryParams.size(); i++) {
			query += "&" + queryParams.get(i);
		}
		// GET <URLbase>/products?queryName={queryName}&priceFrom={priceFrom}&priceSince={priceSince}&coin={coin}&brandName={brandName}&idCategory={idCategory}&rating={rating}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "products" + query);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get product reviews
	 * @param controller
	 * @param idProduct
	 */
	public void getReviews(ProductsController controller, int idProduct) {
		// GET <URLbase>/products/{idProduct}/reviews
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "products/" + idProduct + "/reviews");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get product
	 * @param controller
	 * @param idProduct
	 */
	public void getProduct(ProductsController controller, int idProduct) {
		// GET <URLbase>/products/{idProduct}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "products/" + idProduct);
		new AsyncRequest(this).execute(info);
	}	
	
	
	
	/**
	 * Get product images
	 * @param controller
	 * @param idProduct
	 */
	public void getProductImages(ProductsController controller, int idProduct) {
		// GET <URLbase>/products/{idProduct}/product_images
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "products/" + idProduct + "/product_images");
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get product's front image
	 * @param controller
	 * @param idProduct
	 */
	public void getProductFrontImage(ProductsController controller, int idProduct) {
		// GET <URLbase>/products/{idProduct}/product_images?type={ProductImage.TYPE_FRONT}
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "products/" + idProduct + "/product_images?type=" + ProductImage.TYPE_FRONT);
		new AsyncRequest(this).execute(info);
	}
	
	
	
	/**
	 * Get related products
	 * @param controller
	 * @param idProduct
	 */
	public void getRelatedProducts(ProductsController controller, int idProduct) {
		// GET <URLbase>/products/{idProduct}/related_products
		InfoRequest info = new InfoRequest(controller, HttpMethod.GET, HOST + "products/" + idProduct + "/related_products");
		new AsyncRequest(this).execute(info);
	}
	
	
}
