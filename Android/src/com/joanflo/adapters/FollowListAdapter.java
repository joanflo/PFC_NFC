package com.joanflo.adapters;

import java.util.List;

import com.joanflo.network.ImageLoader;
import com.joanflo.tagit.FollowsListActivity;
import com.joanflo.tagit.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Follow list adapter class
 * @author Joanflo
 * @see FollowListItem
 */
public class FollowListAdapter extends BaseAdapter {


	private Context context;
	private List<FollowListItem> followItems;
	private FollowsListActivity listener;
	
	
	
	/**
	 * Follow list adapter constructor
	 * @param context
	 * @param followItems
	 * @param listener
	 */
	public FollowListAdapter(Context context, List<FollowListItem> followItems, FollowsListActivity listener) {
		this.context = context;
		this.followItems = followItems;
		this.listener = listener;
	}

	

	@Override
	public int getCount() {
		return followItems.size();
	}


	@Override
	public Object getItem(int position) {
		return followItems.get(position);
	}


	@Override
	public long getItemId(int position) {
		return position;
	}


	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_follow, null);
        }
		FollowListItem followItem = followItems.get(position);
		
		ImageView iv = (ImageView) convertView.findViewById(R.id.imageView_follow_thumb);
		ImageLoader il = new ImageLoader(iv);
		il.execute(followItem.getUrl());
		
		TextView tv;
		tv = (TextView) convertView.findViewById(R.id.textView_follow_name);
		tv.setText(followItem.getName());
		
		tv = (TextView) convertView.findViewById(R.id.textView_follow_nick);
		tv.setText(followItem.getNick());
		
		ImageButton ib = (ImageButton) convertView.findViewById(R.id.button_follow_follow);
		ib.setTag(position);
		// same user
		if (followItem.isSameUser()) {
			ib.setVisibility(View.GONE);
		} else {
			ib.setOnClickListener(listener);
			if (followItem.isFollowed()) { // row user is being followed
				ib.setImageResource(R.drawable.ic_following);
			} else {
				ib.setImageResource(R.drawable.ic_notfollowing);
			}
	        ib.setFocusable(false);
		}
		
		return convertView;
	}
	
	
}