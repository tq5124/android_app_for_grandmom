package com.example.forgrandmon;

import com.example.forgrandmon.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings;

public class MainActivity extends Activity {
	
	//BY WJ
	private LocationManager locationManager;
	//BY WJ
	GPSService.GPSBinder binder;
	//BY WJ
	private ServiceConnection connection = new ServiceConnection()
	{
		@Override
		public void onServiceConnected(ComponentName name,IBinder service){
			MainActivity.this.binder = (GPSService.GPSBinder)service;
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name){
		
		}
	};
	
	//BY WJ
	public void GPSInitialize() {
		this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (this.locationManager != null && this.locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Intent intent = new Intent(MainActivity.this, GPSService.class);
			bindService(intent,this.connection,Service.BIND_AUTO_CREATE);
        } else {
        	Toast.makeText(this, "请开启GPS功能！", Toast.LENGTH_SHORT).show();
        	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent,0);
		}
	}
	
	//BY WJ
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		this.GPSInitialize();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		if(manager.getSimState() == TelephonyManager.SIM_STATE_READY){
			if(NetworkCheck.isNetworkAvailable(this)){
				if(NetworkCheck.is3G(this)){
					Toast.makeText(this, "现在您使用的是3G流量！", Toast.LENGTH_SHORT).show();
				}
				if(NetworkCheck.isWifi(this)){
					Toast.makeText(this, "现在您使用的是WIFI！", Toast.LENGTH_SHORT).show();
				}
			} else {
				if(!NetworkCheck.isWifiEnabled(this)){
					Toast.makeText(this, "请开启WIFI功能！", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
	            	startActivity(intent);
				}
			}
			this.GPSInitialize();
		} else {
			Toast.makeText(this, "请插入SIM卡！", Toast.LENGTH_SHORT).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i=new Intent(getBaseContext(), SettingsPreference.class) ;
			startActivity(i) ;
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	

	public static class Buttom_share extends Fragment {

		public Buttom_share() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_bottom, container,
					false);
			return rootView;
		}
	}
	
	// callback functions
	public void onPhoneClicked(View v)
	{
		Intent intent = new Intent();
        //intent.putExtra("name", "hello world");
        intent.setClass(v.getContext(), PhoneActivity.class);
        startActivity(intent);
	}
	
	public void onMessageClicked(View v)
	{
		Intent intent = new Intent();
        intent.setClass(v.getContext(), MessageActivity.class);
        startActivity(intent);
	}
	
	public void onPhotoClicked(View v)
	{
		Intent intent = new Intent();
        intent.setClass(v.getContext(), PhotoActivity.class);
        startActivity(intent);
	}
	
	public void onSettingClicked(View v)
	{
		Intent intent = new Intent();
        intent.setClass(v.getContext(), SettingsPreference.class);
        startActivity(intent);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// set the date
		Time t = new Time();
		t.setToNow();
		String date = new String();
		date += t.year+"年"+(t.month+1)+"月"+t.monthDay+"日";
		TextView tv = (TextView)findViewById(R.id.main_date);
		tv.setText(date);
	}
}
