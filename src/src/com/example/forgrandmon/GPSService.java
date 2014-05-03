package com.example.forgrandmon;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class GPSService extends Service{

	private LocationManager locationManager;
	private Criteria criteria = new Criteria();
	private double latitude = 0;
	private double longitude = 0;
	private String IMEI = "";
	private String TEL = "";
	private String DEVICE = "";
    private GPSBinder binder = new GPSBinder();
    public class GPSBinder extends Binder{
        void setFrequency(int minute){
        	GPSService.this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,60000*minute,0,GPSService.this.locationListener ,Looper.myLooper());
        }
    }
    
    private LocationListener locationListener = new LocationListener() {
        
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            
        }
        
        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
            
        }
        
        // Provider被disable时触发此函数，比如GPS被关闭 
        @Override
        public void onProviderDisabled(String provider) {
            
        }
        
        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发 
        @Override
        public void onLocationChanged(Location location) {
        	if(location != null){
            	GPSService.this.latitude = location.getLatitude();
            	GPSService.this.longitude = location.getLongitude();
            	new Thread(){  
                    @Override  
                    public void run() {
                        // TODO Auto-generated method stub  
                        super.run();  
                        try{
    		            	URI url = new URI("http://59.78.16.94/PHP/website/index.php/device/reply_device_locations");
    		            	HttpPost httpPost = new HttpPost(url); 
    		                // 设置HTTP POST请求参数必须用NameValuePair对象 
    		                List<NameValuePair> params = new ArrayList<NameValuePair>();
    		                params.add(new BasicNameValuePair("IMEI", GPSService.this.IMEI));
    		                params.add(new BasicNameValuePair("phoneNumber",String.valueOf(GPSService.this.TEL)));
    		                params.add(new BasicNameValuePair("phoneType",String.valueOf(GPSService.this.DEVICE)));
    		                params.add(new BasicNameValuePair("latitude", String.valueOf(GPSService.this.latitude)));
    		                params.add(new BasicNameValuePair("longitude",String.valueOf(GPSService.this.longitude)));
    		                params.add(new BasicNameValuePair("username","admin"));
    		                params.add(new BasicNameValuePair("password","admin"));
    		            	// 绑定到请求 Entry 
    		                httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
    		            	// 发送请求
    		                HttpClient client = new DefaultHttpClient();
    		            	HttpResponse httpResponse = client.execute(httpPost);
    	            	} catch(Exception e){
    	            		
    	            		//Toast.makeText(GPSService.this, e.toString(), Toast.LENGTH_SHORT).show();
    	            	}
                    }  
                }.start();
        	}
        }
    };
	
	@Override
	public void onCreate(){
		super.onCreate();
    	TelephonyManager telephonyManager= (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
    	this.IMEI = telephonyManager.getDeviceId();
    	this.TEL = telephonyManager.getLine1Number();
    	this.DEVICE = telephonyManager.getDeviceId();
		this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 获得最好的定位效果
		this.criteria.setAccuracy(Criteria.ACCURACY_FINE);			//设置为最大精度
		this.criteria.setAltitudeRequired(false);					//不获取海拔信息
		this.criteria.setBearingRequired(false);					//不获取方位信息
		this.criteria.setCostAllowed(false);						//不允许付费
		this.criteria.setPowerRequirement(Criteria.POWER_LOW);		//使用省电模式
		try{
			// 获得当前的位置提供者
		    //String provider = this.locationManager.getBestProvider(this.criteria,true);
		    // 获得当前的位置
		    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		    if(location != null){
			    Geocoder gc = new Geocoder(this);
			    //根据经纬度获得地址信息
			    this.latitude = location.getLatitude();
			    this.longitude = location.getLongitude();
			    List<Address> addresses = gc.getFromLocation(this.latitude, this.longitude, 1);
			    if (addresses.size() > 0){
			    	   //获取address类的成员信息
			    	   String msg = "";     
				       msg += "地址：" + addresses.get(0).getAddressLine(0)+ "\n";     
				       msg += "国家：" + addresses.get(0).getCountryName()+ "\n";     
				       msg += "地区：" + addresses.get(0).getLocality() + "\n";     
				       msg += "详细：" + addresses.get(0).getFeatureName();
				       Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				}
		    }
		} catch (Exception e){
			
		}
        //this.locationManager.requestLocationUpdates(provider,600,5,locationListener ,Looper.myLooper());
	    this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,60000,0,this.locationListener ,Looper.myLooper());
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public void onStart(Intent intent,int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return this.binder;
	}
	
}
