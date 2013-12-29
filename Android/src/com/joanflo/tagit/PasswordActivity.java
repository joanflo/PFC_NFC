package com.joanflo.tagit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PasswordActivity extends Activity {

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_password);
		
		TextView tv = (TextView) findViewById(R.id.textView_changepassword_email);
		tv.setText("joanflo@uib.cat");
	}
	
	
	
	public void onClickButton(View v) {
		
		switch (v.getId()) {
		case R.id.button_skip_singin:
			// change password
			
			break;
		}
	}
	
	
}
