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
			goToHomeActivity(false);
			break;
			
		case R.id.button_login:
			// Log in
			EditText et;
			et = (EditText) findViewById(R.id.editText_identifier);
			CharSequence userEmail = et.getText();
			et = (EditText) findViewById(R.id.editText_password);
			CharSequence password = et.getText();
			if (!userEmail.toString().equals("") && !password.toString().equals("")) {
				// UI
				showProgressBar(true);
				// call web service
				UsersController controller = new UsersController(this);
				controller.logInUser(userEmail);
			} else {
				Toast.makeText(this, R.string.toast_loginfields, Toast.LENGTH_SHORT).show();
			}
			break;
			
		case R.id.button_singin_login:
			// Register
			Intent iRegistration = new Intent(this, RegistrationActivity.class);
			startActivity(iRegistration);
			break;
		}
	}
	
	
	
	public boolean loginReceived(String dbPassword) {
		try {
			EditText et = (EditText) findViewById(R.id.editText_password);
			CharSequence localPassword = et.getText();
			if (dbPassword.equals(localPassword.toString())) {
				return true;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			showProgressBar(false);
			Toast.makeText(this, R.string.toast_login, Toast.LENGTH_LONG).show();
		}
		return false;
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
	
	
	
	public void goToHomeActivity(boolean loged) {
		Intent iMain = new Intent(this, HomeActivity.class);
		if (loged) {
			EditText et = (EditText) findViewById(R.id.editText_identifier);
			CharSequence userEmail = et.getText();
			iMain.putExtra("userEmail", userEmail);
		}
		startActivity(iMain);
		finish();
	}
	
	
}
