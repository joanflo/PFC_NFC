package com.joanflo.adapters;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.joanflo.tagit.R;


public class PurchaseListAdapter extends BaseAdapter {

	private Context context;
	private List<PurchaseListItem> purchaseItems;
		
	
	public PurchaseListAdapter(Context context, List<PurchaseListItem> purchaseItems) {
		this.context = context;
		this.purchaseItems = purchaseItems;
	}


	
	@Override
	public int getCount() {
		return purchaseItems.size();
	}


	@Override
	public Object getItem(int position) {
		return purchaseItems.get(position);
	}


	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_purchase, null);
        }
		PurchaseListItem purchaseItem = purchaseItems.get(position);
		
		TextView tv;
		tv = (TextView) convertView.findViewById(R.id.textView_totalprice);
		tv.setText(purchaseItem.getTotalPrice());

		tv = (TextView) convertView.findViewById(R.id.textView_totalprice_coin);
		tv.setText(String.valueOf(purchaseItem.getCoin()));
		
		tv = (TextView) convertView.findViewById(R.id.textView_itemsnumber);
		tv.setText(purchaseItem.getTotalItems());

		tv = (TextView) convertView.findViewById(R.id.textView_purchaseinfo);
		Resources r = context.getResources();
		CharSequence info = r.getText(R.string.shop1) + " " + purchaseItem.getShopDirection()
				   + "\n" + r.getText(R.string.shop2) + " " + purchaseItem.getDate();
		tv.setText(info);
		
		return convertView;
	}


}