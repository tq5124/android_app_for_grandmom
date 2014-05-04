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

public class MessageWhoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_who);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// get text
		String[] contacts = getStoredInfo();
		// remove all old buttons and add buttons of contacts
		LinearLayout layout = (LinearLayout) findViewById(R.id.message_contact_list);
		layout.removeAllViews();
		if (contacts == null)
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
			iv.setImageResource(R.drawable.user_white);
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
			bt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
/*					SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			        setting.edit().putString("HEIGHT", height.getText().toString())  
			                    .putString("WEIGHT", weight.getText().toString())  
			                    .commit();  */
					Intent i = new Intent(getBaseContext(),
							MessageTextActivity.class);
					i.putExtra("phone", phone);
					startActivity(i);
				}

			});
			layout.addView(bt);
		}
		// added by fenny
		// add the "send to others" button
		LinearLayout bt = new LinearLayout(this);
		bt.setOrientation(LinearLayout.HORIZONTAL);
		bt.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		ImageView iv = new ImageView(this);
		iv.setImageResource(R.drawable.user_white);
		LinearLayout.LayoutParams iv_pa = new LinearLayout.LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 0.2f);
		iv_pa.setMargins(0, 20, 20, 30);
		iv.setLayoutParams(iv_pa);

		TextView tv = new TextView(this);
		tv.setText("给其他人发短信");
		tv.setTextSize(20f);
		tv.setLayoutParams(new LinearLayout.LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 1.0f));
		bt.addView(iv);
		bt.addView(tv);
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_VIEW); 
				intent.setType("vnd.android-dir/mms-sms");  
				intent.setData(Uri.parse("content://mms-sms"));
		        startActivity(intent);  
			}

		});
		layout.addView(bt);
	}

	// this function returns the information stored by user, every element is
	// like "name:tel"
	private String[] getStoredInfo() {
		String[] ret = null;
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

}
