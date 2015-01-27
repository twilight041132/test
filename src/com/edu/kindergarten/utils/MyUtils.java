package com.edu.kindergarten.utils;

import java.security.MessageDigest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.util.Log;

public class MyUtils {
	private static final String TAG = "MyUtils";

	/**
	 * 检查字符串是否为手机号
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){     
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");     
        Matcher m = p.matcher(mobiles);  
        boolean isMobile = m.matches();
        LogUtils.d(TAG, "isMobileNO, mobile:" + mobiles + ", isMobile:" + isMobile);
        return isMobile;     
    } 
   
	/**
	 * 检查字符串是否为邮箱
	 * @param email
	 * @return
	 */
    public static boolean isEmail(String email){
    	Log.d(TAG, "isEmail, email:" + email);
    	if (email.indexOf("@") <= 0) {
			return false;
		}
    	String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(email);   
        boolean isEmail = m.matches();
        LogUtils.d(TAG, "isEmail, email:" + email + ", isEmail:" + isEmail);
        return isEmail;     
    } 
    
    /**
     * 获取当前activity名称
     * @param context
     * @return
     */
    public static String getTopActivity(Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

		if (runningTaskInfos != null)
			return runningTaskInfos.get(0).topActivity.getClassName();
		else
			return "";
	}
	
    /**
     * 获取sha1摘要
     * @param str
     * @return
     */
	public static String sha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes());
			
			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}
}
