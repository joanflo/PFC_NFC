package com.joanflo.tagit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
	}
	
	
}
