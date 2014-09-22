package com.joanflo.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.joanflo.utils.Gamification;
import com.joanflo.utils.SearchUtils;
import com.joanflo.utils.Time;

public class User {


	// primary key
	private CharSequence userEmail;
	
	// foreign keys
	private City city;
	private Language language;
	private List<Purchase> purchases;
	private List<Friendship> usersFollowing;
	private List<Friendship> usersFollower;
	private List<Achievement> achievements;
	private List<Review> reviews;
	private List<Wish> wishes;
	
	// user info
	private CharSequence nick;
	private CharSequence name;
	private CharSequence surname;
	private int age;
	private CharSequence password;
	private CharSequence phone;
	private CharSequence direction;
	private Timestamp registration;
	private int points;
	
	
	
	// user from database
	public User(CharSequence userEmail, City city, Language language, List<Purchase> purchases, 
			List<Friendship> usersFollowing, List<Friendship> usersFollower, List<Achievement> achievements, 
			List<Review> reviews, List<Wish> wishes, CharSequence nick, CharSequence name, CharSequence surname, 
			int age, CharSequence password, CharSequence phone, CharSequence direction, Timestamp registration, int points) {
		
		this.userEmail = userEmail;
		this.city = city;
		this.language = language;
		this.purchases = purchases;
		this.usersFollowing = usersFollowing;
		this.usersFollower = usersFollower;
		this.achievements = achievements;
		this.reviews = reviews;
		this.wishes = wishes;
		this.nick = nick;
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.password = password;
		this.phone = phone;
		this.direction = direction;
		this.registration = registration;
		this.points = points;
	}
	
	// new user
	public User(CharSequence userEmail, City city, Language language, CharSequence nick, CharSequence name, 
			CharSequence surname, int age, CharSequence password, CharSequence phone, CharSequence direction) {
		
		this.userEmail = userEmail;
		this.city = city;
		this.language = language;
		this.nick = nick;
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.password = password;
		this.phone = phone;
		this.direction = direction;
		
		// there's no purchases, wishes, following users, followers and reviews initially
		this.purchases = new ArrayList<Purchase>();
		this.wishes = new ArrayList<Wish>();
		this.usersFollowing = new ArrayList<Friendship>();
		this.usersFollower = new ArrayList<Friendship>();
		this.reviews = new ArrayList<Review>();
		
		// get current time
		long time = System.currentTimeMillis();
		this.registration = new java.sql.Timestamp(time);
		
		// User's registration
		this.points = Gamification.POINTS_NEWBIE;
		this.achievements = new ArrayList<Achievement>();
	}
	
	public User(JSONObject jUser) throws JSONException {
		// user email
		if (jUser.has("userEmail")) {
			this.userEmail = jUser.getString("userEmail");
		}
		// nick
		if (jUser.has("nick")) {
			this.nick = jUser.getString("nick");
		}
		// name
		if (jUser.has("name")) {
			this.name = jUser.getString("name");
		}
		// surname
		if (jUser.has("surname")) {
			this.surname = jUser.getString("surname");
		}
		// age
		if (jUser.has("age")) {
			this.age = jUser.getInt("age");
		}
		// password
		if (jUser.has("password")) {
			this.password = jUser.getString("password");
		}
		// phone
		if (jUser.has("phone")) {
			this.phone = jUser.getString("phone");
		}
		// direction
		if (jUser.has("direction")) {
			this.direction = jUser.getString("direction");
		}
		// registration
		if (jUser.has("registration")) {
			this.registration = Time.convertStringToTimestamp(jUser.getString("registration"));
		}
		// points
		if (jUser.has("points")) {
			this.points = jUser.getInt("points");
		} else {
			this.points = 0;
		}
		// city
		if (jUser.has("cityName")) {
			this.city = new City(jUser.getString("cityName"));
		} else if (jUser.has("city")) {
			this.city = new City(jUser.getJSONObject("city"));
		}
		// language
		if (jUser.has("languageName")) {
			this.language = new Language(jUser.getString("languageName"));
		} else if (jUser.has("language")) {
			this.language = new Language(jUser.getJSONObject("language"));
		}
	}
	
	
	
	public City getCity() {
		return city;
	}
	
	public void setCity(City city) {
		this.city = city;
	}
	
	
	public Language getLanguage() {
		return language;
	}
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	
	public CharSequence getName() {
		return name;
	}
	
	public void setName(CharSequence name) {
		this.name = name;
	}
	
	
	public CharSequence getSurname() {
		return surname;
	}
	
	public void setSurname(CharSequence surname) {
		this.surname = surname;
	}
	
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	
	public CharSequence getPassword() {
		return password;
	}
	
	public void setPassword(CharSequence password) {
		this.password = password;
	}
	
	
	public CharSequence getPhone() {
		if (phone == null) {
			return "-";
		}
		return phone;
	}
	
	public void setPhone(CharSequence phone) {
		this.phone = phone;
	}
	
	
	public CharSequence getDirection() {
		if (direction == null) {
			return "-";
		}
		return direction;
	}
	
