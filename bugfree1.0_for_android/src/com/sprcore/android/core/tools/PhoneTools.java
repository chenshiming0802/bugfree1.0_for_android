package com.sprcore.android.core.tools;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class PhoneTools {
	
	/**
	 * @param context
	 * @return
	 */
	public static String getPhoneIMEI(Context context) {
		String serialNum = "";
		TelephonyManager telManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (telManager.getSimSerialNumber() != null) {
			serialNum = "android" + telManager.getSimSerialNumber();
		} else if (telManager.getSubscriberId() != null) {
			serialNum = "android" + telManager.getSubscriberId();
		} else {
			serialNum = "android" + telManager.getDeviceId();
		}
		return serialNum;
	}

    
	/**
	 * @return
	 */
	public static boolean hasSdCard(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * @return
	 */
	public static long getAvailaleSize(){

		File path = Environment.getExternalStorageDirectory(); 
		StatFs stat = new StatFs(path.getPath()); 
		long blockSize = stat.getBlockSize(); 
		long availableBlocks = stat.getAvailableBlocks();

		return availableBlocks * blockSize/1024 /1024; 
	}
	
	/**
	 * @return
	 */
	public static long getAllSize(){

		File path = Environment.getExternalStorageDirectory(); 

		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getBlockCount();
		return availableBlocks * blockSize/1024 /1024; 
	}
	
	/**
	 * @return
	 */
	public static boolean checkNetWork(Context context){
		ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		if (null == networkInfo){
			return false;
		} else {
			boolean available = networkInfo.isAvailable();
			if (!available){
				return false;
				}
			}
		return true;
	}
	
	public static String getCurDateStr(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	/**
	 * @param context
	 * @return
	 */
	public static boolean isAirplaneMode(Context context){
		return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) ==1? false:true;
	}
	
	/**
	 * @param context
	 * @return
	 */
	public static String getDeviceID(Context context){
		String macAddress = "";
		macAddress = android.provider.Settings.System.getString(context.getContentResolver(), android.provider.Settings.System.ANDROID_ID);
		return macAddress;
	}
	
	/**ַ
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context) {
		WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	} 
	
	/**
	 * @param context
	 * @return
	 */
	public static String getPhoneNo(Context context){
		TelephonyManager phoneMgr=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		return phoneMgr.getLine1Number(); 
	}
	/**
	 * @param value
	 * @return
	 */
	public static String format(double value){
		String ret = "";
		NumberFormat formater = new DecimalFormat("###,###.##");
		ret = formater.format(value);
		return ret;
	}
	public static String add(String v1,String v2) { 
	       BigDecimal b1 = new BigDecimal(v1); 
	       BigDecimal b2 = new BigDecimal(v2); 
	       
	       return b1.add(b2).toString(); 
	  } 
	/**
	 * @param context
	 * @return
	 * @throws Exception
	 */
    public static String getVersionName(Context context) throws Exception  
    {  
            PackageManager packageManager = context.getPackageManager();  
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);  
            String version = packInfo.versionName;  
            return version;  
    }  
    /** 
     * @param context 
     * @param className 
     * @return 
     */
    public static boolean isServiceRunning(Context mContext,String className) { 
        boolean isRunning = false; 
        ActivityManager activityManager = (ActivityManager) 
        mContext.getSystemService(Context.ACTIVITY_SERVICE);  
        List<ActivityManager.RunningServiceInfo> serviceList  = activityManager.getRunningServices(100); 
       if (!(serviceList.size()>0)) { 
            return false; 
        } 
        for (int i=0; i<serviceList.size(); i++) { 
        	String name=serviceList.get(i).service.getClassName();
            if (name.equals(className)) { 
                isRunning = true; 
                break; 
            } 
        } 
        return isRunning; 
    }
    /**
     */
    public static boolean isWifiNet(Context context){
    	ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	 NetworkInfo info = connectMgr.getActiveNetworkInfo();
    	 if(info!=null && info.getType()==ConnectivityManager.TYPE_WIFI){
    		 return true;
    	 }
    	 return false;
    }

}
