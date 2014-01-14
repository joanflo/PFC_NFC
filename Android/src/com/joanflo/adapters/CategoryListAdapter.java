package com.joanflo.adapters;

import java.util.List;

import com.joanflo.tagit.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryListAdapter extends BaseAdapter {

	private Context context;
	private List<CategoryListItem> categoryItems;
		
	
	public CategoryListAdapter(Context context, List<CategoryListItem> categoryItems) {
		this.context = context;
		this.categoryItems = categoryItems;
	}
	
	

	@Override
	public int getCount() {
		return categoryItems.size();
	}

	
	@Override
	public Object getItem(int position) {
		return categoryItems.get(position);
	}

	
	@Override
	public long getItemId(int position) {
		return position;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_category, null);
        }
		CategoryListItem categoryItem = categoryItems.get(position);
		
		ImageView iv = (ImageView) convertView.findViewById(R.id.imageView_category_thumb);
		iv.setImageDrawable(categoryItem.getThumb());
		iv.setContentDescription(categoryItem.getName());
		
		TextView tv;
		tv = (TextView) convertView.findViewById(R.id.textView_category_name);
		tv.setText(categoryItem.getName());
		
		tv = (TextView) convertView.findViewById(R.id.textView_category_itemsNumber);
		tv.setText(categoryItem.getItemsNumber());
		
		return convertView;
	}
	

}