	public void setDirection(CharSequence direction) {
		this.direction = direction;
	}
	
	
	public CharSequence getUserEmail() {
		return userEmail;
	}
	
	
	public List<Purchase> getPurchases() {
		return purchases;
	}
	
	public List<Purchase> getPurchaseHistory() {
		// return all finished purchases
		List<Purchase> finishedPurchases = new ArrayList<Purchase>();
		Iterator<Purchase> it = purchases.iterator();
		while (it.hasNext()) {
			Purchase purchase = it.next();
			if (purchase.getStatus() == Purchase.STATUS_FINISHED) {
				finishedPurchases.add(purchase);
			}
		}
		return finishedPurchases;
	}
	
	public Purchase getPurchaseCart() {
		// search not finished purchases
		Iterator<Purchase> it = purchases.iterator();
		while (it.hasNext()) {
			Purchase purchase = it.next();
			if (purchase.getStatus() == Purchase.STATUS_PENDING) {
				return purchase;
			}
		}
		return null;
	}
	
	public Purchase searchPurchaseById(int purchaseId) {
		Iterator<Purchase> it = purchases.iterator();
		while (it.hasNext()) {
			Purchase purchase = it.next();
			if (purchase.getIdPurchase() == purchaseId) {
				return purchase;
			}
		}
		return null;
	}
	
	
	public void addPurchase(Purchase purchase) {
		purchases.add(purchase);
	}
	
	
	public List<Friendship> getUsersFollowing() {
		return usersFollowing;
	}
	
	public void addUserFollowing(Friendship relation) {
		usersFollowing.add(relation);
	}
	
	public void addUserFollowing(User followed) {
		Friendship relation = new Friendship(followed, this);
		usersFollowing.add(relation);
	}
	
	public void removeUserFollowing(User followed) {
		List<User> following = getUsers(usersFollowing, true);
		int position = following.indexOf(followed);
		if (position != -1) {
			usersFollowing.remove(position);
		}
	}
	
	
	public List<Friendship> getUsersFollower() {
		return usersFollower;
	}
	
	public void addUserFollower(Friendship relation) {
		usersFollower.add(relation);
	}
	
	public void addUserFollower(User follower) {
		Friendship relation = new Friendship(this, follower);
		usersFollower.add(relation);
	}
	
	public void removeUserFollower(User follower) {
		List<User> followers = getUsers(usersFollower, true);
		int position = followers.indexOf(follower);
		if (position != -1) {
			usersFollower.remove(position);
		}
	}
	
	
	public List<Achievement> getAchievements() {
		return achievements;
	}
	
	public void addAchievement(Achievement achievement) {
		achievements.add(achievement);
	}
	
	
	public List<Review> getReviews() {
		return reviews;
	}
	
	public void addReview(Review review) {
		reviews.add(review);
	}
	
	
	public List<Wish> getWishes() {
		return wishes;
	}
	
	public void addWish(Wish wish) {
		wishes.add(wish);
	}
	
	
	public CharSequence getNick() {
		return nick;
	}
	
	
	public Timestamp getRegistration() {
		return registration;
	}
	
	
	public int getPoints() {
		return points;
	}
	
	public void updatePoints(int newPoints) {
		points += newPoints;
	}
	
	
	public boolean isFollowedBy(User user) {
		List<User> followers = getUsers(usersFollower, true);
		User u = SearchUtils.searchUserByEmail(user.getUserEmail(), followers);
		return u != null;
	}
	
	public boolean isFollowingTo(User user) {
		List<User> following = getUsers(usersFollowing, false);
		User u = SearchUtils.searchUserByEmail(user.getUserEmail(), following);
		return u != null;
	}
	
	public List<User> getUsers(List<Friendship> relations, boolean getFollowers) {
		List<User> users = new ArrayList<User>();
		Iterator<Friendship> it = relations.iterator();
		while (it.hasNext()) {
			Friendship relation = it.next();
			if (getFollowers) {
				users.add(relation.getUserFollower());
			} else {
				users.add(relation.getUserFollowing());
			}
		}
		return users;
	}
	
	
	
	public JSONObject convertToJSON() {
		JSONObject jUser = new JSONObject();
		
		try {
			jUser.put("userEmail", userEmail);
			jUser.put("nick", nick);
			jUser.put("name", name);
			jUser.put("surname", surname);
			jUser.put("age", age);
			jUser.put("password", password);
			jUser.put("phone", phone);
			jUser.put("direction", direction);
			if (registration != null) {
				jUser.put("registration", registration.toString());
			}
			jUser.put("points", points);
			if (city != null) {
				JSONObject jCity = city.convertToJSON();
				jUser.put("city", jCity);
			}
			if (language != null) {
				JSONObject jLanguage = language.convertToJSON();
				jUser.put("language", jLanguage);
			}
			// 'purchases', 'usersFollowing', 'usersFollower', 'achievements', 'reviews', 'wishes' ignored
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jUser;
	}
	
	
	
	@Override
	public boolean equals(Object o) {
		User userAux = (User) o;
		return this.userEmail.equals(userAux.getUserEmail());
	}
	
	
}