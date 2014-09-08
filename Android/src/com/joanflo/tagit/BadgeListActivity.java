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
		/*
		ArrayList<BadgeListItem> thumbs = loadBadgeResources();
		BadgeListAdapter adapter = new BadgeListAdapter(this.getApplicationContext(), thumbs);
	    
		gv.setAdapter(adapter);
	    gv.setOnItemClickListener(this);*/
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
		startActivity(i);
	}
	
	
}