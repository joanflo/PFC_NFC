package com.joanflo.tagit;

import java.util.List;
import com.joanflo.controllers.BrandsController;
import com.joanflo.controllers.CategoriesController;
import com.joanflo.models.Brand;
import com.joanflo.models.Category;
import com.joanflo.utils.LocalStorage;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;


public class ProductSearchActivity extends BaseActivity implements OnItemSelectedListener {
	
	
	private List<Brand> brands;
	private List<Category> categories;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_productsearch);
        
        // update selected item and title, then close the drawer
        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("drawerPosition");
        super.updateSelected(pos);
		        
        
        // call web service
 		BrandsController bController = new BrandsController(this);
 		bController.getBrands();
 		// call web service
 		CategoriesController cController = new CategoriesController(this);
 		cController.getCategories(1);
        
		prepareSearchSection();
	}



	private void prepareSearchSection() {
		String[] defaultValue = { getResources().getString(R.string.list_default) };
		
		TextView et;
		char coin = LocalStorage.getInstance().getLocaleCountry(this).getCoin();
		
		// price from
		et = (TextView) findViewById(R.id.from_coin);
		et.setText(String.valueOf(coin));
		
		// price until
		et = (TextView) findViewById(R.id.until_coin);
		et.setText(String.valueOf(coin));
		
		// brand and category spinners
		fillSpinner(R.id.spinner_productsearch_brand, defaultValue);
		fillSpinner(R.id.spinner_productsearch_category, defaultValue);
	}



	private void fillSpinner(int spinnerId, String[] items) {
		Spinner spinner = (Spinner) findViewById(spinnerId);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}



	public void brandsReceived(List<Brand> brands) {
		this.brands = brands;
		
		String[] brandNames = new String[brands.size() + 1];
		brandNames[0] = getResources().getString(R.string.list_default);
		for (int i = 0; i < brands.size(); i++) {
			CharSequence brandName = brands.get(i).getBrandName();
			brandNames[i + 1] = (String) brandName;
		}
		fillSpinner(R.id.spinner_productsearch_brand, brandNames);
	}



	public void categoriesReceived(List<Category> categories) {
		this.categories = categories;
		
		String[] categoryNames = new String[categories.size() + 1];
		categoryNames[0] = getResources().getString(R.string.list_default);
		for (int i = 0; i < categories.size(); i++) {
			CharSequence categoryName = categories.get(i).getName();
			categoryNames[i + 1] = (String) categoryName;
		}
		fillSpinner(R.id.spinner_productsearch_category, categoryNames);
	}
	
	
	
	public void onClickButton(View v) {
		Intent i;
    	
		switch (v.getId()) {
		// case: search button clicked
		case R.id.button_advancedsearch_productsearch:
			i = new Intent(getBaseContext(), ProductListActivity.class);
			// get search fields
			TextView et;
			Spinner spinner;
			int pos;
			String str;
			RatingBar rb;
			// query name
			et = (TextView) findViewById(R.id.editText_advancedsearch_name);
			i.putExtra("queryName", et.getText());
			// price from
			et = (TextView) findViewById(R.id.editText_advancedsearch_from);
			str = et.getText().toString();
			if (str.equals("")) {
				str = "0";
			}
			i.putExtra("priceFrom", Float.valueOf(str));
			// price since
			et = (TextView) findViewById(R.id.editText_advancedsearch_until);
			str = et.getText().toString();
			if (str.equals("")) {
				str = "0";
			}
			i.putExtra("priceSince", Float.valueOf(str));
			// coin
			char coin = LocalStorage.getInstance().getLocaleCountry(this).getCoin();
			i.putExtra("coin", coin);
			// brand name
			spinner = (Spinner) findViewById(R.id.spinner_productsearch_brand);
			pos = spinner.getSelectedItemPosition();
			if (pos == 0) { // default value
				i.putExtra("brandName", "");
			} else {
				i.putExtra("brandName", brands.get(pos).getBrandName());
			}
			// id category
			spinner = (Spinner) findViewById(R.id.spinner_productsearch_category);
			pos = spinner.getSelectedItemPosition();
			if (pos == 0) {// default value
				i.putExtra("idCategory", -1);
			} else {
				i.putExtra("idCategory", categories.get(pos).getIdCategory());
			}
			// rating
			rb = (RatingBar) findViewById(R.id.ratingBar_advancedsearch);
			i.putExtra("rating", rb.getRating());
			// display new activity
			i.putExtra("advancedSearch", true);
			startActivity(i);
			break;
			
		default:
			super.onClickButton(v);
			break;
		}
    }



	@Override
	public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
		TextView tv = (TextView) adapterView.getChildAt(0);
		tv.setTextColor(getResources().getColor(R.color.grey_background));
	}



	@Override
	public void onNothingSelected(AdapterView<?> arg0) { }

}
