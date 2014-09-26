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
import android.widget.TextView;

/**
 * Shop list adapter class
 * @author Joanflo
 * @see ShopListItem
 */
public class ShopListAdapter extends BaseAdapter {

	private Context context;
    private ArrayList<ShopListItem> shopItems;
    
    
    
    /**
     * Shop list adapter constructor
     * @param context
     * @param shopItems
     */
    public ShopListAdapter(Context context, ArrayList<ShopListItem> shopItems){
        this.context = context;
        this.shopItems = shopItems;
    }
	
	
	@Override
	public int getCount() {
		return shopItems.size();
	}

	@Override
	public Object getItem(int position) {
		return shopItems.get(position);
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
            convertView = mInflater.inflate(R.layout.list_item_shop, null);
        }
		
		// get views
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtDistance = (TextView) convertView.findViewById(R.id.distance);
		
        // set views info    
        txtTitle.setText(shopItems.get(position).getTitle());
        String distance = String.valueOf(shopItems.get(position).getDistance());
        txtDistance.setText(distance);
        
		return convertView;
	}

}
