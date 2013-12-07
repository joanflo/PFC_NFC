package com.joanflo.tagit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegistrationActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_registration);
		
		
	}
	
	
	
	
	public void onClickButton(View v) {
		
		switch (v.getId()) {
		case R.id.button_skip_singin:
			// Skip log in
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			finish();
			break;
			
		case R.id.button_singin_singin:
			// Sing in
			
			break;
		}
	}
	
	
}
