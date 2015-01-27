package com.edu.kindergarten.app;


import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.util.Log;

import com.edu.kindergarten.config.Constants;
import com.edu.kindergarten.config.ImageLoaderConfig;

public class MyApplication extends Application {
	private static final String TAG  = "MyApplication";
	
	public static int localVersion = 1;
	public static int serverVersion = 1;
	public static String updateUrl = "";
	public static String updateDesc = "";
	public static String versionName = "1.0.0";
	public static String downloadDir = "kindergarten/";

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(TAG, "zxj - onCreate");
		ImageLoaderConfig.initImageLoader(this, Constants.BASE_IMAGE_CACHE);
		
		try {
			PackageInfo packageInfo = getApplicationContext()
					.getPackageManager().getPackageInfo(getPackageName(), 0);
			localVersion = packageInfo.versionCode;
			versionName = packageInfo.versionName;
			
			//TODO: get server version
			//serverVersion = ?
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		Log.d(TAG, "zxj - onLowMemory");
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		Log.d(TAG, "zxj - onTerminate");
	}
	
	/*public synchronized void setActivity(Activity activity){
		Log.d(TAG, "zxj - setActivity, activity:" + activity);
		mActivity = activity;
	}
	
	public synchronized Activity getActivity(){
		Log.d(TAG, "zxj - getActivity, activity:" + mActivity);
		return mActivity;
	}*/
}
