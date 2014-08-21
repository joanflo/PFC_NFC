package com.joanflo.network;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.joanflo.models.Achievement;
import com.joanflo.models.Badge;
import com.joanflo.models.Batch;
import com.joanflo.models.Brand;
import com.joanflo.models.Category;
import com.joanflo.models.City;
import com.joanflo.models.Collection;
import com.joanflo.models.Color;
import com.joanflo.models.Country;
import com.joanflo.models.Friendship;
import com.joanflo.models.Language;
import com.joanflo.models.Product;
import com.joanflo.models.ProductImage;
import com.joanflo.models.Purchase;
import com.joanflo.models.PurchaseDetail;
import com.joanflo.models.Region;
import com.joanflo.models.Review;
import com.joanflo.models.Shop;
import com.joanflo.models.Size;
import com.joanflo.models.Tax;
import com.joanflo.models.User;
import com.joanflo.models.Wish;

public class RESTClient {

	
	public static final CharSequence HOST = "http://alumnes-ltim.uib.es/~jflorit/";
	
	
	
	public boolean logInUser(CharSequence userEmail, CharSequence password) {
		// GET <URLbase>/users/{userEmail}?fields=password
		// comparar el que ens torna la consulta amb els paràmetres del mètode
		return true;
	}
	
	
	
	public User signInUser(User user) {
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
		// PUT <URLbase>/users/{userEmail}?userEmail={userEmail}&cityName={cityName}&languageName={languageName}&nick={nick}&name={name}&surname={surname}&age={age}&password={password}&phone={phone}&direction={direction}
		return null;
	}
	
	
	
	public ArrayList<Language> getLanguages() {
		// GET <URLbase>/languages
		return null;
	}
	
	
	
	public ArrayList<Country> getCountries() {
		// GET <URLbase>/countries
		return null;
	}
	
	
	
	public Country getCountry(CharSequence cityName) {
		// GET <URLbase>/countries?cityName={cityName}
		return null;
	}
	
	
	
	public ArrayList<Region> getRegions(CharSequence countryName) {
		// GET <URLbase>/countries/{countryName}/regions
		return null;
	}
	
	
	
	public ArrayList<City> getCities(CharSequence countryName, CharSequence regionName) {
		// GET <URLbase>/countries/{countryName}/regions/{regionName}/cities
		return null;
	}
	
	
	
	public ArrayList<Product> getProducts(CharSequence queryName) {
		// GET <URLbase>/products?queryName={queryName}
		return null;
	}
	
	
	
	public ArrayList<Product> getProducts(CharSequence queryName, float priceFrom, float priceSince, char coin, CharSequence brandName, int idCategory, float rating) {
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
	
	
	
	public ArrayList<Category> getCategories(int level) {
		// (includes subcategories/products for each category)
		// GET <URLbase>/categories?level={level}
		return null;
	}
	
	
	
	public ArrayList<Category> getSubategories(int idCategory) {
		// (includes sub-subcategories/products for each category)
		// GET <URLbase>/categories/{idCategory}/subcategories
		return null;
	}
	
	
	
	public ArrayList<Shop> getShops(CharSequence cityName) {
		// returns all the shops in the city
		// GET <URLbase>/shops?cityName={cityName}
		return null;
	}
	
	
	
	public ArrayList<Shop> getShops(double latitude, double longitude) {
		// returns the most near shops from current location
		// GET <URLbase>/shops?latitude={latitude}&longitude={longitude}
		return null;
	}
	
	
	
	public Shop getShop(int idShop) {
		// GET <URLbase>/shops/{idShop}
		return null;
	}
	
	
	
	public User getCurrentUser(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}
		return null;
	}
	
	
	
	public User getAnotherUser(CharSequence userEmail) {
		// doesn't returns personal information
		// GET <URLbase>/users/{userEmail}?fields=nick,points
		return null;
	}
	
	
	
	public User updateUserData(User user) {
		// same request
		return signInUser(user);
	}
	
	
	
	public boolean changeUserPassword(CharSequence userEmail, CharSequence password) {
		// PUT <URLbase>/users/{userEmail}?password={password}
		return true;
	}
	
	
	
	public ArrayList<User> getFollowers(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/friendships?following=false
		return null;
	}
	
	
	
	public ArrayList<User> getFollowing(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/friendships?following=true
		return null;
	}
	
	
	
	public Friendship followUser(CharSequence userEmailFollowing, CharSequence userEmailFollowed) {
		// POST <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
		return null;
	}
	
	
	
	public Friendship unfollowUser(CharSequence userEmailFollowing, CharSequence userEmailFollowed) {
		// DELETE <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
		return null;
	}
	
	
	
	public ArrayList<Achievement> getAchievements(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/achievements
		return null;
	}
	
	
	
	public Achievement createAchievement(Achievement achievement) {
		CharSequence userEmail = achievement.getUser().getUserEmail();
		CharSequence badgeName = achievement.getBadge().getBadgeName();
		Timestamp date = achievement.getDate();
		// POST <URLbase>/users/{userEmail}/achievements?badgeName={badgeName}&date={date}
		return null;
	}
	
	
	
	public Achievement getAchievement(CharSequence userEmail, int idAchievement) {
		// GET <URLbase>/users/{userEmail}/achievements/{idAchievement}
		return null;
	}
	
	
	
	public ArrayList<Badge> getBadges() {
		// GET <URLbase>/badges
		return null;
	}
	
	
	
