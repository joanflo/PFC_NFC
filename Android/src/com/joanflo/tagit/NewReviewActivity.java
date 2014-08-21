package com.joanflo.tagit;

import java.net.MalformedURLException;
import java.text.DecimalFormat;

import org.json.JSONObject;

import com.joanflo.models.Brand;
import com.joanflo.models.City;
import com.joanflo.models.Collection;
import com.joanflo.models.Country;
import com.joanflo.models.Language;
import com.joanflo.models.Product;
import com.joanflo.models.ProductImage;
import com.joanflo.models.Region;
import com.joanflo.models.Review;
import com.joanflo.models.User;
import com.joanflo.network.ImageLoader;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


public class NewReviewActivity extends BaseActivity {


	private Product product;
	private User user;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_newreview);
		
        
        loadInfo();
        
        prepareNewReviewSection();
	}



	private void loadInfo() {
		Country espanya = new Country("Espanya", null, 34, Country.EURO);
		Region balears = new Region("Balears", null, espanya);
		City palma = new City("Palma", null, balears);
		Language catala = new Language("Català");
		user = new User("joan@uib.cat", palma, catala, "Joan_flo", "Joan", "Florit Gomila", 23, "password", "686922414", "Sant Vicenç Ferrer 117");
		
		Brand brand = new Brand("Vans", "", "", "", 0, 0);
		Collection col = new Collection(0, "primavera", 2014);
		Product p1 = new Product(0, brand, col, "Camiseta verda", "Una camiseta verda", "896-25/3", "100% cotton");
		
		ProductImage img1;
		try {
			img1 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p1, ProductImage.TYPE_FRONT, "description...");
			p1.addImage(img1);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img14;
		try {
			img14 = new ProductImage("http://shop.camisetasfrikis.es/163-1195-large/camiseta-i-love-mates.jpg", p1, ProductImage.TYPE_REGULAR, "description...");
			p1.addImage(img14);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		Review r1 = new Review(0, p1, user, 5, "Lorem ipsum...");
		user.addReview(r1);
		p1.addReview(r1);
		Review r2 = new Review(0, p1, user, (float) 3.5, "Lorem ipsum...");
		user.addReview(r2);
		p1.addReview(r2);
		Review r3 = new Review(0, p1, user, 5, "Lorem ipsum...");
		user.addReview(r3);
		p1.addReview(r3);
		
		product = p1;
	}
	
	
	
	private void prepareNewReviewSection() {
		// front image
		ImageView iv = (ImageView) findViewById(R.id.imageView_newreview_front);
		ProductImage front = product.getFrontImage();
		if (front != null) {
			iv.setContentDescription(front.getDescription());
			ImageLoader il = new ImageLoader(iv);
			il.execute(front.getUrl());
		} else {
			iv.setImageResource(R.drawable.default_image);
		}
		
		// product name
		TextView tv;
		tv = (TextView) findViewById(R.id.textView_newreview_name);
		tv.setText(product.getName());
		
		// product average rating
		tv = (TextView) findViewById(R.id.textView_newreview_averagerating);
		DecimalFormat df = new DecimalFormat("0.00");
		double averageRate = product.calculateAverageRating();
    	CharSequence rate;
    	if (averageRate == -1) {
    		rate = "-";
    	} else {
    		df = new DecimalFormat("0.0");
    		rate = df.format(averageRate);
    	}
		tv.setText(rate);
	}



	public void onClickButton(View v) {
		Intent i;
    	
		switch (v.getId()) {
		case R.id.button_newreview_seereview:
			i = new Intent(this, ReviewListActivity.class);
	        i.putExtra("idProduct", product.getIdProduct());
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
		EditText et = (EditText) findViewById(R.id.editText_newreview_comment);
		CharSequence comment = et.getText();
		
		RatingBar bar = (RatingBar) findViewById(R.id.ratingBar_newreview);
		float rating = bar.getRating();
		
		Review review = new Review(666, product, user, rating, comment);
	}
	
	
	
	public synchronized void requestFinished(JSONObject jResponses) {
		// TODO
	}
	
	
}