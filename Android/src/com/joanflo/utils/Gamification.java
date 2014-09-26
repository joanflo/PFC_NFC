package com.joanflo.utils;

import java.sql.Timestamp;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.joanflo.models.Badge;
import com.joanflo.models.User;
import com.joanflo.tagit.HomeActivity;
import com.joanflo.tagit.R;

/**
 * Class to handle user's points and achievements.
 * @author Joanflo
 */
public class Gamification {

	
	/**
	 * Check gamification conditions when the app starts
	 * @param activity
	 */
	public static void check(HomeActivity activity) {
		// get data
		Timestamp currentDate = new Timestamp(System.currentTimeMillis());
		LocalStorage storage = LocalStorage.getInstance();
		User user = storage.getUser(activity);
		String lastDateTxt = storage.getLastDate(activity);
		Timestamp lastDate;
		if (lastDateTxt != null) {
			lastDate = Time.convertStringToTimestamp(lastDateTxt);
		} else {
			lastDate = new Timestamp(System.currentTimeMillis());
		}
		int consecutiveDays = storage.getConsecutiveDaysNumber(activity);
		
		// check if last execution wasn't at the current date
		if (user != null) {
			if (Time.differenceInDays(currentDate, lastDate) > 0) {
				activity.updateUserPoints(Gamification.POINTS_OPEN_APP);
				String currentDateTxt = Time.convertDateToString(currentDate);
				storage.saveLastDate(activity, currentDateTxt);
			}
		}
		
		// check if last execution was exactly one day ago
		if (Time.differenceInDays(currentDate, lastDate) == 1) {
			consecutiveDays++;
			// 5 consecutive days executing app?
			if (consecutiveDays == 5) {
				activity.createAchievement(Gamification.BADGE_5DAYS);
			}
		} else {
			consecutiveDays = 0;
		}
		storage.setConsecutiveDaysNumber(activity, consecutiveDays);
		
		// check if registration date was exactly one year ago
		if (user != null) {
			Timestamp registrationDate = user.getRegistration();
			if (Time.differenceInDays(currentDate, registrationDate) == 365) {
				activity.createAchievement(Gamification.BADGE_1YEAR);
			}
		}
	}
		
	
	
