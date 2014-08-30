package com.joanflo.tagit;

import java.util.ArrayList;

import org.json.JSONObject;

import com.joanflo.adapters.BadgeListAdapter;
import com.joanflo.adapters.BadgeListItem;
import com.joanflo.models.Achievement;
import com.joanflo.models.Badge;
import com.joanflo.utils.Gamification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;


public class BadgeListActivity extends BaseActivity implements OnItemClickListener {

	
	private ArrayList<Achievement> userAchievements;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_badgelist);
		
        
        prepareBadgesSection();
	}
	
	
	
	private void prepareBadgesSection() {
		GridView gv = (GridView) findViewById(R.id.gridView_badgelist_badges);
		
		ArrayList<BadgeListItem> thumbs = loadBadgeResources();
		BadgeListAdapter adapter = new BadgeListAdapter(this.getApplicationContext(), thumbs);
	    
		gv.setAdapter(adapter);
	    gv.setOnItemClickListener(this);
	}
	
	
	
	private ArrayList<BadgeListItem> loadBadgeResources() {

		Badge b1 = Gamification.getBadge(Gamification.BADGE_1FOLLOWER);
		Badge b2 = Gamification.getBadge(Gamification.BADGE_1FOLLOWING);
		Badge b3 = Gamification.getBadge(Gamification.BADGE_1PURCHASE);
		Badge b4 = Gamification.getBadge(Gamification.BADGE_1REVIEW);
		Badge b5 = Gamification.getBadge(Gamification.BADGE_1SHARE);
		Badge b6 = Gamification.getBadge(Gamification.BADGE_1YEAR);
		Badge b7 = Gamification.getBadge(Gamification.BADGE_5DAYS);
		Badge b8 = Gamification.getBadge(Gamification.BADGE_10PURCHASES);
		Badge b9 = Gamification.getBadge(Gamification.BADGE_10REVIEWS);
		Badge b10 = Gamification.getBadge(Gamification.BADGE_10SHARES);
		Badge b11 = Gamification.getBadge(Gamification.BADGE_100FOLLOWERS);
		Badge b12 = Gamification.getBadge(Gamification.BADGE_NEWBIE);
		Badge b13 = Gamification.getBadge(Gamification.BADGE_NFC);
		
		Achievement a1 = new Achievement(b1, null, null);
		Achievement a2 = new Achievement(b2, null, null);
		Achievement a3 = new Achievement(b3, null, null);
		Achievement a4 = new Achievement(b4, null, null);
		Achievement a5 = new Achievement(b5, null, null);
		Achievement a6 = new Achievement(b6, null, null);
		Achievement a7 = new Achievement(b7, null, null);
		Achievement a8 = new Achievement(b8, null, null);
		Achievement a9 = new Achievement(b9, null, null);
		Achievement a10 = new Achievement(b10, null, null);
		Achievement a11 = new Achievement(b11, null, null);
		Achievement a12 = new Achievement(b12, null, null);
		Achievement a13 = new Achievement(b13, null, null);
		
		userAchievements = new ArrayList<Achievement>();
		userAchievements.add(a1);
		userAchievements.add(a2);
		userAchievements.add(a3);
		userAchievements.add(a4);
		userAchievements.add(a5);
		userAchievements.add(a6);
		userAchievements.add(a7);
		userAchievements.add(a8);
		userAchievements.add(a9);
		userAchievements.add(a10);
		userAchievements.add(a11);
		userAchievements.add(a12);
		userAchievements.add(a13);
		
		ArrayList<BadgeListItem> badgeItems = new ArrayList<BadgeListItem>();
		for (int i = 0; i < userAchievements.size(); i++) {
			Achievement a = userAchievements.get(i);
			BadgeListItem b = new BadgeListItem(this.getApplicationContext(), a.getBadge().getType(), a.getBadge().getDescription());
			badgeItems.add(b);
		}
		
		return badgeItems;
	}



	public void onClickButton(View v) {
		super.onClickButton(v);
    }



	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Achievement achievement = userAchievements.get(position);
		Badge badge = achievement.getBadge();
		
		Intent i = new Intent(this, BadgeActivity.class);
		if (achievement.getDate() != null) {
			i.putExtra("date", achievement.getDate().toString());
		}
		i.putExtra("badgeName", badge.getBadgeName());
		i.putExtra("badgeDescription", badge.getDescription());
		i.putExtra("badgeType", badge.getType());
		startActivity(i);
	}
	
	
}