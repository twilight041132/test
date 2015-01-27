package com.edu.kindergarten.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.edu.kindergarten.R;

public class UiUtils {

	/**
	 * Toastͳһ������
	 * 
	 */

	/**
	 * ��ʱ����ʾToast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShortToast(Context context, CharSequence message)
	{
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * ��ʱ����ʾToast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShortToast(Context context, int message)
	{
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * ��ʱ����ʾToast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLongToast(Context context, CharSequence message)
	{
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * ��ʱ����ʾToast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLongToast(Context context, int message)
	{
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * �Զ�����ʾToastʱ��
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void showToast(Context context, CharSequence message, int duration)
	{
		Toast.makeText(context, message, duration).show();
	}

	/**
	 * �Զ�����ʾToastʱ��
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void showToast(Context context, int message, int duration)
	{
		Toast.makeText(context, message, duration).show();
	}
	
	
	/**
	 * Dialogͳһ������
	 * 
	 */
	/**
	 * ȷ�϶Ի���
	 * 
	 * @param context
	 * @param msg
	 * @param DialogInterface
	 */
	public static void showConfirmDialog(Context context,String msg,DialogInterface.OnClickListener listener){
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(msg);
			builder.setPositiveButton(R.string.ok, listener);
			AlertDialog d = builder.create();
			d.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ȷ�϶Ի���
	 * 
	 * @param context
	 * @param strId
	 * @param DialogInterface
	 */
	public static void showConfirmDialog(Context context,int strId,DialogInterface.OnClickListener listener){
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(strId);
			builder.setPositiveButton(R.string.ok, listener);
			AlertDialog d = builder.create();
			d.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * Yes/No�Ի���
	 * 
	 * @param context
	 * @param msg
	 * @param DialogInterface
	 */
	public static void showYesNoDialog(Context context,String msg,DialogInterface.OnClickListener yesListener,DialogInterface.OnClickListener noListener){
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(msg);
			builder.setPositiveButton(R.string.dialog_yes, yesListener);
			builder.setNegativeButton(R.string.dialog_no, noListener);
			AlertDialog d = builder.create();
			d.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * Yes/No�Ի���
	 * 
	 * @param context
	 * @param strId
	 * @param DialogInterface
	 */
	public static void showYesNoDialog(Context context,int strId,DialogInterface.OnClickListener yesListener,DialogInterface.OnClickListener noListener){
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(strId);
			builder.setPositiveButton(R.string.dialog_yes, yesListener);
			builder.setNegativeButton(R.string.dialog_no, noListener);
			AlertDialog d = builder.create();
			d.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * Yes/No�Ի���
	 * 
	 * @param context
	 * @param msg
	 * @param DialogInterface
	 */
	public static void showYesNoDialog(Context context,String msg,DialogInterface.OnClickListener dClickListener){
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(msg);
			builder.setPositiveButton(R.string.dialog_yes, dClickListener);
			builder.setNegativeButton(R.string.dialog_no, getGeneralClickListener());
			AlertDialog d = builder.create();
			d.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * Yes/No�Ի���
	 * 
	 * @param context
	 * @param strId
	 * @param DialogInterface
	 */
	public static void showYesNoDialog(Context context,int strId,DialogInterface.OnClickListener dClickListener){
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(strId);
			builder.setPositiveButton(R.string.dialog_yes, dClickListener);
			builder.setNegativeButton(R.string.dialog_no, getGeneralClickListener());
			AlertDialog d = builder.create();
			d.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * getGeneralClickListener
	 * 
	 */
	public static DialogInterface.OnClickListener getGeneralClickListener(){
		return new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
				
			}
		}; 
	}
	
	/**
	 * getFinishClickListener
	 * 
	 * @param Activity
	 */
	public static DialogInterface.OnClickListener getFinishClickListener(final Activity activity){
		return new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
				activity.finish();
			}
		}; 
	}
}

