package com.joanflo.adapters;

import java.util.ArrayList;

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
 * Spinner adapter class
 * @author Joanflo
 * @see SpinnerNavItem
 */
public class SpinnerNavAdapter extends BaseAdapter {
	
	private Context context;
    private ArrayList<SpinnerNavItem> spinnerNavItems;
    
    
    
    /**
     * Spinner adapter constructor
     * @param context
     * @param spinnerNavItems
     */
	public SpinnerNavAdapter(Context context, ArrayList<SpinnerNavItem> spinnerNavItems) {
		this.context = context;
		this.spinnerNavItems = spinnerNavItems;
	}

	

	@Override
	public int getCount() {
		return spinnerNavItems.size();
	}


	@Override
	public Object getItem(int position) {
		return spinnerNavItems.get(position);
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
            convertView = mInflater.inflate(R.layout.list_item_navspinner, null);
        }
		SpinnerNavItem navItem = spinnerNavItems.get(position);
		
		// set icon
		ImageView icon = (ImageView) convertView.findViewById(R.id.imageView_icon);
		icon.setImageResource(navItem.getIcon());
		icon.setContentDescription(navItem.getTitle());
		
		// set title
		TextView title = (TextView) convertView.findViewById(R.id.textView_title);
		title.setText(navItem.getTitle());
		
		return convertView;
	}
    
	 
   @Override
   public View getDropDownView(int position, View convertView, ViewGroup parent) {
	   return getView(position, convertView, parent);
   }
	
	
}