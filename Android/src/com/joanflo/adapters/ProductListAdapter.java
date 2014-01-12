package com.joanflo.adapters;

import java.util.List;

import com.joanflo.network.ImageLoader;
import com.joanflo.tagit.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductListAdapter extends BaseAdapter {

	private Context context;
	private List<ProductListItem> productItems;
	
	
	public ProductListAdapter(Context context, List<ProductListItem> productItems) {
		this.context = context;
		this.productItems = productItems;
	}

	

	@Override
	public int getCount() {
		return productItems.size();
	}


	@Override
	public Object getItem(int position) {
		return productItems.get(position);
	}


	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_product, null);
        }
		ProductListItem productItem = productItems.get(position);
		
		ImageView iv = (ImageView) convertView.findViewById(R.id.imageView_product_thumb);
		iv.setContentDescription(productItem.getDescription());
		ImageLoader il = new ImageLoader(iv);
		il.execute(productItem.getUrl());
		
		TextView tv;
		tv = (TextView) convertView.findViewById(R.id.textView_product_productname);
		tv.setText(productItem.getProductName());
		
		tv = (TextView) convertView.findViewById(R.id.textView_product_brandname);
		tv.setText(productItem.getBrandName());

		tv = (TextView) convertView.findViewById(R.id.textView_product_categoryname);
		tv.setText(productItem.getCategoryName());
		
		tv = (TextView) convertView.findViewById(R.id.textView_product_price);
		tv.setText(productItem.getPrice());

		tv = (TextView) convertView.findViewById(R.id.textView_product_pricetext);
		tv.setText(productItem.getCoin());
		
		tv = (TextView) convertView.findViewById(R.id.textView_product_rating);
		tv.setText(productItem.getRating());
		
		return convertView;
	}
	
	
}