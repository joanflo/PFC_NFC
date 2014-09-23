package com.joanflo.utils;

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
import com.joanflo.tagit.R;

public class Gamification {

	
	public static final int POINTS_NEWBIE = 0;
	public static final int POINTS_SHARE_NFC = 1;
	public static final int POINTS_SHARE_SOCIAL_NETWORKS = 2;
	public static final int POINTS_REVIEW_PRODUCT = 3;
	public static final int POINTS_BUY_PRODUCT = 4;
	public static final int POINTS_OPEN_APP = 5;
	
	
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
	
	
	
	public static void showToastBadge(Activity activity, CharSequence badgeName, CharSequence description, CharSequence date) {
		// inflate toast layout
		LayoutInflater inflater = activity.getLayoutInflater();
		ViewGroup vg = (ViewGroup) activity.findViewById(R.id.toast_layout_root);
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
	
}