package com.joanflo.tagit;

import com.joanflo.controllers.ProductsController;
import com.joanflo.controllers.UsersController;
import com.joanflo.models.Product;
import com.joanflo.models.ProductImage;
import com.joanflo.models.Review;
import com.joanflo.models.User;
import com.joanflo.network.ImageLoader;
import com.joanflo.utils.LocalStorage;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class NewReviewActivity extends BaseActivity {


	private int idProduct;
	private CharSequence productName;
	private CharSequence averageRate;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_newreview);
		super.setTitle(R.string.title_createreview);
		
		// get product data
        Bundle bundle = getIntent().getExtras();
        idProduct = bundle.getInt("idProduct");
        productName = bundle.getCharSequence("productName");
        averageRate = bundle.getCharSequence("averageRate");
        
        super.showProgressBar(true);
        // call web service
        ProductsController controller = new ProductsController(this);
        controller.getProductFrontImage(idProduct);
	}
	
	
	
	private void prepareNewReviewSection(ProductImage frontImage) {
		TextView tv;
		
		// product name
		tv = (TextView) findViewById(R.id.textView_newreview_name);
		tv.setText(productName);
		
		// product average rating
		tv = (TextView) findViewById(R.id.textView_newreview_averagerating);
		tv.setText(averageRate);
		
		// front image
		ImageView iv = (ImageView) findViewById(R.id.imageView_newreview_front);
		iv.setContentDescription(frontImage.getDescription());
		ImageLoader il = new ImageLoader(iv);
		il.execute(frontImage.getUrl());
	}
	
	
	
	public void frontImageReceived(ProductImage frontImage) {
        prepareNewReviewSection(frontImage);
		
		super.showProgressBar(false);
	}



	public void onClickButton(View v) {
		Intent i;
    	
		switch (v.getId()) {
		case R.id.button_newreview_seereview:
			i = new Intent(this, ReviewListActivity.class);
	        i.putExtra("idProduct", idProduct);
	        i.putExtra("productName", productName);
	        i.putExtra("averageRate", averageRate);
	        startActivity(i);
			break;
			
		case R.id.button_newreview_sendreview:
			sendReview();
			break;
			
		default:
			super.onClickButton(v);
			break;
		}
    }



	private void sendReview() {
		// comment
		EditText et = (EditText) findViewById(R.id.editText_newreview_comment);
		CharSequence comment = et.getText().toString();
		
		// required field
		if (!comment.equals("")) {
			// id product
			Product product = new Product(idProduct, null, null, "", "", "", "");
	
			// user email
			CharSequence userEmail = LocalStorage.getInstance().getUserEmail(this);
			User user = new User(userEmail, null, null, "", "", "", 0, "", "", "");
			
			// rating
			RatingBar bar = (RatingBar) findViewById(R.id.ratingBar_newreview);
			float rating = bar.getRating();
			
			// create review
			Review review = new Review(0, product, user, rating, comment, null);
			
			super.showProgressBar(true);
			// call web service
			UsersController controller = new UsersController(this);
			controller.createReview(review);
			
		} else {
			Toast.makeText(this, R.string.toast_requiredfields3, Toast.LENGTH_SHORT).show();
		}
	}
	
	
	
	public void reviewCreated(Review review) {
		showProgressBar(false);
		
		if (review == null) {
			// problem
			Toast.makeText(this, R.string.toast_problem_creatingreview, Toast.LENGTH_SHORT).show();
			
		} else {
			// review created successfully
			Toast.makeText(this, R.string.toast_reviewcreated, Toast.LENGTH_SHORT).show();
			finish();
		}
	}
	
	
}