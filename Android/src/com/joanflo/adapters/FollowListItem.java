package com.joanflo.adapters;

import com.joanflo.tagit.R;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class FollowListItem {

	
	private Drawable thumb;
	private CharSequence name;
	private CharSequence nick;
	private boolean follower;
	private boolean followed;
	
	private CharSequence userEmail;
	
	
	
	public FollowListItem(Context context, CharSequence url, CharSequence name, CharSequence nick, boolean follower, boolean followed, CharSequence userEmail) {
		this.thumb = context.getResources().getDrawable(R.drawable.logo_base); //url
		this.name = name;
		this.nick = nick;
		this.follower = follower;
		this.followed = followed;
		this.userEmail = userEmail;
	}



	public Drawable getThumb() {
		return thumb;
	}

	public void setThumb(Drawable thumb) {
		this.thumb = thumb;
	}


	public CharSequence getName() {
		return name;
	}

	public void setName(CharSequence name) {
		this.name = name;
	}


	public CharSequence getNick() {
		return nick;
	}

	public void setNick(CharSequence nick) {
		this.nick = nick;
	}


	public boolean isFollower() {
		return follower;
	}
	
	public void setFollower(boolean follower) {
		this.follower = follower;
	}


	public boolean isFollowed() {
		return followed;
	}
	
	public void setFollowed(boolean followed) {
		this.followed = followed;
	}


	public CharSequence getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(CharSequence userEmail) {
		this.userEmail = userEmail;
	}
	
	
	public void followUnfollow() {
		followed = !followed;
	}
	
	
}