package com.joanflo.tagit;

import com.joanflo.controllers.UsersController;
import com.joanflo.utils.LocalStorage;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class PasswordActivity extends Activity {

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_password);
		
		TextView tv = (TextView) findViewById(R.id.textView_changepassword_email);
		CharSequence userEmail = LocalStorage.getInstance().getUserEmail(this);
		tv.setText(userEmail);
	}
	
	
	
	public void passwordChanged(boolean successful) {
		if (successful) {
			Toast.makeText(this, R.string.toast_password2, Toast.LENGTH_LONG).show();
			finish();
		} else {
			Toast.makeText(this, R.string.toast_password, Toast.LENGTH_LONG).show();
		}
		showProgressBar(false);
	}
	
	
	
	private boolean requiredFieldsAreFilled() {
		EditText et;
		
		// old password field
		et = (EditText) findViewById(R.id.editText_changepassword_oldpassword);
		if (et.getText().toString().equals("")) {
			return false;
		}
		
		// new password fields
		et = (EditText) findViewById(R.id.editText_changepassword_newpassword);
		if (et.getText().toString().equals("")) {
			// empty field
			return false;
		} else {
			CharSequence firstPassword = et.getText().toString();
			et = (EditText) findViewById(R.id.editText_changepassword_repeatnewpassword);
			if (!et.getText().toString().equals(firstPassword)) {
				// both passwords must be equals
				return false;
			}
		}
		
		return true;
	}
	
	
	
	private void showProgressBar(boolean show) {
		ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar_changepassword);
		ScrollView sv = (ScrollView) findViewById(R.id.aux14);
		Button button = (Button) findViewById(R.id.button_changepassword);
		// show progress bar?
		if (show) {
			spinner.setVisibility(View.VISIBLE);
			// hide scroll view
			sv.setVisibility(View.GONE);
			// disable sign in button
			button.setEnabled(false);
		} else {
			spinner.setVisibility(View.GONE);
			// show scroll view
			sv.setVisibility(View.VISIBLE);
			// enable sign in button
			button.setEnabled(true);
		}
	}
	
	
	
	public void onClickButton(View v) {
		
		switch (v.getId()) {
		case R.id.button_changepassword:
			// change password
			if (requiredFieldsAreFilled()) {
				EditText et;
				// get old password
				et = (EditText) findViewById(R.id.editText_changepassword_oldpassword);
				CharSequence oldPassword = et.getText().toString();
				// get new password
				et = (EditText) findViewById(R.id.editText_changepassword_newpassword);
				CharSequence newPassword = et.getText().toString();
				// get user email
				CharSequence userEmail = LocalStorage.getInstance().getUserEmail(this);
				// UI
				showProgressBar(true);
				// call web service
				UsersController controller = new UsersController(this);
				controller.changeUserPassword(userEmail, newPassword, oldPassword);
			} else {
				Toast.makeText(this, R.string.toast_requiredfields2, Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}
	
	
}