	public static final int POINTS_NEWBIE = 0;
	public static final int POINTS_SHARE_NFC = 1;
	public static final int POINTS_SHARE_SOCIAL_NETWORKS = 2;
	public static final int POINTS_REVIEW_PRODUCT = 3;
	public static final int POINTS_BUY_PRODUCT = 4;
	public static final int POINTS_OPEN_APP = 5;
	
	
	/**
	 * Get points number corresponding to the given event
	 * @param event
	 * @return
	 */
	public static int getPoints(int event) {
		int points = 0;
		
		switch (event) {
		case POINTS_NEWBIE:
			points = 10;
			break;
		case POINTS_SHARE_NFC:
			points = 5;
			break;
		case POINTS_SHARE_SOCIAL_NETWORKS:
			points = 5;
			break;
		case POINTS_REVIEW_PRODUCT:
			points = 10;
			break;
		case POINTS_BUY_PRODUCT:
			points = 20;
			break;
		case POINTS_OPEN_APP:
			points = 5;
			break;
		}
		
		return points;
	}
	
	
	/**
	 * Get points description corresponding to the given event
	 * @param activity
	 * @param event
	 * @return
	 */
	public static CharSequence getPointsDescription(Activity activity, int event) {
		String[] descriptions = activity.getResources().getStringArray(R.array.points_descriptions);
		CharSequence description = "";
		
		switch (event) {
		case POINTS_NEWBIE:
			description = descriptions[0];
			break;
		case POINTS_SHARE_NFC:
			description = descriptions[1];
			break;
		case POINTS_SHARE_SOCIAL_NETWORKS:
			description = descriptions[2];
			break;
		case POINTS_REVIEW_PRODUCT:
			description = descriptions[3];
			break;
		case POINTS_BUY_PRODUCT:
			description = descriptions[4];
			break;
		case POINTS_OPEN_APP:
			description = descriptions[5];
			break;
		}
		
		return description;
	}
	
	
	/**
	 * Show toast notification with the new points
	 * @param activity
	 * @param newPoints
	 * @param totalPoints
	 * @param description
	 */
	public static void showToastPoints(Activity activity, int newPoints, int totalPoints, CharSequence description) {
		// inflate toast layout
		LayoutInflater inflater = activity.getLayoutInflater();
		ViewGroup vg = (ViewGroup) activity.findViewById(R.id.toast_layout_points);
		View layout = inflater.inflate(R.layout.toast_points, vg);
		
		// set new points
		TextView tv = (TextView) layout.findViewById(R.id.textView_toastpoints_newpoints);
		CharSequence newPointsTxt = "+" + String.valueOf(newPoints) + " " + activity.getResources().getString(R.string.title_point);
		tv.setText(newPointsTxt);
		
		// set total points
		tv = (TextView) layout.findViewById(R.id.textView_toastpoints_totalpoints);
		CharSequence totalPointsTxt = String.valueOf(totalPoints);
		tv.setText(totalPointsTxt);

		// set description
		tv = (TextView) layout.findViewById(R.id.textView_toastpoints_description);
		tv.setText(description);
		
		// create toast
		Toast toast = new Toast(activity.getApplicationContext());
		int height = activity.getActionBar().getHeight();
		toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, height + 50);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}

	
	
	public static final int BADGE_NEWBIE = 0;
	public static final int BADGE_5DAYS = 1;
	public static final int BADGE_1YEAR = 2;
	public static final int BADGE_1SHARE = 3;
	public static final int BADGE_10SHARES = 4;
	public static final int BADGE_1REVIEW = 5;
	public static final int BADGE_10REVIEWS = 6;
	public static final int BADGE_NFC = 7;
	public static final int BADGE_1FOLLOWING = 8;
	public static final int BADGE_1FOLLOWER = 9;
	public static final int BADGE_100FOLLOWERS = 10;
	public static final int BADGE_1PURCHASE = 11;
	public static final int BADGE_10PURCHASES = 12;
	
	
	/**
	 * Get badge object corresponding to the given event
	 * @param event
	 * @return
	 */
	public static Badge getBadge(int event) {
		Badge badge = null;
		switch (event) {
		case BADGE_NEWBIE:
			// You've signed up for the app.
			badge = new Badge("Newbie!", "");
			break;
		case BADGE_5DAYS:
			// You've logged in 5 days in a row.
			badge = new Badge("Constant", "");
			break;
		case BADGE_1YEAR:
			// You've used the app for 1 year
			badge = new Badge("Veteran!", "");
			break;
			
		case BADGE_NFC:
			// You've used your phone to read an NFC tag of a product
			badge = new Badge("Tag it!", "");
			break;
		case BADGE_1SHARE:
			// You've shared for the first time a product
			badge = new Badge("Sharing", "");
			break;
		case BADGE_10SHARES:
			// You've shared at least 10 products
			badge = new Badge("Sharing is life!", "");
			break;
			
		case BADGE_1REVIEW:
			// You've reviewed a product for the first time.
			badge = new Badge("First review", "");
			break;
		case BADGE_10REVIEWS:
			// You've reviewed at least 10 different products.
			badge = new Badge("Guru", "");
			break;
			
		case BADGE_1FOLLOWING:
			// You've followed your first user.
			badge = new Badge("Let's be friends!", "");
			break;
		case BADGE_1FOLLOWER:
			// You've got your first followed.
			badge = new Badge("First friend", "");
			break;
		case BADGE_100FOLLOWERS:
			// You have at least 100 followers.
			badge = new Badge("Create trend", "");
			break;
			
		case BADGE_1PURCHASE:
			// You've bought a product for the first time.
			badge = new Badge("Buyer", "");
			break;
		case BADGE_10PURCHASES:
			// You've bought at least 10 products.
			badge = new Badge("Shopaholic", "");
			break;
		}
		
		return badge;
	}
	
	
	/**
	 * Show toast notification with the new badge
	 * @param activity
	 * @param badgeName
	 * @param description
	 * @param date
	 */
	public static void showToastBadge(Activity activity, CharSequence badgeName, CharSequence description, CharSequence date) {
		// inflate toast layout
		LayoutInflater inflater = activity.getLayoutInflater();
		ViewGroup vg = (ViewGroup) activity.findViewById(R.id.toast_layout_achievement);
		View layout = inflater.inflate(R.layout.toast_achievement, vg);

		// set image
		ImageView iv = (ImageView) layout.findViewById(R.id.imageView_toastachievement_image);
		Drawable d = AssetsUtils.getImageFromAssets(activity, AssetsUtils.BADGES_DIRECTORY, String.valueOf(badgeName));
		iv.setImageDrawable(d);
		iv.setContentDescription(date);
		
		// set title
		TextView tv = (TextView) layout.findViewById(R.id.textView_toastachievement_badgename);
		tv.setText(badgeName);
		
		// set description
		tv = (TextView) layout.findViewById(R.id.textView_toastachievement_description);
		tv.setText(description);
		
		// create toast
		Toast toast = new Toast(activity.getApplicationContext());
		int height = activity.getActionBar().getHeight();
		toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, height + 50);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}
	
}