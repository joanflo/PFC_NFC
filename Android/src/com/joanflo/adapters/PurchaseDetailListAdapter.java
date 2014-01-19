package com.joanflo.adapters;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.joanflo.network.ImageLoader;
import com.joanflo.tagit.PurchaseDetailListActivity;
import com.joanflo.tagit.R;


public class PurchaseDetailListAdapter extends BaseAdapter {

	private Context context;
	private List<PurchaseDetailListItem> purchaseDetailItems;
	private PurchaseDetailListActivity listener;
	private boolean viewingCart;
	
		
	
	public PurchaseDetailListAdapter(PurchaseDetailListActivity listener, Context context, List<PurchaseDetailListItem> purchaseDetailItems, boolean viewingCart) {
		this.context = context;
		this.purchaseDetailItems = purchaseDetailItems;
		this.listener = listener;
		this.viewingCart = viewingCart;
	}


	
	@Override
	public int getCount() {
		return purchaseDetailItems.size();
	}


	@Override
	public Object getItem(int position) {
		return purchaseDetailItems.get(position);
	}


	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_purchasedetail, null);
        }
		PurchaseDetailListItem purchaseDetailItem = purchaseDetailItems.get(position);
		
		ImageView iv = (ImageView) convertView.findViewById(R.id.imageView_productpurchase_thumb);
		iv.setContentDescription(purchaseDetailItem.getDescription());
		ImageLoader il = new ImageLoader(iv);
		il.execute(purchaseDetailItem.getUrl());
		
		TextView tv;
		tv = (TextView) convertView.findViewById(R.id.textView_purchase_productname);
		tv.setText(purchaseDetailItem.getProductName());
		
		tv = (TextView) convertView.findViewById(R.id.textView_purchase_price);
		tv.setText(purchaseDetailItem.getTotalPrice());

		tv = (TextView) convertView.findViewById(R.id.textView_purchase_pricetext);
		tv.setText(purchaseDetailItem.getCoin());
		
		tv = (TextView) convertView.findViewById(R.id.textView_purchase_units);
		tv.setText(String.valueOf(purchaseDetailItem.getUnits()));
		
		if (viewingCart) {
			tv = (TextView) convertView.findViewById(R.id.textView_purchase_size);
			tv.setVisibility(View.GONE);
			
			tv = (TextView) convertView.findViewById(R.id.textView_purchase_colorcode);
			tv.setVisibility(View.GONE);
			
			tv = (TextView) convertView.findViewById(R.id.textView_purchase_colorname);
			tv.setVisibility(View.GONE);
			
			ImageButton ib = (ImageButton) convertView.findViewById(R.id.button_purchase_remove);
			ib.setTag(position);
			ib.setOnClickListener(listener);
	        ib.setFocusable(false);
			
		} else {
			tv = (TextView) convertView.findViewById(R.id.textView_purchase_size);
			tv.setText(context.getResources().getString(R.string.product_size2) + " " + String.valueOf(purchaseDetailItem.getSize()));
			
			tv = (TextView) convertView.findViewById(R.id.textView_purchase_colorcode);
			int c = android.graphics.Color.parseColor((String) purchaseDetailItem.getColorCode());
			tv.setBackgroundColor(c);
			
			tv = (TextView) convertView.findViewById(R.id.textView_purchase_colorname);
			tv.setText(purchaseDetailItem.getColorName());
			
			ImageButton ib = (ImageButton) convertView.findViewById(R.id.button_purchase_remove);
			ib.setVisibility(View.GONE);
		}
		
		return convertView;
	}


}