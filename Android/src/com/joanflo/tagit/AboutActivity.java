package com.joanflo.tagit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_about);
	}
	
	
	
	public void onClickButton(View v) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		String url = "";
		
		switch (v.getId()) {
		case R.id.imageButton_about_github:
			url = "https://github.com/joanflo";
			break;
			
		case R.id.imageButton_about_linkedin:
			url = "https://www.linkedin.com/in/joanflo";
			break;
			
		case R.id.imageButton_about_twitter:
			url = "https://twitter.com/Joan_flo";
			break;
		}

		i.setData(Uri.parse(url));
		startActivity(i);
	}

	
	
}
