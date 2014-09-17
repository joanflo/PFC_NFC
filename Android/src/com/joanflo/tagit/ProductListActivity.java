package com.joanflo.tagit;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import com.joanflo.adapters.ProductListAdapter;
import com.joanflo.adapters.ProductListItem;
import com.joanflo.adapters.SpinnerNavAdapter;
import com.joanflo.adapters.SpinnerNavItem;
import com.joanflo.controllers.CategoriesController;
import com.joanflo.controllers.ProductsController;
import com.joanflo.controllers.TaxesController;
import com.joanflo.models.Category;
import com.joanflo.models.Country;
import com.joanflo.models.Product;
import com.joanflo.models.ProductBelongsCategory;
import com.joanflo.models.ProductImage;
import com.joanflo.models.Review;
import com.joanflo.models.Tax;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.SearchUtils;
import android.app.SearchManager;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


public class ProductListActivity extends BaseActivity implements ActionBar.OnNavigationListener {

	
	private List<Product> products;
	private List<ProductListItem> productItems;
	private int currentPosition = 0;
	
    private ArrayList<SpinnerNavItem> navSpinner;
    
	private int requestsNumber;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_productlist);
		

		super.showProgressBar(true);
	    // Get the intent and verify the action
    	ProductsController controller = new ProductsController(this);
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	    	// Search intent
	    	String query = intent.getStringExtra(SearchManager.QUERY);
	    	
	    	// call web service
	    	controller.getProducts(query);
	    	
	    } else {
	    	Bundle bundle = intent.getExtras();
	    	
	    	if (bundle.getBoolean("advancedSearch")) {
		    	// Advanced search intent
	    		CharSequence queryName = bundle.getCharSequence("queryName");
	    		float priceFrom = bundle.getFloat("priceFrom");
	    		float priceSince = bundle.getFloat("priceSince");
	    		char coin = bundle.getChar("coin");
	    		CharSequence brandName = bundle.getCharSequence("brandName");
	    		int idCategory = bundle.getInt("idCategory");
	    		float rating = bundle.getFloat("rating");
	    		
	    		// call web service
		    	controller.getProducts(queryName, priceFrom, priceSince, coin, brandName, idCategory, rating);
	    	
		    } else {
		    	// we come from category list
		    	int idCategory = bundle.getInt("idCategory");
		    	
		    	// call web service
		    	controller.getProducts(idCategory);
		    }
	    }
	    
	}
	
	
	
	private void prepareActionBar() {
		ActionBar actionBar = getActionBar();
		
		// Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(false);
        
        // Enabling Spinner dropdown navigation
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        
        // Spinner title navigation data
        navSpinner = new ArrayList<SpinnerNavItem>();
        String[] navSpinnerTitles = getResources().getStringArray(R.array.nav_spinner_items);
        TypedArray navSpinnerIcons = getResources().obtainTypedArray(R.array.nav_spinner_icons);
        for (int i = 0; i < navSpinnerTitles.length; i++) {
        	navSpinner.add(new SpinnerNavItem(navSpinnerTitles[i], navSpinnerIcons.getResourceId(i, -1)));
        }
        navSpinnerIcons.recycle();
        
        // assigning the spinner navigation
        SpinnerNavAdapter adapter = new SpinnerNavAdapter(getApplicationContext(), navSpinner);
        actionBar.setListNavigationCallbacks(adapter, this);
	}



	@Override
	protected void onResume() {
		super.onResume();
		
		// uncheck elements
        ListView productList = (ListView) findViewById(R.id.listView_product);
		productList.setItemChecked(currentPosition, false);
	}
	
	
	
	public void productsReceived(List<Product> products) {
		this.products = products;
		
		if (products.size() == 0) {
			// empty products list
			ImageView iv = (ImageView) findViewById(R.id.imageview_productList_empty);
			iv.setVisibility(View.VISIBLE);
			ListView productList = (ListView) findViewById(R.id.listView_product);
			productList.setVisibility(View.GONE);
			super.showProgressBar(false);
			Toast.makeText(this, R.string.toast_problem_empty, Toast.LENGTH_LONG).show();
			
		} else {
			// for each product, 4 requests (tax, front image, reviews, category)
			requestsNumber = products.size() * 4;
			
			// get user country
			Country country = LocalStorage.getInstance().getLocaleCountry(this);
			
			// for each product
			TaxesController tController = new TaxesController(this);
			ProductsController pController = new ProductsController(this);
			CategoriesController cController = new CategoriesController(this);
			for (Product product : products) {
				int idProduct = product.getIdProduct();
				// call web service
				tController.getTax(idProduct, country.getCountryName());
				// call web service
				pController.getProductFrontImage(idProduct);
				// call web service
				pController.getReviews(idProduct);
				// call web service
				cController.getProductCategories(idProduct);
			}
		}
	}
	
	
	
	public void taxReceived(Tax tax) {
		// search product's tax
		int idProduct = tax.getProduct().getIdProduct();
		Product product = SearchUtils.searchProductById(idProduct, products);
		// add tax to product
		product.addTax(tax);
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void frontImageReceived(ProductImage productImage) {
		// search product's front image
		int idProduct = productImage.getProduct().getIdProduct();
		Product product = SearchUtils.searchProductById(idProduct, products);
		// add image to product
		product.addImage(productImage);
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void reviewsReceived(List<Review> reviews) {
		// for each review
		for (Review review : reviews) {
			// search product's review
			int idProduct = review.getProduct().getIdProduct();
			Product product = SearchUtils.searchProductById(idProduct, products);
			// add review to product
			product.addReview(review);
		}
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	public void categoriesReceived(List<Category> categories, List<ProductBelongsCategory> productBelongsCategories) {
		// for each category
		for (int i = 0; i < categories.size(); i++) {
			// get models
			Category category = categories.get(i);
			ProductBelongsCategory productBelongsCategory = productBelongsCategories.get(i);
			// search product's category
			int idProduct = productBelongsCategory.getProduct().getIdProduct();
			Product product = SearchUtils.searchProductById(idProduct, products);
			// add image to product
			product.addCategory(category);
		}
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	private synchronized void checkLastRequest() {
		requestsNumber--;
		if (requestsNumber == 0) {
			// last request
			prepareList();
			prepareActionBar();
	        super.showProgressBar(false);
		}
	}
	
	
	
	private void prepareList() {
		Country country = LocalStorage.getInstance().getLocaleCountry(this);
		// creating product list items
		Iterator<Product> it = products.iterator();
		productItems = new ArrayList<ProductListItem>();
        while(it.hasNext()) {
        	Product product = (Product) it.next();

        	// product id
        	int idProduct = product.getIdProduct();
        	// front image
        	ProductImage front = SearchUtils.searchFrontImage(product);
        	URL url = null;
        	CharSequence desc = "";
        	if (front != null) { // image not found
        		url = front.getUrl();
        		desc = front.getDescription();
        	}
        	// product name
        	CharSequence pName = product.getName();
        	// product brand
        	CharSequence bName = product.getBrand().getBrandName();
        	// category name
        	CharSequence cName = product.getCategories().get(0).getName();
        	// calculate prices
        	Tax tax = product.getTaxes().get(0);
        	DecimalFormat df = new DecimalFormat("00.00");
        	CharSequence price = df.format(product.calculatePrice(tax));
        	CharSequence coin = String.valueOf(country.getCoin());
        	double averageRate = product.calculateAverageRating();
        	CharSequence rate;
        	if (averageRate == -1) {
        		rate = "-";
        	} else {
        		df = new DecimalFormat("0.0");
        		rate = df.format(averageRate);
        	}
        	
        	ProductListItem pItem = new ProductListItem(this, idProduct, url, desc, pName, bName, cName, price, coin, rate);
        	productItems.add(pItem);
        }
        
        // setting the product list adapter
        ProductListAdapter adapter = new ProductListAdapter(getApplicationContext(), productItems);
        ListView productList = (ListView) findViewById(R.id.listView_product);
        productList.setEmptyView(findViewById(R.layout.list_empty));
        productList.setAdapter(adapter);
        
        // setting the product click listener
        productList.setOnItemClickListener(new ProductClickListener());
	}
	
	
	
	public void onClickButton(View v) {
		super.onClickButton(v);
    }
	
	
	
	private class ProductClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// go to product
			Intent i;
			currentPosition = position;
			ProductListItem productItem = productItems.get(position);
			i = new Intent(getBaseContext(), ProductActivity.class);
			i.putExtra("idProduct", productItem.getIdProduct());
			startActivity(i);
		}
		
	}



	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		ProductListItem.setSortCriteria(itemPosition);
		Collections.sort(productItems);
		ProductListAdapter adapter = new ProductListAdapter(getApplicationContext(), productItems);
        ListView productList = (ListView) findViewById(R.id.listView_product);
        productList.setAdapter(adapter);
		return false;
	}
	
	
}
