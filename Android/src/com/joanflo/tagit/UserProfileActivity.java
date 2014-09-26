package com.joanflo.tagit;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.joanflo.adapters.BadgeListAdapter;
import com.joanflo.adapters.BadgeListItem;
import com.joanflo.controllers.UsersController;
import com.joanflo.models.Achievement;
import com.joanflo.models.Badge;
import com.joanflo.models.City;
import com.joanflo.models.Language;
import com.joanflo.models.User;
import com.joanflo.network.ImageLoader;
import com.joanflo.utils.AssetsUtils;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.Time;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * User profile activity
 * @author Joanflo
 */
public class UserProfileActivity extends BaseActivity implements OnItemClickListener {
	
	
	private List<Achievement> achievements;
	private User user;
	private int followersCount = 0;
	private int followingCount = 0;
	
	private CharSequence userEmail;
	
	private int requestsNumber;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_userprofile);
		
        
        // update selected item and title, then close the drawer
        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("drawerPosition");
        super.updateSelected(pos);
        
        super.showProgressBar(true);
        // viewing logged user?
        UsersController controller = new UsersController(this);
        userEmail = bundle.getString("userEmail", null);
        if (userEmail == null) {
        	// logged user
        	requestsNumber = 1; // achievements
        	userEmail = LocalStorage.getInstance().getUserEmail(this);
        	// personal info
        	prepareProfileSection(true);
        } else {
        	// another user
        	requestsNumber = 4; // achievements, user, followers count, following count
        	// call web service
        	controller.getAnotherUser(userEmail);
        	// call web service
    		controller.getFollowers(userEmail);
        	// call web service
    		controller.getFollowing(userEmail);
        	// personal info
        	RelativeLayout rl = (RelativeLayout) findViewById(R.id.section_userprofile_personalinfo);
        	rl.setVisibility(View.GONE);
        	Button b = (Button) findViewById(R.id.button_userprofile_seepurchase);
        	b.setVisibility(View.GONE);
        	b = (Button) findViewById(R.id.button_userprofile_seecart);
        	b.setVisibility(View.GONE);
        }
        // call web service
        controller.getAchievements(userEmail);
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.section_userprofile_personalinfo);
    	if (rl.getVisibility() != View.GONE) {
    		preparePersonalInfoSection();
    	}
	}

	
	
	public void achievementsReceived(List<Achievement> achievements) {
		this.achievements = achievements;
		
		// check if it's the last request
		checkLastRequest();
	}

	
	
	public void userReceived(User user) {
		this.user = user;
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void followersCountReceived(int followersCount) {
		this.followersCount = followersCount;
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void followingCountReceived(int followingCount) {
		this.followingCount = followingCount;
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void zeroCountReceived() {
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	private synchronized void checkLastRequest() {
		requestsNumber--;
		if (requestsNumber == 0) {
			// last request
			if (user != null) {
				prepareProfileSection(false);
			}
			prepareBadgesSection();
	        super.showProgressBar(false);
		}
	}
	
	
	
	private void prepareBadgesSection() {
		GridView gv = (GridView) findViewById(R.id.gridView_userprofile_badges);

		// set container height
		int pxColumnWidth = (gv.getWidth() / 5) - 20;
		ViewGroup.LayoutParams paramsGroup = gv.getLayoutParams();
		paramsGroup.height = (pxColumnWidth + 20) * ((achievements.size() / 5) + 1);
		gv.setLayoutParams(paramsGroup);
		
		// for each achievement, add it to the grid view
		List<BadgeListItem> badgeItems = new ArrayList<BadgeListItem>(achievements.size());
		for (int i = 0; i < achievements.size(); i++) {
			Achievement a = achievements.get(i);
			CharSequence badgeName = a.getBadge().getBadgeName();
			CharSequence userEmail = a.getUser().getUserEmail();
			BadgeListItem b = new BadgeListItem(this.getApplicationContext(), badgeName, userEmail);
			badgeItems.add(b);
		}
		BadgeListAdapter adapter = new BadgeListAdapter(this, badgeItems);
	    
		gv.setAdapter(adapter);
	    gv.setOnItemClickListener(this);
	}



	private void prepareProfileSection(boolean loggedUser) {
		ImageView imageProfile = (ImageView) findViewById(R.id.imageView_userprofile);
		TextView textNick = (TextView) findViewById(R.id.textView_userprofile_nick);
		TextView textPoints = (TextView) findViewById(R.id.textView_userprofile_pointsnumber);
		TextView textFollowers = (TextView) findViewById(R.id.textView_userprofile_counterfollowers);
		TextView textFollowing = (TextView) findViewById(R.id.textView_userprofile_counterfollowing);
		
		if (loggedUser) {
			LocalStorage storage = LocalStorage.getInstance();
			// show user image
			Uri uri = storage.getProfileImage(this);
			imageProfile.setImageURI(uri);
			// show nick
			CharSequence nick = storage.getUserNick(this);
			textNick.setText("@" + nick);
			// show points
			String points = String.valueOf(storage.getUserPoints(this));
			textPoints.setText(points);
			// show followers count
			String followersCount = String.valueOf(storage.getFollowersCount(this));
			textFollowers.setText(followersCount);
			// show following count
			String followingCount = String.valueOf(storage.getFollowingCount(this));
			textFollowing.setText(followingCount);
			
		} else {
			// show user image
			URL url;
        	try {
    			String imagePath = "profiles/" + user.getNick() + ".jpg";
    			url = AssetsUtils.getUrlFromPath(imagePath);
    		} catch (MalformedURLException e) {
    			url = null;
    		}
			ImageLoader il = new ImageLoader(imageProfile);
			il.execute(url);
			// show nick
			CharSequence nick = user.getNick();
			textNick.setText("@" + nick);
			// show points
			String points = String.valueOf(user.getPoints());
			textPoints.setText(points);
			// show followers count
			String followersCount = String.valueOf(this.followersCount);
			textFollowers.setText(followersCount);
			// show following count
			String followingCount = String.valueOf(this.followingCount);
			textFollowing.setText(followingCount);
		}
	}

	
	
	private void preparePersonalInfoSection() {
		User user = LocalStorage.getInstance().getUser(this);
		TextView tv;
		
		// email
		tv = (TextView) findViewById(R.id.textView_userprofile_email);
		tv.setText(user.getUserEmail());
		
		// name
		tv = (TextView) findViewById(R.id.textView_userprofile_name);
		tv.setText(user.getName());
		// surname
		tv = (TextView) findViewById(R.id.textView_userprofile_surname);
		tv.setText(user.getSurname());
		// age
		tv = (TextView) findViewById(R.id.textView_userprofile_age);
		tv.setText(String.valueOf(user.getAge()));
		
		// language
		tv = (TextView) findViewById(R.id.textView_userprofile_language);
		Language language = user.getLanguage();
		tv.setText(language.getLanguageName());
		// city
		tv = (TextView) findViewById(R.id.textView_userprofile_city);
		City city = user.getCity();
		tv.setText(city.getCityName());
		// registration
		tv = (TextView) findViewById(R.id.textView_userprofile_registration);
		String date = Time.convertTimestampToString(user.getRegistration());
		tv.setText(date);
		
		// direction
		tv = (TextView) findViewById(R.id.textView_userprofile_direction);
		if (!user.getDirection().equals("null") && !user.getDirection().equals("")) {
			tv.setText(user.getDirection());
		} else {
			tv.setText("-");
		}
		// phone
		tv = (TextView) findViewById(R.id.textView_userprofile_phone);
		if (!user.getPhone().equals("null") && !user.getPhone().equals("")) {
			tv.setText(user.getPhone());
		} else {
			tv.setText("-");
		}
	}
	
	
	
	private void viewUserRealationship(boolean seeFollowers, CharSequence userEmail) {
		Intent i;
		if (LocalStorage.getInstance().isUserLogged(this)) {
			i = new Intent(this, FollowsListActivity.class);
	        i.putExtra("seeFollowers", seeFollowers);
	        i.putExtra("userEmail", userEmail);
		} else {
			Toast.makeText(this, R.string.toast_registration, Toast.LENGTH_SHORT).show();
    		i = new Intent(this, LoginActivity.class);
	        startActivity(i);
		}
		startActivity(i);
    }
	
	
	
	public void onClickButton(View v) {
		Intent i;
		
		switch (v.getId()) {
		case R.id.button_userprofile_seecomments:
			// See user comments
			i = new Intent(this, ReviewListActivity.class);
			i.putExtra("userEmail", userEmail);
			startActivity(i);
			break;

		case R.id.button_userprofile_seepurchase:
			// See purchase history
			i = new Intent(this, PurchaseListActivity.class);
			startActivity(i);
			break;

		case R.id.button_userprofile_seecart:
			// See user's cart
			super.displayView(4);
			break;
			
		case R.id.textView_userprofile_pointsnumber:
			// see user's points
			super.viewUserPoints();
			break;
			
		case R.id.textView_userprofile_counterfollowing:
			// see user's following
			this.viewUserRealationship(false, userEmail);
			break;
	
		case R.id.textView_userprofile_counterfollowers:
			// see user's followers
			this.viewUserRealationship(true, userEmail);
			break;
	
		case R.id.button_userprofile_seeallbadges:
			// see all badges
			i = new Intent(this, BadgeListActivity.class);
			startActivity(i);
			break;
	
		case R.id.button_userprofile_modifydata:
			// modify personal data
			i = new Intent(this, UpdateUserDataActivity.class);
			startActivity(i);
			break;
	
		case R.id.button_userprofile_changepassword:
			// change password
			i = new Intent(this, PasswordActivity.class);
			startActivity(i);
			break;
			
		default:
			super.onClickButton(v);
			break;
		}
		
    }



	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Achievement achievement = achievements.get(position);
		Badge badge = achievement.getBadge();
		
		Intent i = new Intent(this, BadgeActivity.class);
		i.putExtra("date", achievement.getDate().toString());
		i.putExtra("badgeName", badge.getBadgeName());
		startActivity(i);
	}
	
	
	
}
