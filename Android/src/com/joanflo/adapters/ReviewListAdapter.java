package com.joanflo.adapters;

import java.util.List;
import com.joanflo.network.ImageLoader;
import com.joanflo.tagit.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Review list adapter class
 * @author Joanflo
 * @see ReviewListItem
 */
public class ReviewListAdapter extends BaseAdapter {

	
	private Context context;
    private List<ReviewListItem> reviewItems;
    
    
    
    /**
     * Review list adapter constructor
     * @param context
     * @param reviewItems
     */
    public ReviewListAdapter(Context context, List<ReviewListItem> reviewItems){
        this.context = context;
        this.reviewItems = reviewItems;
    }
	
	
	
	@Override
	public int getCount() {
		return reviewItems.size();
	}
	

	@Override
	public Object getItem(int position) {
		return reviewItems.get(position);
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
            convertView = mInflater.inflate(R.layout.list_item_review, null);
        }
		ReviewListItem reviewItem = reviewItems.get(position);
		
		ImageView iv = (ImageView) convertView.findViewById(R.id.imageView_review_thumb);
		ImageLoader il = new ImageLoader(iv);
		il.execute(reviewItem.getUrl());
		iv.setContentDescription(reviewItem.getDescription());
		
		TextView tv;
		tv = (TextView) convertView.findViewById(R.id.textView_date);
		tv.setText(reviewItem.getDate());
		
		tv = (TextView) convertView.findViewById(R.id.textView_rating);
		tv.setText(reviewItem.getRating());
		
		tv = (TextView) convertView.findViewById(R.id.textView_comment);
		tv.setText(reviewItem.getComment());
		
		return convertView;
	}
	

}