package com.joanflo.tagit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.joanflo.adapters.FollowListAdapter;
import com.joanflo.adapters.FollowListItem;
import com.joanflo.models.City;
import com.joanflo.models.Country;
import com.joanflo.models.Friendship;
import com.joanflo.models.Language;
import com.joanflo.models.Region;
import com.joanflo.models.User;
import com.joanflo.utils.SearchUtils;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;


public class FollowsListActivity extends BaseActivity implements Button.OnClickListener {


	private User currentUser;
	private List<FollowListItem> followItems;
	private int currentPosition;
	private boolean seeFollowers;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// update the main content by replacing view
		LayoutInflater factory = LayoutInflater.from(this);
		View activityView = factory.inflate(R.layout.activity_followslist, null);
		// inflate activity layout
        FrameLayout viewContainer = (FrameLayout) findViewById(R.id.frame_container);
        viewContainer.addView(activityView);
        
        // get info from last activity
        Bundle bundle = getIntent().getExtras();
        seeFollowers = bundle.getBoolean("seeFollowers");
        CharSequence userEmail = bundle.getCharSequence("userEmail");
        
        // prepare list
        prepareList(userEmail);
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// uncheck elements
        ListView followList = (ListView) findViewById(R.id.listView_follow);
        followList.setItemChecked(currentPosition, false);
	}
	
	
	
	private void prepareList(CharSequence userEmail) {
		loadData(userEmail);
		
		// get iterator
		List<Friendship> friendships;
		if (seeFollowers) {
			friendships = currentUser.getUsersFollower();
		} else {
			friendships = currentUser.getUsersFollowing();
		}
		Iterator<?> it = friendships.iterator();
		
		// creating category list items
		followItems = new ArrayList<FollowListItem>();
        while(it.hasNext()) {
        	Friendship friendship = (Friendship) it.next();
        	User user;
        	if (seeFollowers) {
        		user = friendship.getUserFollower();
        	} else {
        		user = friendship.getUserFollowing();
        	}
        	
        	CharSequence name = user.getName() + " " + user.getSurname();
        	CharSequence nick = "@" + user.getNick();
        	boolean currentUserFollowed = currentUser.isFollowedBy(user);
        	boolean currentUserFollowing = currentUser.isFollowingTo(user);
        	CharSequence email = user.getUserEmail();
        	
        	FollowListItem followItem = new FollowListItem(this, "", name, nick, currentUserFollowed, currentUserFollowing, email);
	        followItems.add(followItem);
        }
		
		// setting the follow list adapter
        FollowListAdapter adapter = new FollowListAdapter(getApplicationContext(), followItems, this);
        ListView followList = (ListView) findViewById(R.id.listView_follow);
        followList.setAdapter(adapter);
        
        // setting the category click listener
        followList.setOnItemClickListener(new FollowClickListener());
	}



	private void loadData(CharSequence userEmail) {
		Country espanya = new Country("Espanya", null, 34, Country.EURO);
		Region balears = new Region("Balears", null, espanya);
		City palma = new City("Palma", null, balears);
		Language catala = new Language("Català");
		User userActual = new User("prova@prova.com", palma, catala, "Joan_flo", "Joan", "Florit Gomila", 23, "password", "686922414", "Sant Vicenç Ferrer 117");
		
		List<User> users = new ArrayList<User>();
		users.add(userActual);
		currentUser = SearchUtils.searchUserByEmail(userEmail, users);
		
		User u1 = new User("a@prova.com", palma, catala, "usauri_1", "Usuari", "1", 22, "password", "666666666", "Verderol 34");
		User u2 = new User("b@prova.com", palma, catala, "usauri_2", "Usuari", "2", 22, "password", "666666666", "Verderol 34");
		User u3 = new User("c@prova.com", palma, catala, "usauri_3", "Usuari", "3", 22, "password", "666666666", "Verderol 34");
		User u4 = new User("d@prova.com", palma, catala, "usauri_4", "Usuari", "4", 22, "password", "666666666", "Verderol 34");
		User u5 = new User("e@prova.com", palma, catala, "usauri_5", "Usuari", "5", 22, "password", "666666666", "Verderol 34");
		User u6 = new User("f@prova.com", palma, catala, "usauri_6", "Usuari", "6", 22, "password", "666666666", "Verderol 34");
		User u7 = new User("g@prova.com", palma, catala, "usauri_7", "Usuari", "7", 22, "password", "666666666", "Verderol 34");
		
		currentUser.addUserFollower(u1);
		u1.addUserFollowing(currentUser);
		
		currentUser.addUserFollower(u2);
		u2.addUserFollowing(currentUser);
		
		currentUser.addUserFollower(u3);
		u3.addUserFollowing(currentUser);
		
		currentUser.addUserFollower(u4);
		u4.addUserFollowing(currentUser);
		

		currentUser.addUserFollowing(u4);
		u4.addUserFollower(currentUser);
		
		currentUser.addUserFollowing(u5);
		u5.addUserFollower(currentUser);
		
		currentUser.addUserFollowing(u6);
		u6.addUserFollower(currentUser);
		
		currentUser.addUserFollowing(u7);
		u7.addUserFollower(currentUser);
	}



	@Override
	public void onClick(View v) {
		onClickButton(v);
	}



	public void onClickButton(View v) {
		switch (v.getId()) {
		case R.id.button_follow_follow:
			// get user email clicked
			int position = (Integer) v.getTag();
			FollowListItem followItem = followItems.get(position);
			CharSequence userEmail = followItem.getUserEmail();
			
			// get list of displayed users
			List<Friendship> friendships;
			if (seeFollowers) {
				friendships = currentUser.getUsersFollower();
			} else {
				friendships = currentUser.getUsersFollowing();
			}
			List<User> users = currentUser.getUsers(friendships, seeFollowers);
			
			// search user by email
			User user = SearchUtils.searchUserByEmail(userEmail, users);
			
			// follow / unfollow
			ImageButton ib = (ImageButton) v;
			if (followItem.isFollowed()) {
				// user is followed by current user, then current user unfollows user
				ib.setImageResource(R.drawable.ic_notfollowing);
				currentUser.removeUserFollowing(user);
				user.removeUserFollower(currentUser);
			} else {
				// user is not followed by current user, then current user follows user
				ib.setImageResource(R.drawable.ic_following);
				currentUser.addUserFollowing(user);
				user.addUserFollower(currentUser);
			}
			followItem.followUnfollow();
			break;
			
		default:
			super.onClickButton(v);
			break;
		}
    }
	
	
	

	private class FollowClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// go to user profile
			currentPosition = position;
			CharSequence email = followItems.get(position).getUserEmail();
			Intent i = new Intent(getBaseContext(), UserProfileActivity.class);
			i.putExtra("userEmail", email);
			startActivity(i);
		}
		
	}
	
	
}
