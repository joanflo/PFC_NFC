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
import com.joanflo.models.City;
import com.joanflo.models.Country;
import com.joanflo.models.Region;
import com.joanflo.models.Shop;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;


public class ShopSelectionActivity extends BaseActivity implements OnItemSelectedListener {

	
	private String[] cities = {"Palma", "Paris", "London"};
	private String[] regions = {"Illes Balears", "Paris", "Barcelona"};
	private String[] countries = {"Espanya", "Françe", "United Kingdom"};
	
	private GoogleMap googleMap;
	
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
        
		prepareMapSection();
        preparePlaceSection();
        prepareShopListSection();
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
	            	loadShops();
	            }
	        }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	
	private void loadShops() {
		// TODO: mètode que carregui tendes pròximes
		City palma;
		Region illesBalears = null;
		Country espanya = null;
		
		palma = new City("Palma", shops, illesBalears);
		
		List<City> ciutats = new ArrayList<City>();
		ciutats.add(palma);
		illesBalears = new Region("Illes Balears", ciutats, espanya);
		
		List<Region> regions = new ArrayList<Region>();
		regions.add(illesBalears);
		espanya = new Country("Espanya", regions, 34, '€');
		
		shops = new ArrayList<Shop>();
		shops.add(new Shop(0, palma, "Sant Vicenç Ferrer 117", "L-V 9.00h a 19.00h\nS 9.00h a 13.00h", "686922414", "info@tenda.cat", 39.57993, 2.666000));
		shops.add(new Shop(0, palma, "Sant Vicenç Ferrer 117", "L-V 9.00h a 19.00h\nS 9.00h a 13.00h", "686922414", "info@tenda.cat", 39.57000, 2.666812));
		shops.add(new Shop(0, palma, "Sant Vicenç Ferrer 117", "L-V 9.00h a 19.00h\nS 9.00h a 13.00h", "686922414", "info@tenda.cat", 39.57993, 2.666812));
		/*********************************************************/
		
		// add shops to map
		for (int i = 0; i < shops.size(); i++) {
			addShopToMap(shops.get(i).getLatitude(), shops.get(i).getLongitude());
		}
		
		// go to nearest shop
		googleMap.setMyLocationEnabled(true);
		googleMap.getUiSettings().setMyLocationButtonEnabled(true);
		if (shops.size() > 0) {
			LatLng loc = new LatLng(shops.get(0).getLatitude(), shops.get(0).getLongitude());
			CameraPosition cameraPosition = new CameraPosition.Builder().target(loc).zoom(13).build();
			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
		fillSpinner(R.id.spinner_shopselection_city, cities);
		fillSpinner(R.id.spinner_shopselection_region, regions);
		fillSpinner(R.id.spinner_shopselection_country, countries);
	}
	
	
	
	private void fillSpinner(int spinnerId, String[] items) {
		Spinner spinner = (Spinner) findViewById(spinnerId);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}
	
	
	
	private void prepareShopListSection() {
		shopList = (ListView) findViewById(R.id.list_shops);
		shopItems = new ArrayList<ShopListItem>();
 		for (int i = 0; i < shops.size(); i++) {
 			CharSequence direction = shops.get(i).getDirection();
 			int distance = 10;
 			ShopListItem item = new ShopListItem(direction, distance);
 			shopItems.add(item);
 			addShopToMap(shops.get(i).getLatitude(), shops.get(i).getLongitude());
 		}
		
		// setting the shop list adapter
        adapter = new ShopListAdapter(this.getApplicationContext(), shopItems);
        shopList.setAdapter(adapter);
        // setting the shop click listener
        ShopClickListener listener = new ShopClickListener();
        shopList.setOnItemClickListener(listener);
        shopList.setOnItemLongClickListener(listener);
        registerForContextMenu(shopList);
	}



	@Override
	public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
		String name;
		
		TextView tv = (TextView) adapterView.getChildAt(0);
		tv.setTextColor(getResources().getColor(R.color.grey_background));
		
		switch (v.getId()) {
		case R.id.spinner_shopselection_city:
			name = cities[i];
			break;

		case R.id.spinner_shopselection_region:
			name = regions[i];
			break;

		case R.id.spinner_shopselection_country:
			name = countries[i];
			break;
		}
	}

	
	
	public void onClickButton(View v) {
		super.onClickButton(v);
    }



	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		prepareMapSection();
	}
	
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Log.i("DEBUG", "onContextItemSelected!");
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
			shopList.setItemChecked(position, true);
		}
		
    }
    

}
