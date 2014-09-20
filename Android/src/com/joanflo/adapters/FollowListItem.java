package com.joanflo.adapters;

import java.net.URL;
import android.content.Context;

public class FollowListItem {

	
	private URL url;
	private CharSequence name;
	private CharSequence nick;
	private boolean followed;
	private boolean sameUser;
	
	
	
	public FollowListItem(Context context, URL url, CharSequence name, CharSequence nick, boolean followed, boolean sameUser) {
		this.url = url;
		this.name = name;
		this.nick = nick;
		this.followed = followed;
		this.sameUser = sameUser;
	}

	

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
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
	

	public boolean isFollowed() {
		return followed;
	}
	
	public void setFollowed(boolean followed) {
		this.followed = followed;
	}
	

	public boolean isSameUser() {
		return sameUser;
	}
	
	public void setSameUser(boolean sameUser) {
		this.sameUser = sameUser;
	}
	
	
}