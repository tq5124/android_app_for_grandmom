package com.example.forgrandmon;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
		if(contacts==null)
			return;
		for (String info : contacts) {
			String[] namePhone = info.split(":");
			String name = namePhone[0];
			final String phone = namePhone[1];

			// rewrite by tq
			LinearLayout bt = new LinearLayout(this);
			bt.setOrientation(LinearLayout.HORIZONTAL);
			bt.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			ImageView iv = new ImageView(this);
			iv.setImageResource(R.drawable.user);
			LinearLayout.LayoutParams iv_pa = new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 0.2f);
			iv_pa.setMargins(0, 20, 20, 30);
			iv.setLayoutParams(iv_pa);

			TextView tv = new TextView(this);
			tv.setText(name + "(" + phone + ")");
			tv.setTextSize(20f);
			tv.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 1.0f));

			bt.addView(iv);
			bt.addView(tv);

			// Button bt = new Button(this);
			// bt.setText(name);
			bt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					/*
					 * Uri smsToUri = Uri.parse("smsto:" + phone); Intent intent
					 * = new Intent(Intent.ACTION_SENDTO, smsToUri);
					 * intent.putExtra("sms_body", "hello world");
					 * startActivity(intent);
					 */
					Intent intent = new Intent(Intent.ACTION_CALL, Uri
							.parse("tel:" + phone));
					startActivity(intent);
				}

			});
			layout.addView(bt);
		}
	}

	// this function returns the information stored by user, every element is like "name:tel"
	private String[] getStoredInfo() {
		String[] ret = null;
		// these codes are deprecated
		// name should be <package>_preferences
		// String PREF_NAME = "com.example.forgrandmon_preferences";
		// SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);
		// we can't retrieve the android:defaultValue...Don't know why
		ContactModel contacts = new ContactModel(this);
		ArrayList<Contact> contactLists = contacts.GetDBContacts();
		// there is no contacts
		if (contactLists.size() == 0) {
			return ret;
		}
		int sz = contactLists.size();
		ret = new String[sz];
		int i = 0;
		for (Contact contact : contactLists) {
			ret[i++] = contact.name + ":" + contact.number;
		}
		return ret;
	}

	// callback function of phone_call_other_btn
	public void onBtCallOtherClicked(View v) {
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
		startActivity(intent);
	}

}
