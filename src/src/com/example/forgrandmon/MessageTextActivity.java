package com.example.forgrandmon;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

public class MessageTextActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_text);
	}

	// all the rest is for test
	public void onTest1Clicked(View v) {
		SmsManager smsManager = SmsManager.getDefault();  
	    PendingIntent pintent = PendingIntent.getBroadcast(MessageTextActivity.this, 0, new Intent(), 0);  
	    smsManager.sendTextMessage("13585735146", null, "今天回家吃饭吗？", pintent, null);  
	    Toast.makeText(this, "已发送", Toast.LENGTH_SHORT);
	}
	
	public void onTest2Clicked(View v) {
		SmsManager smsManager = SmsManager.getDefault();  
	    PendingIntent pintent = PendingIntent.getBroadcast(MessageTextActivity.this, 0, new Intent(), 0);  
	    smsManager.sendTextMessage("13585735146", null, "今天回家吃饭吗？", pintent, null);
	    Toast.makeText(this, "已发送", Toast.LENGTH_SHORT);
	}
	
	public void onTest3Clicked(View v) {
		// TO DO : now it is my cell phone number
		Uri uri = Uri.parse("smsto:" + "13585735146");
		Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
		sendIntent.putExtra("sms_body", "");
		startActivity(sendIntent);
	}
}
