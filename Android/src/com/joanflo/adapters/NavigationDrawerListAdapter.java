package com.joanflo.adapters;

import java.util.ArrayList;

import com.joanflo.tagit.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationDrawerListAdapter extends BaseAdapter {

	private Context context;
    private ArrayList<NavigationDrawerListItem> navDrawerItems;
    
    public NavigationDrawerListAdapter(Context context, ArrayList<NavigationDrawerListItem> navDrawerItems){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }
	
	
	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_drawer, null);
        }
		
		// get views
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
		
        // set views info
        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());        
        txtTitle.setText(navDrawerItems.get(position).getTitle());
        if(navDrawerItems.get(position).getCounterVisibility()){ // counter view
        	// check whether it set visible or not
            txtCount.setText(navDrawerItems.get(position).getCount());
        }else{
            // hide the counter view
            txtCount.setVisibility(View.GONE);
        }
        
		return convertView;
	}

}
