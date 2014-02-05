package com.joanflo.tagit;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joanflo.models.Brand;
import com.joanflo.utils.AssetsUtils;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class BrandActivity extends BaseActivity {

	
	private GoogleMap googleMap;
	
	private Brand brand;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_brand);
		
        
        // update selected item and title, then close the drawer
        Bundle bundle = getIntent().getExtras();
        CharSequence brandName = bundle.getCharSequence("brandName");
        brand = new Brand(brandName, "C/ Sant vicenç ferrer 117", "971420779", "hola@hola.com", 34.45, 34.45);
		        
		prepareMapSection();
        prepareBrandInfoSection();
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
	            	// show headquarter
	            	addHeadquarterToMap();
	            }
	        }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}



	private void prepareBrandInfoSection() {
		TextView tv;
		
		tv = (TextView) findViewById(R.id.textView_brand_title);
		tv.setText(brand.getBrandName());

		tv = (TextView) findViewById(R.id.textView_brand_direction);
		tv.setText(brand.getHeadquarter());

		tv = (TextView) findViewById(R.id.textView_brand_phone);
		tv.setText(brand.getPhone());

		tv = (TextView) findViewById(R.id.textView_brand_email);
		tv.setText(brand.getEmail());
		
		ImageView iv = (ImageView) findViewById(R.id.imageView_brand_logo);
		Drawable logo = AssetsUtils.getImageFromAssets(getBaseContext(), AssetsUtils.BRANDS_DIRECTORY, (String) brand.getBrandName());
		iv.setImageDrawable(logo);
		iv.setContentDescription(brand.getBrandName());
	}
	

	
	private void addHeadquarterToMap() {
		// adding marker
		LatLng loc = new LatLng(brand.getLatitude(), brand.getLongitude());
		MarkerOptions marker = new MarkerOptions().position(loc);
		marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
		googleMap.addMarker(marker);
		
		// map display
		CameraPosition cameraPosition = new CameraPosition.Builder().target(loc).zoom(14).build();
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}
	


	@Override
	protected void onResume() {
		super.onResume();
		prepareMapSection();
	}
	
	
	
	public void onClickButton(View v) {
		
		switch (v.getId()) {
		case R.id.imageButton_brand_direction:
			Intent intentMap = new Intent(android.content.Intent.ACTION_VIEW);
			intentMap.setData(Uri.parse("geo:" + brand.getLatitude() + "," + brand.getLongitude() + "?q=" + brand.getHeadquarter()));
			startActivity(intentMap);
			break;
			
		case R.id.imageButton_brand_phone:
			Intent intentPhone = new Intent(Intent.ACTION_DIAL);
			intentPhone.setData(Uri.parse("tel:" + brand.getPhone()));
		    startActivity(intentPhone);
			break;
			
		case R.id.imageButton_brand_email:
			Intent intentEmail = new Intent(Intent.ACTION_SEND);
			intentEmail.setType("text/plain"); // MIME type
			String txt = (String) brand.getEmail();
			intentEmail.putExtra(Intent.EXTRA_EMAIL , new String[]{txt});
			try {
				txt = getResources().getString(R.string.pick_email_client);
				startActivity(Intent.createChooser(intentEmail, txt));
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(BrandActivity.this, R.string.no_email_client, Toast.LENGTH_SHORT).show();
			}
			break;
			
		default:
			super.onClickButton(v);
			break;
		}
    }
	
	
}
