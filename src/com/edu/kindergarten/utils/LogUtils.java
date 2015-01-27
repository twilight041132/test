package com.edu.kindergarten.utils;

import android.util.Log;

/**
 * Logͳһ������
 * 
 */
public class LogUtils
{

	private LogUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isDebug = true;// �Ƿ���Ҫ��ӡlog��������application��onCreate���������ʼ��
	private static final String TAG = "LogUtils";

	// �����ĸ���Ĭ��tag�ĺ���
	public static void i(String msg)
	{
		if (isDebug)
			Log.i(TAG, msg);
	}

	public static void d(String msg)
	{
		if (isDebug)
			Log.d(TAG, msg);
	}
	
	public static void w(String msg)
	{
		if (isDebug)
			Log.w(TAG, msg);
	}

	public static void e(String msg)
	{
		if (isDebug)
			Log.e(TAG, msg);
	}

	public static void v(String msg)
	{
		if (isDebug)
			Log.v(TAG, msg);
	}

	// �����Ǵ����Զ���tag�ĺ���
	public static void i(String tag, String msg)
	{
		if (isDebug)
			Log.i(tag, msg);
		
	}

	public static void d(String tag, String msg)
	{
		if (isDebug)
			Log.d(tag, msg);
	}
	
	public static void w(String tag, String msg)
	{
		if (isDebug)
			Log.w(tag, msg);
	}

	public static void e(String tag, String msg)
	{
		if (isDebug)
			Log.e(tag, msg);
	}

	public static void v(String tag, String msg)
	{
		if (isDebug)
			Log.v(tag, msg);
	}
}