package com.joanflo.tagit;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.joanflo.adapters.FollowListAdapter;
import com.joanflo.adapters.FollowListItem;
import com.joanflo.controllers.UsersController;
import com.joanflo.models.User;
import com.joanflo.utils.AssetsUtils;
import com.joanflo.utils.Gamification;
import com.joanflo.utils.LocalStorage;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Follow list activity
 * @author Joanflo
 */
public class FollowsListActivity extends BaseActivity implements Button.OnClickListener {


	private boolean seeFollowers;
	private CharSequence currentUserEmail;
	private CharSequence loggedUserEmail;
	
	private List<User> currentUsersFollows;
	private List<User> loggedUsersFollows;
	private List<FollowListItem> followItems;
	
	private int currentPosition;
	private View currentView;
	
	private int requestsNumber;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_followslist);
		super.setTitle(R.string.title_follows);
		
        
        // get info from last activity
        Bundle bundle = getIntent().getExtras();
        seeFollowers = bundle.getBoolean("seeFollowers");
        
        currentUserEmail = bundle.getCharSequence("userEmail");
        loggedUserEmail = LocalStorage.getInstance().getUserEmail(this);
        
        currentUsersFollows = null;
        loggedUsersFollows = new ArrayList<User>();
        
        requestsNumber = 2;
        super.showProgressBar(true);
        
        UsersController controller = new UsersController(this);
        if (seeFollowers) {
            // call web service
        	controller.getFollowers(currentUserEmail);
        } else {
            // call web service
            controller.getFollowing(currentUserEmail);
        }
        // call web service
    	controller.getFollowing(loggedUserEmail);
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// uncheck elements
        ListView followList = (ListView) findViewById(R.id.listView_follow);
        followList.setItemChecked(currentPosition, false);
	}
	
	
	
	public void friendshipsReceived(List<User> users, Boolean followers) {
		if (followers != null) {
			if (seeFollowers && followers) {
				this.currentUsersFollows = users;
			} else if (!seeFollowers && !followers) {
				if (this.currentUsersFollows == null && requestsNumber != 1) {
					this.currentUsersFollows = users;
				} else {
					this.loggedUsersFollows = users;
				}
			} else {
				this.loggedUsersFollows = users;
			}
		}
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void friendshipRemoved(boolean successful) {
		if (successful) {
			Toast.makeText(this, R.string.toast_deleteitem, Toast.LENGTH_SHORT).show();
			if (currentUserEmail.equals(loggedUserEmail)) {
				LocalStorage.getInstance().updateFollowingCount(this, false);
				removeItem();
			} else {
				followItems.get(currentPosition).setFollowed(false);
				// hide animation
				showImageButton(currentView, true);
			}
		} else {
			Toast.makeText(this, R.string.toast_problem_deleteitem, Toast.LENGTH_SHORT).show();
			// hide animation
			showImageButton(currentView, true);
		}
	}
	
	
	
	public void friendshipCreated() {
		followItems.get(currentPosition).setFollowed(true);
		if (currentUserEmail.equals(loggedUserEmail)) {
			LocalStorage.getInstance().updateFollowingCount(this, true);
		}
		
		// hide animation
		showImageButton(currentView, true);
	}
	
	
	
	private synchronized void checkLastRequest() {
		requestsNumber--;
		if (requestsNumber == 0) {
			// last request
			if (currentUsersFollows == null) { // currentUsersFollows.size() == 0
				// empty users list
				ImageView iv = (ImageView) findViewById(R.id.imageview_followList_empty);
				iv.setVisibility(View.VISIBLE);
				ListView productList = (ListView) findViewById(R.id.listView_follow);
				productList.setVisibility(View.GONE);
				Toast.makeText(this, R.string.toast_problem_empty, Toast.LENGTH_LONG).show();
			
			} else {
				checkGamification();
				prepareList();
			}
			
	        super.showProgressBar(false);
		}
	}
	
	
	
	private void prepareList() {
		// creating followers/following list items
		followItems = new ArrayList<FollowListItem>();
		Iterator<User> it = currentUsersFollows.iterator();
        while(it.hasNext()) {
        	User user = (User) it.next();
        	
        	// user name
        	CharSequence name = user.getName() + " " + user.getSurname();
        	// nick
        	CharSequence nick = "@" + user.getNick();
        	// current user followed?
        	boolean currentUserFollowed = loggedUsersFollows.contains(user);
        	// thumb url
        	URL url;
        	try {
    			String imagePath = "profiles/" + user.getNick() + ".jpg";
    			url = AssetsUtils.getUrlFromPath(imagePath);
    		} catch (MalformedURLException e) {
    			url = null;
    		}
        	// are we followed by the current user?
        	boolean logedUserFollowed = user.getUserEmail().equals(loggedUserEmail);
        	
        	// create list item
        	FollowListItem followItem = new FollowListItem(this, url, name, nick, currentUserFollowed, logedUserFollowed);
	        followItems.add(followItem);
        }
		
		// setting the follow list adapter
        FollowListAdapter adapter = new FollowListAdapter(getApplicationContext(), followItems, this);
        ListView followList = (ListView) findViewById(R.id.listView_follow);
        followList.setAdapter(adapter);
        
        // setting the category click listener
        followList.setOnItemClickListener(new FollowClickListener());
	}



	private void removeItem() {
		// remove from array lists
		currentUsersFollows.remove(currentPosition);
		followItems.remove(currentPosition);
		// remove from list view
		ListView followList = (ListView) findViewById(R.id.listView_follow);
		FollowListAdapter badapter = (FollowListAdapter) followList.getAdapter();
		badapter.notifyDataSetChanged();
	}
	
	
	
	private void showImageButton(View v, boolean show) {
		ImageButton ib = (ImageButton) v;
		if (show) {
			boolean userFollowed = followItems.get(currentPosition).isFollowed();
			if (userFollowed) {
				ib.setImageResource(R.drawable.ic_following);
			} else {
				ib.setImageResource(R.drawable.ic_notfollowing);
			}
			ib.setBackgroundResource(R.drawable.button_selector2);
		} else {
			ib.setImageResource(android.R.color.transparent);
			ib.setBackgroundResource(R.drawable.animation_refresh);
			AnimationDrawable frameAnimation = (AnimationDrawable) ib.getBackground();
			frameAnimation.start();
		}
	}



	@Override
	public void onClick(View v) {
		onClickButton(v);
	}



	public void onClickButton(View v) {
		switch (v.getId()) {
		case R.id.button_follow_follow:
			currentView = v;
			currentPosition = (Integer) v.getTag();
			// get user email clicked
			CharSequence userEmailClicked = currentUsersFollows.get(currentPosition).getUserEmail();
			// user clicked followed?
			boolean userFollowed = followItems.get(currentPosition).isFollowed();
			// perform action
			UsersController controller = new UsersController(this);
			if (userFollowed) {
				// clicked user is followed by current user --> unfollow
				controller.unfollowUser(loggedUserEmail, userEmailClicked);
			} else {
				// clicked user isn't followed by current user -> follow
				controller.followUser(loggedUserEmail, userEmailClicked);
			}
			// show button animation
			showImageButton(v, false);
			break;
			
		default:
			super.onClickButton(v);
			break;
		}
    }
	
	
	
	private void checkGamification() {
		if (currentUserEmail.equals(loggedUserEmail)) {
			LocalStorage storage = LocalStorage.getInstance();
			if (seeFollowers) {
				// followers
				int followersCount = storage.getFollowersCount(this);
				if (followersCount != currentUsersFollows.size()) {
					storage.setFollowersCount(this, followersCount);
					if (followersCount == 1) {
						super.createAchievement(Gamification.BADGE_1FOLLOWER);
					} else if (followersCount == 100) {
						super.createAchievement(Gamification.BADGE_100FOLLOWERS);
					}
				}
			}
			// following
			int followingCount = storage.getFollowingCount(this);
			if (followingCount != loggedUsersFollows.size()) {
				storage.setFollowingCount(this, followingCount);
				if (followingCount == 1) {
					super.createAchievement(Gamification.BADGE_1FOLLOWING);
				}
			}
		}
	}
	
	

	private class FollowClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// go to user profile
			currentPosition = position;
			CharSequence email = currentUsersFollows.get(position).getUserEmail();
			Intent i = new Intent(getBaseContext(), UserProfileActivity.class);
			i.putExtra("userEmail", email);
			startActivity(i);
		}
		
	}
	
	
}
