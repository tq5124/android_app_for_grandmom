package com.example.forgrandmon;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
	}

	public void onSendClicked(View v) {
		Intent intent = new Intent();
		intent.setClass(v.getContext(), MessageWhoActivity.class);
		startActivity(intent);
	}

	public void onReceiveClicked(View v) {
		Intent intent = new Intent();
		intent.setClassName("com.android.mms","com.android.mms.ui.ConversationList");
		startActivity(intent); 
	}
}
