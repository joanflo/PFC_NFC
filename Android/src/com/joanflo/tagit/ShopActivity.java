package com.joanflo.tagit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joanflo.models.Shop;


public class ShopActivity extends BaseActivity {

	
	private GoogleMap googleMap;
	
	private Shop shop;
	private boolean shopSelected;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// update the main content by replacing view
		LayoutInflater factory = LayoutInflater.from(this);
		View activityView = factory.inflate(R.layout.activity_shop, null);
		// inflate activity layout
        FrameLayout viewContainer = (FrameLayout) findViewById(R.id.frame_container);
        viewContainer.addView(activityView);
        
        // update selected item and title, then close the drawer
        Bundle bundle = getIntent().getExtras();
        int idShop = bundle.getInt("idShop");
        String cityName = bundle.getString("cityName");
        String direction = bundle.getString("direction");
        String schedule = bundle.getString("schedule");
        String phone = bundle.getString("phone");
        String email = bundle.getString("email");
        double latitude = bundle.getDouble("latitude");
        double longitude = bundle.getDouble("longitude");
        shop = new Shop(idShop, cityName, direction, schedule, phone, email, latitude, longitude);
		        
		prepareMapSection();
		
		shopSelected = bundle.getBoolean("isSelected");
        prepareShopInfoSection();
	}
	


	private void prepareMapSection() {
		try {
            // Loading map
			if (googleMap == null) {
				MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.shop_map);
				googleMap = mapFragment.getMap();
				
	            if (googleMap == null) {
	            	Log.i("DEBUG", "map is not created successfully");
	            } else {
	            	// show shop
	            	addShopToMap();
	            }
	        }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	
	private void addShopToMap() {
		// adding marker
		LatLng loc = new LatLng(shop.getLatitude(), shop.getLongitude());
		MarkerOptions marker = new MarkerOptions().position(loc);
		marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
		googleMap.addMarker(marker);
		
		// map display
		googleMap.setMyLocationEnabled(true);
		googleMap.getUiSettings().setMyLocationButtonEnabled(true);
		CameraPosition cameraPosition = new CameraPosition.Builder().target(loc).zoom(14).build();
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}
	

	
	private void prepareShopInfoSection() {
		updateShopState();
		
		TextView tv;
		
		tv = (TextView) findViewById(R.id.textView_shop_direction);
		tv.setText(shop.getDirection());
		
		tv = (TextView) findViewById(R.id.textView_shop_city);
		tv.setText(shop.getCityName());
		
		tv = (TextView) findViewById(R.id.textView_shop_phone);
		tv.setText(shop.getPhone());
		
		tv = (TextView) findViewById(R.id.textView_shop_email);
		tv.setText(shop.getEmail());
		
		tv = (TextView) findViewById(R.id.textView_shop_schedule);
		tv.setText(shop.getSchedule());
	}
	


	@Override
	protected void onResume() {
		super.onResume();
		prepareMapSection();
	}
	
	
	
	public void onClickButton(View v) {
		
		switch (v.getId()) {
		case R.id.button_shop_pickshop:
			updateShopState();
			break;
			
		case R.id.imageButton_shop_direction:
			shop.getDirection();
			break;
			
		case R.id.imageButton_shop_phone:
			shop.getPhone();
			break;
			
		case R.id.imageButton_shop_email:
			shop.getPhone();
			break;
		}
	}
	
	
	
	private void updateShopState() {
		TextView tv = (TextView) findViewById(R.id.title_shop_shopselected);
		Button b = (Button) findViewById(R.id.button_shop_pickshop);
		
		if (shopSelected) {
			tv.setText(R.string.currentshop_text);
			b.setText(R.string.button_unselectshop);
			
		} else {
			tv.setText(R.string.shopinfo_text);
			b.setText(R.string.button_pickshop);
		}
		
		shopSelected = !shopSelected;
	}
	
	
	
}
