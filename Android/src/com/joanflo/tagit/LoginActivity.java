package com.joanflo.tagit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_login);
		
		
	}
	
	
	
	public void onClickButton(View v) {
		
		switch (v.getId()) {
		case R.id.button_skip_login:
			// Skip log in
			Intent iMain = new Intent(this, HomeActivity.class);
			startActivity(iMain);
			finish();
			break;
			
		case R.id.button_login:
			// Log in
			break;
			
		case R.id.button_singin_login:
			// Register
			Intent iRegistration = new Intent(this, RegistrationActivity.class);
			startActivity(iRegistration);
			break;
		}
	}
	
	
}
