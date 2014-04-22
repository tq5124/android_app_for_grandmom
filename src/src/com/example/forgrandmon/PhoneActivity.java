package com.example.forgrandmon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class PhoneActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// get text
		// name should be <package>_preferences
		String PREF_NAME = "com.example.forgrandmon_preferences";
		SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);
		// we can't retrieve the android:defaultValue...Don't know why
		String phone = settings.getString("webAccount", "Wrong!");
		// add buttonwebAccount
		LinearLayout layout = (LinearLayout) findViewById(R.id.phone_list);
		Button btTest = new Button(this);
		btTest.setText(phone);
		layout.addView(btTest);
	}

}
