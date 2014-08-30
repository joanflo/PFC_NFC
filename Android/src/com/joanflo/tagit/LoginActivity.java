package com.joanflo.tagit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.joanflo.controllers.UsersController;

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
			goToHomeActivity();
			break;
			
		case R.id.button_login:
			// Log in
			TextView tv;
			tv = (TextView) findViewById(R.id.textView_identifier);
			CharSequence userEmail = tv.getText();
			tv = (TextView) findViewById(R.id.textView_password);
			CharSequence password = tv.getText();
			// UI
			showProgressBar(true);
			// call web service
			UsersController controller = new UsersController(this);
			controller.logInUser(userEmail, password.toString());
			break;
			
		case R.id.button_singin_login:
			// Register
			Intent iRegistration = new Intent(this, RegistrationActivity.class);
			startActivity(iRegistration);
			break;
		}
	}
	
	
	
	public void loginReceived(boolean successful) {
		if (successful) {
			goToHomeActivity();
		} else {
			showProgressBar(false);
			Toast.makeText(this, R.string.toast_login, Toast.LENGTH_LONG).show();
		}
	}
	
	
	
	private void showProgressBar(boolean show) {
		ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar_login);
		EditText etIdentifier = (EditText) findViewById(R.id.editText_identifier);
		TextView tvIdentifier = (TextView) findViewById(R.id.textView_identifier);
		EditText etPassword = (EditText) findViewById(R.id.editText_password);
		TextView tvPassword = (TextView) findViewById(R.id.textView_password);
		Button buttonLogin = (Button) findViewById(R.id.button_login);
		// show progress bar?
		if (show) {
			spinner.setVisibility(View.VISIBLE);
			// hide another UI elements
			etIdentifier.setVisibility(View.INVISIBLE);
			tvIdentifier.setVisibility(View.INVISIBLE);
			etPassword.setVisibility(View.INVISIBLE);
			tvPassword.setVisibility(View.INVISIBLE);
			// disable login button
			buttonLogin.setEnabled(false);
		} else {
			spinner.setVisibility(View.GONE);
			// show another UI elements
			etIdentifier.setVisibility(View.VISIBLE);
			tvIdentifier.setVisibility(View.VISIBLE);
			etPassword.setVisibility(View.VISIBLE);
			tvPassword.setVisibility(View.VISIBLE);
			// enable login button
			buttonLogin.setEnabled(true);
		}
		
	}
	
	
	
	private void goToHomeActivity() {
		Intent iMain = new Intent(this, HomeActivity.class);
		startActivity(iMain);
		finish();
	}
	
	
}
