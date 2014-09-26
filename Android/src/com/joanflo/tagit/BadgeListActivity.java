package com.joanflo.tagit;

import java.util.ArrayList;
import java.util.List;
import com.joanflo.adapters.BadgeListAdapter;
import com.joanflo.adapters.BadgeListItem;
import com.joanflo.controllers.BadgesController;
import com.joanflo.models.Badge;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Badge list activity
 * @author Joanflo
 */
public class BadgeListActivity extends BaseActivity implements OnItemClickListener {

	
	private List<Badge> badges;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_badgelist);
		super.setTitle(R.string.title_badgelist);
		
        super.showProgressBar(true);
        // call web service
        BadgesController controller = new BadgesController(this);
        controller.getBadges();
	}
	
	
	
	public void badgesReceived(List<Badge> badges) {
		this.badges = badges;
		prepareBadgesSection();
		super.showProgressBar(false);
	}
	
	
	
	private void prepareBadgesSection() {
		GridView gv = (GridView) findViewById(R.id.gridView_badgelist_badges);
		
		// set container height
		int pxColumnWidth = (gv.getWidth() / 5) - 20;
		ViewGroup.LayoutParams paramsGroup = gv.getLayoutParams();
		paramsGroup.height = (pxColumnWidth + 20) * ((badges.size() / 5) + 1);
		gv.setLayoutParams(paramsGroup);
		
		// for each badge, add it to the grid view
		List<BadgeListItem> badgeItems = new ArrayList<BadgeListItem>(badges.size());
		for (int i = 0; i < badges.size(); i++) {
			Badge badge = badges.get(i);
			CharSequence badgeName = badge.getBadgeName();
			CharSequence description = badge.getDescription();
			BadgeListItem b = new BadgeListItem(this.getApplicationContext(), badgeName, description);
			badgeItems.add(b);
		}
		BadgeListAdapter adapter = new BadgeListAdapter(this, badgeItems);
	    
		gv.setAdapter(adapter);
	    gv.setOnItemClickListener(this);
	}



	public void onClickButton(View v) {
		super.onClickButton(v);
    }



	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Intent i = new Intent(this, BadgeActivity.class);
		Badge badge = badges.get(position);
		i.putExtra("badgeName", badge.getBadgeName());
		i.putExtra("badgeDescription", badge.getDescription());
		startActivity(i);
	}
	
	
}