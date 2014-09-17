package com.joanflo.tagit;

import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joanflo.adapters.ShopListAdapter;
import com.joanflo.adapters.ShopListItem;
import com.joanflo.controllers.CountriesController;
import com.joanflo.controllers.ShopsController;
import com.joanflo.models.City;
import com.joanflo.models.Country;
import com.joanflo.models.Region;
import com.joanflo.models.Shop;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.LocationUtils;
import com.joanflo.utils.LocationUtils.LocationResult;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


public class ShopSelectionActivity extends BaseActivity implements OnItemSelectedListener {

	
	private Activity currentActivity = this;
	
	private String[] cityNames;
	private String[] regionNames;
	private String[] countryNames;
	
	private GoogleMap googleMap;
	private Location currentLocation;
	
	private List<Shop> shops;
	
	private ArrayList<ShopListItem> shopItems;
	private ShopListAdapter adapter;
    private ListView shopList;
    
    private View itemSelected;
    private int itemSelectedPosition;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_shopselection);
		
        
        // update selected item and title, then close the drawer
        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("drawerPosition");
        super.updateSelected(pos);
        
		// call web service
		CountriesController controller = new CountriesController(this);
		controller.getCountries();
        
		prepareMapSection();
        preparePlaceSection();
	}
	
	
	
	private void prepareMapSection() {
		try {
            // Loading map
			if (googleMap == null) {
				MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.shopselection_map);
				googleMap = mapFragment.getMap();
				
	            if (googleMap == null) {
	            	Log.i("DEBUG", "map is not created successfully");
	            	
	            } else {
	        		// location settings
	        		googleMap.setMyLocationEnabled(true);
	        		googleMap.getUiSettings().setMyLocationButtonEnabled(true);

	        		// get nearest shops from current location
	        		LocationResult locationResult = new LocationResult(){
	        		    @Override
	        		    public void gotLocation(Location location){
	        		    	// get coordinates
	        		    	currentLocation = location;
	    	        		double latitude = location.getLatitude();
	    	        		double longitude = location.getLongitude();
	    	        		
	    	        		// move camera to coordinates
	    	    			LatLng loc = new LatLng(latitude, longitude);
	    	    			CameraPosition cameraPosition = new CameraPosition.Builder().target(loc).zoom(13).build();
	    	    			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	    	    			
	    	    			// call web service
	    	        		ShopsController controller = new ShopsController(currentActivity);
	    	        		controller.getShops(latitude, longitude);
	        		    }
	        		};
	        		Toast.makeText(this, R.string.toast_gps, Toast.LENGTH_SHORT).show();
	        		LocationUtils location = new LocationUtils();
	        		location.getLocation(this, locationResult);
	        		
	    			prepareShopListSection(false);
	            }
	        }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	
	public void shopsReceived(List<Shop> shops) {
		this.shops = shops;
		
		if (shops.size() == 0) {
			showProgressBar(false);
			Toast.makeText(this, R.string.toast_problem_shop, Toast.LENGTH_LONG).show();
		
		} else {
			// add shops to map
			for (Shop shop : shops) {
				addShopToMap(shop.getLatitude(), shop.getLongitude());
			}
			
			// go to nearest shop
			LatLng loc = new LatLng(shops.get(0).getLatitude(), shops.get(0).getLongitude());
			CameraPosition cameraPosition = new CameraPosition.Builder().target(loc).zoom(13).build();
			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			
			// populate list
			prepareShopListSection(true);
		}
	}
	
	
	
	private void addShopToMap(double latitude, double longitude) {
		// create marker
		MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude));
		marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
		
		// adding marker
		googleMap.addMarker(marker);
	}
	
	
	
	private void preparePlaceSection() {
		// default values
		String[] defaultValue = { getResources().getString(R.string.list_default) };
		fillSpinner(R.id.spinner_shopselection_city, defaultValue);
		fillSpinner(R.id.spinner_shopselection_region, defaultValue);
		fillSpinner(R.id.spinner_shopselection_country, defaultValue);
	}
	
	
	
	private void fillSpinner(int spinnerId, String[] items) {
		Spinner spinner = (Spinner) findViewById(spinnerId);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}
	
	
	
	private void prepareShopListSection(boolean ready) {
    	showProgressBar(!ready);
    	
    	if (ready) {
    		// the given list of shops contains the picked one?
    		LocalStorage storage = LocalStorage.getInstance();
    		int idShop = -1;
    		int posShop = -1;
    		if (storage.isShopPicked(this)) {
    			idShop = storage.getShop(this).getIdShop();
    		}
    		// get coordinates
    		double currentLatitude = currentLocation.getLatitude();
    		double currentLongitude = currentLocation.getLongitude();
    		// populate list
			shopList = (ListView) findViewById(R.id.list_shops);
			shopItems = new ArrayList<ShopListItem>();
			int i = 0;
	 		for (Shop shop : shops) {
	 			if (shop.getIdShop() == idShop) {
	 				posShop = i;
	 			}
	 			// direction
	 			CharSequence direction = shop.getDirection();
	 			// distance
	 			double shopLatitude = shop.getLatitude();
	 			double shopLongitude = shop.getLongitude();
	 			float[] results = {0};
	 			Location.distanceBetween(currentLatitude, currentLongitude, shopLatitude, shopLongitude, results);
	 			int distance = (int) results[0];
	 			// create list item
	 			ShopListItem item = new ShopListItem(direction, distance);
	 			shopItems.add(item);
	 			// add shop to the map
	 			addShopToMap(shopLatitude, shopLongitude);
	 			
	 			i++;
	 		}
			
			// setting the shop list adapter
	        adapter = new ShopListAdapter(this.getApplicationContext(), shopItems);
	        shopList.setAdapter(adapter);
	        // setting the shop click listener
	        ShopClickListener listener = new ShopClickListener();
	        shopList.setOnItemClickListener(listener);
	        shopList.setOnItemLongClickListener(listener);
	        registerForContextMenu(shopList);
	        
	        // select current shop
	        if (posShop != -1) {
				shopList.setItemChecked(posShop, true);
	        }
    	} else {
    		// removes all markers, overlays, and polylines from the map.
            googleMap.clear();
            shopItems = null;
    	}
	}
	
	
	
	public void countriesReceived(List<Country> countries) {
		countryNames = new String[countries.size() + 1];
		countryNames[0] = getResources().getString(R.string.list_default);
		for (int i = 0; i < countries.size(); i++) {
			CharSequence countryName = countries.get(i).getCountryName();
			countryNames[i + 1] = (String) countryName;
		}
		fillSpinner(R.id.spinner_shopselection_country, countryNames);
	}
	
	
	
	public void regionsReceived(List<Region> regions) {
		regionNames = new String[regions.size() + 1];
		regionNames[0] = getResources().getString(R.string.list_default);
		for (int i = 0; i < regions.size(); i++) {
			CharSequence regionName = regions.get(i).getRegionName();
			regionNames[i + 1] = (String) regionName;
		}
		fillSpinner(R.id.spinner_shopselection_region, regionNames);
	}
	
	
	
	public void citiesReceived(List<City> cities) {
		cityNames = new String[cities.size() + 1];
		cityNames[0] = getResources().getString(R.string.list_default);
		for (int i = 0; i < cities.size(); i++) {
			CharSequence cityName = cities.get(i).getCityName();
			cityNames[i + 1] = (String) cityName;
		}
		fillSpinner(R.id.spinner_shopselection_city, cityNames);
	}



	@Override
	public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
		TextView tv = (TextView) adapterView.getChildAt(0);
		tv.setTextColor(getResources().getColor(R.color.grey_background));
		
		if (i != 0) { // to avoid default value
			CountriesController cController = new CountriesController(this);
			Spinner spinner = (Spinner) findViewById(R.id.spinner_shopselection_country);
			CharSequence countryName = spinner.getSelectedItem().toString();
			switch (adapterView.getId()) {
			case R.id.spinner_shopselection_city:
				prepareShopListSection(false);
				// call web service to load shops from selected city
				spinner = (Spinner) findViewById(R.id.spinner_shopselection_city);
				CharSequence cityName = spinner.getSelectedItem().toString();
				ShopsController sController = new ShopsController(this);
				sController.getShops(cityName);
				break;
				
			case R.id.spinner_shopselection_region:
				// call web service to load cities from selected region
				spinner = (Spinner) findViewById(R.id.spinner_shopselection_region);
				CharSequence regionName = spinner.getSelectedItem().toString();
				cController.getCities(countryName, regionName);
				break;
	
			case R.id.spinner_shopselection_country:
				// call web service to load regions from selected country
				cController.getRegions(countryName);
				break;
			}
		}
	}

	
	
	public void onClickButton(View v) {
		super.onClickButton(v);
    }
	
	
	
	@Override
	protected void showProgressBar(boolean show) {
    	ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar_shopselection);
    	ListView lv = (ListView) findViewById(R.id.list_shops);
		// show progress bar?
		if (show) {
			spinner.setVisibility(View.VISIBLE);
			// hide list view
			lv.setVisibility(View.GONE);
		} else {
			spinner.setVisibility(View.GONE);
			// show list view
			lv.setVisibility(View.VISIBLE);
		}
    }



	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		LocalStorage storage = LocalStorage.getInstance();
		if (!storage.isShopPicked(this)) {
			itemSelected = null;
			itemSelectedPosition = -1;
		}
		
		prepareMapSection();
	}
	
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return false;
	}
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK && itemSelected != null) {
	    	
	    	RelativeLayout rlNormal = (RelativeLayout) itemSelected.findViewById(R.id.listview_shop_normal);
	    	RelativeLayout rlPressed = (RelativeLayout) itemSelected.findViewById(R.id.listview_shop_pressed);
			rlNormal.setVisibility(View.VISIBLE);
			rlPressed.setVisibility(View.GONE);
	    	
			itemSelected = null;
			itemSelectedPosition = -1;
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	
	
    /**
     * Shop list item click listener
     */
    private class ShopClickListener implements ListView.OnItemClickListener, ListView.OnItemLongClickListener, Button.OnClickListener {
    	
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	selectShop(position);
        }

        
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			RelativeLayout rlNormal, rlPressed;
			
			if (itemSelected != null) {
				// another item is selected
				rlNormal = (RelativeLayout) itemSelected.findViewById(R.id.listview_shop_normal);
				rlPressed = (RelativeLayout) itemSelected.findViewById(R.id.listview_shop_pressed);
				rlNormal.setVisibility(View.VISIBLE);
				rlPressed.setVisibility(View.GONE);
			}

			rlNormal = (RelativeLayout) view.findViewById(R.id.listview_shop_normal);
			rlPressed = (RelativeLayout) view.findViewById(R.id.listview_shop_pressed);
			rlNormal.setVisibility(View.GONE);
			rlPressed.setVisibility(View.VISIBLE);
			
			Button b;
			b = (Button) rlPressed.findViewById(R.id.button_seeshop);
			b.setOnClickListener(this);
			b = (Button) rlPressed.findViewById(R.id.button_pickshop);
			b.setOnClickListener(this);

			itemSelected = view;
			itemSelectedPosition = position;
			return true;
		}

		
		@Override
		public void onClick(View v) {
			RelativeLayout rl;
			rl = (RelativeLayout) itemSelected.findViewById(R.id.listview_shop_normal);
			rl.setVisibility(View.VISIBLE);
			rl = (RelativeLayout) itemSelected.findViewById(R.id.listview_shop_pressed);
			rl.setVisibility(View.GONE);
			
			switch (v.getId()) {
			case R.id.button_pickshop:
				selectShop(itemSelectedPosition);
				break;
				
			case R.id.button_seeshop:
				Intent i = new Intent(getBaseContext(), ShopActivity.class);
				Shop s = shops.get(itemSelectedPosition);
				i.putExtra("idShop", s.getIdShop());
				i.putExtra("cityName", s.getCity().getCityName());
				i.putExtra("direction", s.getDirection());
				i.putExtra("schedule", s.getSchedule());
				i.putExtra("phone", s.getPhone());
				i.putExtra("email", s.getEmail());
				i.putExtra("latitude", s.getLatitude());
				i.putExtra("longitude", s.getLongitude());
				i.putExtra("isSelected", shopList.isItemChecked(itemSelectedPosition));
				startActivity(i);
				break;
			}
		}
		
		
		private void selectShop(int position) {
			Shop shop = shops.get(position);
			LocalStorage.getInstance().saveShop(currentActivity, shop);
			shopList.setItemChecked(position, true);
			
			Toast.makeText(currentActivity, R.string.toast_shopselected, Toast.LENGTH_SHORT).show();
		}
		
    }
    

}
