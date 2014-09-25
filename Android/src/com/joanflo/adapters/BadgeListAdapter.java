package com.joanflo.adapters;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class BadgeListAdapter extends BaseAdapter {

	
	private Context context;
    private List<BadgeListItem> thumbs;
    
    
    
    public BadgeListAdapter(Context context, List<BadgeListItem> thumbs){
        this.context = context;
        this.thumbs = thumbs;
    }
	
	
	
	@Override
	public int getCount() {
		return thumbs.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// get view
		ImageView iv = new ImageView(context);
		
		// set view info
		BadgeListItem badge = thumbs.get(position);
        iv.setContentDescription(badge.getDescription());
		iv.setImageDrawable(badge.getThumb());
		
		// set view settings
		iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
		int pxColumnWidth = (parent.getWidth() / 5) - 20;
		LayoutParams params = new GridView.LayoutParams(LayoutParams.MATCH_PARENT, pxColumnWidth);
		iv.setLayoutParams(params);
		iv.setPadding(2, 2, 2, 2);
		
		return iv;
	}

}