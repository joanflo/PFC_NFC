package com.joanflo.tagit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.joanflo.controllers.BadgesController;
import com.joanflo.controllers.CategoriesController;
import com.joanflo.controllers.CountriesController;
import com.joanflo.controllers.LanguagesController;
import com.joanflo.controllers.ShopsController;
import com.joanflo.utils.LocalStorage;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends Activity {

	private Activity startActivity = this;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_start);
		
		
		/*
		BadgesController badgesController = new BadgesController(this);
		badgesController.getBadge("Newbie!");
		badgesController.getBadges();
		*/
		/*
		ShopsController shopsController = new ShopsController(this);
		shopsController.getShops(2, 5);
		shopsController.getShops("Palma");
		shopsController.getShop(2);
		*/
		/*
		CategoriesController categoriesController = new CategoriesController(this);
		categoriesController.getCategories(0);
		categoriesController.getProductCategories(5);
		categoriesController.getSubategories(2);
		categoriesController.getSubategoriesCount(2);
		*/
		/*
		CountriesController countriesController = new CountriesController(this);
		countriesController.getCountries();
		countriesController.getRegions("Espanya");
		countriesController.getCities("Espanya", "Illes Balears");
		countriesController.getCountry("Palma");
		*/
		/*
		LanguagesController languagesController = new LanguagesController(this);
		languagesController.getLanguages();
		*/
		
		
		
		
		
		
		
		/*
		ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
		Runnable task = new Runnable() {
			public void run() {
				chooseInitialActivity();
			}

			private void chooseInitialActivity() {
				Intent i;
				if (LocalStorage.getInstance().isUserLoged(startActivity)) {
					i = new Intent(startActivity, HomeActivity.class);
				} else {
					i = new Intent(startActivity, LoginActivity.class);
				}
		    	startActivity(i);
				finish();
			}
		};
		worker.schedule(task, 1, TimeUnit.SECONDS);
		*/
	}
	
	
}
