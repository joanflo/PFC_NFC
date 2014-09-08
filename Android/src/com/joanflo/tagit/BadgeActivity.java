package com.joanflo.tagit;

import java.sql.Timestamp;

import org.json.JSONObject;

import com.joanflo.models.Achievement;
import com.joanflo.models.Badge;
import com.joanflo.utils.AssetsUtils;
import com.joanflo.utils.Time;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class BadgeActivity extends BaseActivity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_badge);
		
        
        // get extras
        Bundle bundle = getIntent().getExtras();
        String date = bundle.getString("date");
        CharSequence badgeName = bundle.getCharSequence("badgeName");
        CharSequence badgeDescription = bundle.getCharSequence("badgeDescription");
        int badgeType = bundle.getInt("badgeType");
        Badge badge = new Badge(badgeName, badgeDescription);
        Timestamp timestamp = null;
        if (date != null) {
        	timestamp = Timestamp.valueOf(date);
        }
        Achievement achievement = new Achievement(badge, null, timestamp);
        
        prepareBadgeSection(achievement);
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