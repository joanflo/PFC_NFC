package com.joanflo.tagit;

import java.util.ArrayList;
import com.joanflo.adapters.BadgeListAdapter;
import com.joanflo.adapters.BadgeListItem;
import com.joanflo.models.Achievement;
import com.joanflo.models.Badge;
import com.joanflo.models.City;
import com.joanflo.models.Country;
import com.joanflo.models.Language;
import com.joanflo.models.Region;
import com.joanflo.models.User;
import com.joanflo.utils.Gamification;
import com.joanflo.utils.Time;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class UserProfileActivity extends BaseActivity implements OnItemClickListener {
	
	
	private ArrayList<Achievement> userAchievements;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_userprofile);
		
        
        // update selected item and title, then close the drawer
        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("drawerPosition");
        super.updateSelected(pos);
        
		prepareProfileSection();
        prepareBadgesSection();
        preparePersonalInfoSection();
	}

	
	
	private void preparePersonalInfoSection() {

		// show user image
		ImageView iv = (ImageView) findViewById(R.id.imageView_userprofile);
		//iv.setImageResource(R.drawable.user_profile);
		
		// set user nick
		TextView tv = (TextView) findViewById(R.id.textView_userprofile_nick);
		tv.setText("@" + "Joan_flo");
		
		// set user points
		tv = (TextView) findViewById(R.id.textView_userprofile_pointsnumber);
		tv.setText("326");
		
		// set user followers
		tv = (TextView) findViewById(R.id.textView_userprofile_counterfollowers);
		tv.setText("106");
		
		// set user following
		tv = (TextView) findViewById(R.id.textView_userprofile_counterfollowing);
		tv.setText("256");
	}

	
	
	private void prepareBadgesSection() {
		GridView gv = (GridView) findViewById(R.id.gridView_userprofile_badges);
		/*
		ArrayList<BadgeListItem> thumbs = loadBadgeResources();
		BadgeListAdapter adapter = new BadgeListAdapter(this.getApplicationContext(), thumbs);
	    
		gv.setAdapter(adapter);
	    gv.setOnItemClickListener(this);*/
	}



	private void prepareProfileSection() {
		Country espanya = new Country("Espanya", null, 34, Country.EURO);
		Region balears = new Region("Balears", null, espanya);
		City palma = new City("Palma", null, balears);
		Language catala = new Language("Català");
		User user = new User("joan@uib.cat", palma, catala, "Joan_flo", "Joan", "Florit Gomila", 23, "password", "686922414", "Sant Vicenç Ferrer 117");
		
		TextView tv;
		
		tv = (TextView) findViewById(R.id.textView_userprofile_email);
		tv.setText(user.getUserEmail());
		
		tv = (TextView) findViewById(R.id.textView_userprofile_name);
		tv.setText(user.getName());
		
		tv = (TextView) findViewById(R.id.textView_userprofile_surname);
		tv.setText(user.getSurname());
		
		tv = (TextView) findViewById(R.id.textView_userprofile_city);
		City city = user.getCity();
		tv.setText(city.getCityName());
		
		tv = (TextView) findViewById(R.id.textView_userprofile_region);
		Region region = city.getRegion();
		tv.setText(region.getRegionName());
		
		tv = (TextView) findViewById(R.id.textView_userprofile_country);
		Country country = region.getCountry();
		tv.setText(country.getCountryName());
		
		tv = (TextView) findViewById(R.id.textView_userprofile_language);
		tv.setText(user.getLanguage().getLanguageName());
		
		tv = (TextView) findViewById(R.id.textView_userprofile_registration);
		String date = Time.convertTimestampToString(user.getRegistration());
		tv.setText(date);
		
		tv = (TextView) findViewById(R.id.textView_userprofile_age);
		tv.setText(String.valueOf(user.getAge()));
		
		tv = (TextView) findViewById(R.id.textView_userprofile_direction);
		tv.setText(user.getDirection());
		
		tv = (TextView) findViewById(R.id.textView_userprofile_phone);
		tv.setText(user.getPhone());
	}
	
	
	
	public void onClickButton(View v) {
		Intent i;
		
		switch (v.getId()) {
		case R.id.button_userprofile_seecomments:
			// See user comments
			i = new Intent(this, ReviewListActivity.class);
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
			super.viewUserRealationship(false);
			break;
	
		case R.id.textView_userprofile_counterfollowers:
			// see user's followers
			super.viewUserRealationship(true);
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
		Achievement achievement = userAchievements.get(position);
		Badge badge = achievement.getBadge();
		
		Intent i = new Intent(this, BadgeActivity.class);
		i.putExtra("date", achievement.getDate().toString());
		i.putExtra("badgeName", badge.getBadgeName());
		i.putExtra("badgeDescription", badge.getDescription());
		startActivity(i);
	}
	
	
	
}
