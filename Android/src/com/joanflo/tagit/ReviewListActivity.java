package com.joanflo.tagit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.joanflo.adapters.ReviewListAdapter;
import com.joanflo.adapters.ReviewListItem;
import com.joanflo.controllers.ProductsController;
import com.joanflo.controllers.UsersController;
import com.joanflo.models.Product;
import com.joanflo.models.ProductImage;
import com.joanflo.models.Review;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.SearchUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


public class ReviewListActivity extends BaseActivity {
	
	private Activity activity = this;
	
	private List<Review> reviews;

	int idProductBundle;
	CharSequence productNameBundle;
	CharSequence averageRateBundle;
	
	private boolean viewProductReviews;
	private int currentPosition = 0;
	private int requestsNumber;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_reviewlist);
		super.setTitle(R.string.title_reviewlist);
		
        
        // get extras
        Bundle bundle = getIntent().getExtras();
        idProductBundle = bundle.getInt("idProduct", -1);
        CharSequence userEmail = bundle.getCharSequence("userEmail", null);
        
        super.showProgressBar(true);
        // call web service
        viewProductReviews = userEmail == null;
        if (viewProductReviews) {
        	// product reviews
        	productNameBundle = bundle.getCharSequence("productName");
            averageRateBundle = bundle.getCharSequence("averageRate");
        	
        } else {
        	// user reviews
        	// call web service
        	UsersController uController = new UsersController(this);
        	uController.getReviews(userEmail);
        }
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		
		// uncheck elements
		ListView reviewList = (ListView) findViewById(R.id.listView_review);
		reviewList.setItemChecked(currentPosition, false);
		
		if (viewProductReviews) {
			super.showProgressBar(true);
        	// product reviews
        	ProductsController pController = new ProductsController(this);
        	pController.getReviews(idProductBundle);
        }
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (viewProductReviews) {
			getMenuInflater().inflate(R.menu.home, menu);
			
			// show add review item
			MenuItem reviewItem = menu.findItem(R.id.action_review);
			reviewItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
		
		return super.onCreateOptionsMenu(menu);
	}
	
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar actions click
        switch (item.getItemId()) {
        case R.id.action_review:
        	Intent intent;
    		if (LocalStorage.getInstance().isUserLoged(this)) {
	        	intent = new Intent(this, NewReviewActivity.class);
    	        intent.putExtra("idProduct", idProductBundle);
    	        intent.putExtra("productName", productNameBundle);
    	        intent.putExtra("averageRate", averageRateBundle);
    		} else {
        		Toast.makeText(this, R.string.toast_registration, Toast.LENGTH_SHORT).show();
        		intent = new Intent(this, RegistrationActivity.class);
        	}
        	startActivity(intent);
            return true;
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	
	
	public void reviewsReceived(List<Review> reviews) {
		this.reviews = reviews;
		
		if (reviews.size() == 0) {
			// empty reviews list
			ImageView iv = (ImageView) findViewById(R.id.imageview_reviewList_empty);
			iv.setVisibility(View.VISIBLE);
			ListView reviewList = (ListView) findViewById(R.id.listView_review);
			reviewList.setVisibility(View.GONE);
			super.showProgressBar(false);
			Toast.makeText(this, R.string.toast_problem_empty, Toast.LENGTH_LONG).show();
			
		} else {
			if (!viewProductReviews) {
				requestsNumber = reviews.size();
				
				// for each review
				ProductsController controller = new ProductsController(this);
				for (Review review : reviews) {
					int idProduct = review.getProduct().getIdProduct();
					// call web service
					controller.getProductFrontImage(idProduct);
				}
				
			} else {
				prepareList();
				super.showProgressBar(false);
			}
		}
	}
	
	
	
	public void frontImageReceived(ProductImage productImage) {
		// search product's front image
		int idProduct = productImage.getProduct().getIdProduct();
		Product product = SearchUtils.searchProductReviewById(idProduct, reviews);
		// add image to product
		product.addImage(productImage);
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	private synchronized void checkLastRequest() {
		requestsNumber--;
		if (requestsNumber == 0) {
			// last request
			prepareList();
	        super.showProgressBar(false);
		}
	}



	private void prepareList() {
		// creating review list items
		List <ReviewListItem> reviewItems = new ArrayList<ReviewListItem>(reviews.size());
		Iterator<Review> it = reviews.iterator();
        while(it.hasNext()) {
        	Review review = it.next();
	        ReviewListItem reviewItem;
	        if (viewProductReviews) {
	        	CharSequence userEmail = review.getUser().getUserEmail();
	        	CharSequence nick = review.getUser().getNick();
	        	reviewItem = new ReviewListItem(userEmail, nick, review.getDate(), review.getRating(), review.getComment());
	        } else {
	        	reviewItem = new ReviewListItem(review.getProduct(), review.getDate(), review.getRating(), review.getComment());
	        }
	        reviewItems.add(reviewItem);
        }
        
        // setting the review list adapter
        ReviewListAdapter adapter = new ReviewListAdapter(getApplicationContext(), reviewItems);
        ListView reviewList = (ListView) findViewById(R.id.listView_review);
        reviewList.setAdapter(adapter);
        
        // setting the review click listener
        reviewList.setOnItemClickListener(new ReviewClickListener());
	}



	public void onClickButton(View v) {
		super.onClickButton(v);
    }
	
	
	
	private class ReviewClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent i;
			currentPosition = position;
			
			Review review = reviews.get(position);
			if (viewProductReviews) {
				// goto user profile activity
				i = new Intent(getBaseContext(), UserProfileActivity.class);
				CharSequence currentUserEmail = review.getUser().getUserEmail();
				CharSequence loggedUserEmail = LocalStorage.getInstance().getUserEmail(activity);
				if (currentUserEmail.equals(loggedUserEmail)) {
					i.putExtra("drawerPosition", PROFILE);
				} else {
					i.putExtra("userEmail", currentUserEmail);
				}
				
			} else {
				// goto product activity
				i = new Intent(getBaseContext(), ProductActivity.class);
				int idProduct = review.getProduct().getIdProduct();
				i.putExtra("idProduct", idProduct);
			}
			startActivity(i);
		}
		
	}
	
	
}