	public Badge getBadge(CharSequence badgeName) {
		// GET <URLbase>/badges/{badgeName}
		return null;
	}
	
	
	
	public ArrayList<Review> getReviews(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/reviews
		return null;
	}
	
	
	
	public ArrayList<Review> getReviews(int idProduct) {
		// GET <URLbase>/products/{idProduct}/reviews
		return null;
	}
	
	
	
	public Review createReview(Review review) {
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
	
	
	
	public Review getReview(CharSequence userEmail, int idComment) {
		// GET <URLbase>/users/{userEmail}/reviews/{idComment}
		return null;
	}
	
	
	
	public ArrayList<Wish> getWishes(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/wishes
		return null;
	}
	
	
	
	public Wish createWish(Wish wish) {
		CharSequence userEmail = wish.getUser().getUserEmail();
		int idProduct = wish.getProduct().getIdProduct();
		Timestamp date = wish.getDate();
		// POST <URLbase>/users/{userEmail}/wishes?idProduct={idProduct}&date={date}
		// equivalent:
		// POST <URLbase>/products/{idProduct}/wishes?userEmail={userEmail}&date={date}
		return null;
	}
	
	
	
	public Wish getWish(CharSequence userEmail, int idWish) {
		// GET <URLbase>/users/{userEmail}/wishes/{idWish}
		return null;
	}
	
	
	
	public Wish deleteWish(CharSequence userEmail, int idWish) {
		// DELETE <URLbase>/users/{userEmail}/wishes/{idWish}
		return null;
	}
	
	
	
	public ArrayList<Purchase> getPurchases(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/purchases
		return null;
	}
	
	
	
	public Purchase getPurchase(CharSequence userEmail, int idPurchase) {
		// GET <URLbase>/users/{userEmail}/purchases/{idPurchase}
		return null;
	}
	
	
	
	public Purchase getCartPurchase(CharSequence userEmail) {
		// GET <URLbase>/users/{userEmail}/purchases?status={Purchase.STATUS_PENDING}
		return null;
	}
	
	
	
	public ArrayList<PurchaseDetail> getPurchaseDetails(CharSequence userEmail, int idPurchase) {
		// GET <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details
		return null;
	}
	
	
	
	public PurchaseDetail getPurchaseDetail(CharSequence userEmail, int idPurchase, int idPurchaseDetail) {
		// GET <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}
		return null;
	}
	
	
	
	public PurchaseDetail createPurchaseDetail(CharSequence userEmail, PurchaseDetail detail) {
		// new purchase detail added to the current purchase (STATUS_PENDING). If doesn't exists, it's created.
		int idPurchase = detail.getPurchase().getIdPurchase();
		int idBatch = detail.getBatch().getIdBatch();
		int units = detail.getUnits();
		// POST <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details?idBatch={idBatch}&units={units}
		return null;
	}
	
	
	
	public PurchaseDetail updatePurchaseDetail(CharSequence userEmail, PurchaseDetail detail) {
		int idPurchaseDetail = detail.getIdPurchaseDetail();
		int idPurchase = detail.getPurchase().getIdPurchase();
		int idBatch = detail.getBatch().getIdBatch();
		int units = detail.getUnits();
		// PUT <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}?idBatch={idBatch}&units={units}
		return null;
	}
	
	
	
	public PurchaseDetail deletePurchaseDetail(CharSequence userEmail, int idPurchase, int idPurchaseDetail) {
		// DELETE <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}
		return null;
	}
	
	
	
	public Batch getBatch(int idBatch) {
		// GET <URLbase>/batches/{idBatch}
		return null;
	}
	
	
	
	public Batch updateBatch(Batch batch) {
		int idBatch = batch.getIdBatch();
		int units = batch.getUnits();
		// PUT <URLbase>/batches/{idBatch}?units={units}
		return null;
	}
	
	
	
	public Tax getTax(int idProduct, CharSequence countryName) {
		// GET <URLbase>/taxes?idProduct={idProduct}&countryName={countryName}
		return null;
	}
	
	
	
	public Size getSize(int idSize) {
		// GET <URLbase>/sizes/{idSize}
		return null;
	}
	
	
	
	public Color getColor(CharSequence colorCode) {
		// GET <URLbase>/colors/{colorCode}
		return null;
	}
	
	
	
	public Product getProduct(int idProduct) {
		// GET <URLbase>/products/{idProduct}
		return null;
	}
	
	
	
	public Brand getBrand(CharSequence brandName) {
		// GET <URLbase>/brands/{brandName}
		return null;
	}
	
	
	
	public Collection getCollection(int idCollection) {
		// GET <URLbase>/collections/{idCollection}
		return null;
	}
	
	
	
	public ArrayList<ProductImage> getProductImages(int idProduct) {
		// GET <URLbase>/products/{idProduct}/product_images
		return null;
	}
	
	
	
	public ProductImage getProductFrontImage(int idProduct) {
		// GET <URLbase>/products/{idProduct}/product_images?type={ProductImage.TYPE_FRONT}
		return null;
	}
	
	
	
	public ArrayList<Batch> getBatches(int idProduct) {
		// GET <URLbase>/batches?idProduct={idProduct}
		return null;
	}
	
	
	
	public ArrayList<Batch> getBatches(int idProduct, int idShop) {
		// GET <URLbase>/batches?idProduct={idProduct}&idShop={idShop}
		return null;
	}
	
	
	
	public ArrayList<Product> getRelatedProducts(int idProduct) {
		// GET <URLbase>/products/{idProduct}/related_products
		return null;
	}
	
	
}
