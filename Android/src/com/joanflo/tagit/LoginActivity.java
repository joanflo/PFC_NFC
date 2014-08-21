package com.joanflo.tagit;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.joanflo.network.AsyncResponse;
import com.joanflo.network.RESTClient;
import com.joanflo.tagit.R.id;

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
			TextView tv;
			tv = (TextView) findViewById(R.id.textView_identifier);
			CharSequence userEmail = tv.getText();
			tv = (TextView) findViewById(R.id.textView_password);
			CharSequence password = tv.getText();
			RESTClient.getInstance().logInUser(this, userEmail, password);
			break;
			
		case R.id.button_singin_login:
			// Register
			Intent iRegistration = new Intent(this, RegistrationActivity.class);
			startActivity(iRegistration);
			break;
		}
	}
	
	
	
	public synchronized void requestFinished(JSONObject jResponses) {
		// TODO
		Toast.makeText(this, "YEAH", Toast.LENGTH_LONG).show();
	}
	
	
}
