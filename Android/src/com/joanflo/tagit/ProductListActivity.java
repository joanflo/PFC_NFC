package com.joanflo.tagit;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

public class ProductListActivity extends ListActivity {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_productlist);

	    // Get the intent and verify the action
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	    	// Search intent
	    	String query = intent.getStringExtra(SearchManager.QUERY);
	      	searchProducts(query);
	    } else {
	    	
	    }
	}
	
	
	
	private void searchProducts(String query) {
		
	}
	
	
	
}
