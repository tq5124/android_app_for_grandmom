package com.example.forgrandmon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
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
		String[] contacts = getStoredInfo();
		// remove all old buttons and add buttons of contacts
		LinearLayout layout = (LinearLayout) findViewById(R.id.phone_list);
		layout.removeAllViews();
		for (String info : contacts) {
			String[] namePhone = info.split(":");
			String name = namePhone[0];
			final String phone = namePhone[1];
			Button bt = new Button(this);
			bt.setText(name);
			bt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Uri smsToUri = Uri.parse("smsto:"+phone);
					Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
					intent.putExtra("sms_body", "hello world");
					startActivity(intent);
				}

			});
			layout.addView(bt);
		}
	}

	// this function returns the information stored by user
	// every element in return array is like "name:tel"
	private String[] getStoredInfo() {
		String[] ret;
		// TO DO START
		// name should be <package>_preferences
		// String PREF_NAME = "com.example.forgrandmon_preferences";
		// SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);
		// we can't retrieve the android:defaultValue...Don't know why
		ret = new String[] { "wuhao:15121197268", "fengzhe:13585735146" };
		// TO DO END
		return ret;
	}

}
