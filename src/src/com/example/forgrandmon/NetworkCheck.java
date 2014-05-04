package com.example.forgrandmon;

import java.util.List;  

import android.content.Context;  
import android.location.LocationManager;  
import android.net.ConnectivityManager;  
import android.net.NetworkInfo;  
import android.telephony.TelephonyManager;  
import android.widget.Toast;
  
public class NetworkCheck {  
  
    /** 
     * 网络是否可用 
     *  
     * @param activity 
     * @return 
     */  
    public static boolean isNetworkAvailable(Context context) {
    	try{
	        ConnectivityManager connectivity = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        if (connectivity == null) {  
	        } else {  
	            NetworkInfo[] info = connectivity.getAllNetworkInfo();  
	            if (info != null) {  
	                for (int i = 0; i < info.length; i++) {  
	                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {  
	                        return true;  
	                    }  
	                }  
	            }  
	        }  
	        return false;  
    	} catch(Exception e) {
    		Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
    		return false;
    	}
    }
  
    /** 
     * wifi是否打开 
     */  
    public static boolean isWifiEnabled(Context context) { 
    	try{
	        ConnectivityManager mgrConn = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        TelephonyManager mgrTel = (TelephonyManager) context  
	                .getSystemService(Context.TELEPHONY_SERVICE);  
	        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn  
	                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel  
	                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS); 
    	} catch(Exception e) {
    		Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
    		return false;
    	}
    }  
  
    /** 
     * 判断当前网络是否是wifi网络 
     * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) { //判断3G网 
     *  
     * @param context 
     * @return boolean 
     */  
    public static boolean isWifi(Context context) {
    	try{
	        ConnectivityManager connectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();  
	        if (activeNetInfo != null  
	                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {  
	            return true;  
	        }  
	        return false;
        } catch(Exception e) {
    		Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
    		return false;
    	}
    }  
  
    /** 
     * 判断当前网络是否是3G网络 
     *  
     * @param context 
     * @return boolean 
     */  
    public static boolean is3G(Context context) {
    	try{
	        ConnectivityManager connectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();  
	        if (activeNetInfo != null  
	                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {  
	            return true;  
	        }  
	        return false;
    	} catch(Exception e) {
    		Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
    		return false;
    	}
    }  
}  