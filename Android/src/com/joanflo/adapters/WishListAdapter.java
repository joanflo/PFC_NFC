package com.joanflo.adapters;

import java.util.List;

import com.joanflo.network.ImageLoader;
import com.joanflo.tagit.R;
import com.joanflo.tagit.WishListActivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class WishListAdapter extends BaseAdapter {

	private Context context;
	private List<WishListItem> wishItems;
	private WishListActivity listener;
		
	
	public WishListAdapter(WishListActivity listener, Context context, List<WishListItem> wishItems) {
		this.listener = listener;
		this.context = context;
		this.wishItems = wishItems;
	}


	
	@Override
	public int getCount() {
		return wishItems.size();
	}


	@Override
	public Object getItem(int position) {
		return wishItems.get(position);
	}


	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_wish, null);
        }
		WishListItem wishItem = wishItems.get(position);
		
		ImageView iv = (ImageView) convertView.findViewById(R.id.imageView_wishlist_thumb);
		ImageLoader il = new ImageLoader(iv);
		il.execute(wishItem.getUrl());
		iv.setContentDescription(wishItem.getDescription());
		
		TextView tv;
		tv = (TextView) convertView.findViewById(R.id.textView_wishlist_date);
		tv.setText(wishItem.getDate());

		tv = (TextView) convertView.findViewById(R.id.textView_wishlist_productname);
		tv.setText(wishItem.getDate());
		
		ImageButton ib;
		ib = (ImageButton) convertView.findViewById(R.id.button_wishlist_remove);
		ib.setTag(position);
		ib.setOnClickListener(listener);
        ib.setFocusable(false);
		
		ib = (ImageButton) convertView.findViewById(R.id.button_wishlist_addtocart);
		ib.setTag(position);
		ib.setOnClickListener(listener);
        ib.setFocusable(false);
		
		return convertView;
	}


}