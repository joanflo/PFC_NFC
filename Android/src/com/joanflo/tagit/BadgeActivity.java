package com.joanflo.tagit;

import java.sql.Timestamp;
import com.joanflo.controllers.BadgesController;
import com.joanflo.models.Achievement;
import com.joanflo.models.Badge;
import com.joanflo.utils.AssetsUtils;
import com.joanflo.utils.Time;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Badge activity
 * @author Joanflo
 */
public class BadgeActivity extends BaseActivity {

	
	private String date;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_badge);
		super.setTitle(R.string.title_badge);
		
        
        // get extras
        Bundle bundle = getIntent().getExtras();
        date = bundle.getString("date", null);
        CharSequence badgeName = bundle.getCharSequence("badgeName");
        CharSequence badgeDescription = bundle.getCharSequence("badgeDescription", null);
        
        if (badgeDescription != null) {
	        // we come from badges list
	        Badge badge = new Badge(badgeName, badgeDescription);
	        Achievement achievement = new Achievement(badge, null, null);
	        prepareBadgeSection(achievement);
	        
        } else {
        	// we come from user profile
        	super.showProgressBar(true);
        	// call web service
        	BadgesController controller = new BadgesController(this);
        	controller.getBadge(badgeName);
        }
	}
	
	
	
	public void badgeReceived(Badge badge) {
		Timestamp timestamp = Timestamp.valueOf(date);
        Achievement achievement = new Achievement(badge, null, timestamp);
        prepareBadgeSection(achievement);
    	super.showProgressBar(false);
	}
	
	
	
	private void prepareBadgeSection(Achievement achievement) {
		Badge badge = achievement.getBadge();
		TextView tv;
		
		tv = (TextView) findViewById(R.id.textView_badge_name);
		tv.setText(badge.getBadgeName());
		
		tv = (TextView) findViewById(R.id.textView_badge_description);
		tv.setText(badge.getDescription());
		
		if (achievement.getDate() == null) {
			tv = (TextView) findViewById(R.id.textView_badge_wontext);
			tv.setVisibility(View.GONE);
		} else {
			String dateTxt = Time.convertTimestampToString(achievement.getDate());
			tv = (TextView) findViewById(R.id.textView_badge_date);
			tv.setText(dateTxt);
		}
		
		ImageView iv = (ImageView) findViewById(R.id.imageView_badge_thumb);
		Drawable d = AssetsUtils.getImageFromAssets(this, AssetsUtils.BADGES_DIRECTORY, String.valueOf(badge.getBadgeName()));
		iv.setImageDrawable(d);
		iv.setContentDescription(badge.getDescription());
	}
	
	
	
	public void onClickButton(View v) {
		super.onClickButton(v);
    }

}