package com.example.forgrandmon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class PhoneActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone);
		// add buttons to it
		LinearLayout layout = (LinearLayout) findViewById(R.id.phone_list);
		Button btTest = new Button(this);
		btTest.setText("added button");
		layout.addView(btTest);
	}

}
