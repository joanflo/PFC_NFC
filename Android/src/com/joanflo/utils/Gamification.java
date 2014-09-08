package com.joanflo.utils;

import com.joanflo.models.Badge;

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
	
	/*
	public static Badge getBadge(int event) {
		Badge badge = null;
		
		switch (event) {
		case BADGE_NEWBIE:
			badge = new Badge("newbie", "description", BADGE_NEWBIE);
			break;
		case BADGE_5DAYS:
			badge = new Badge("5 days", "description", BADGE_5DAYS);
			break;
		case BADGE_1YEAR:
			badge = new Badge("1 year", "description", BADGE_1YEAR);
			break;
		case BADGE_1SHARE:
			badge = new Badge("1 share", "description", BADGE_1SHARE);
			break;
		case BADGE_10SHARES:
			badge = new Badge("10 shares", "description", BADGE_10SHARES);
			break;
		case BADGE_1REVIEW:
			badge = new Badge("1 review", "description", BADGE_1REVIEW);
			break;
		case BADGE_10REVIEWS:
			badge = new Badge("1 review", "description", BADGE_10REVIEWS);
			break;
		case BADGE_NFC:
			badge = new Badge("nfc", "description", BADGE_NFC);
			break;
		case BADGE_1FOLLOWING:
			badge = new Badge("1 following", "description", BADGE_1FOLLOWING);
			break;
		case BADGE_1FOLLOWER:
			badge = new Badge("1follower", "description", BADGE_1FOLLOWER);
			break;
		case BADGE_100FOLLOWERS:
			badge = new Badge("100followers", "description", BADGE_100FOLLOWERS);
			break;
		case BADGE_1PURCHASE:
			badge = new Badge("1purchase", "description", BADGE_1PURCHASE);
			break;
		case BADGE_10PURCHASES:
			badge = new Badge("10purchases", "description", BADGE_10PURCHASES);
			break;
		}
		
		return badge;
	}
	*/
	
}