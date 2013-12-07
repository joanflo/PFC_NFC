package com.joanflo.tagit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
				boolean userLoged = false;
				chooseInitialActivity(userLoged);
			}

			private void chooseInitialActivity(boolean userLoged) {
				Intent i;
				if (userLoged) {
					i = new Intent(startActivity, MainActivity.class);
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