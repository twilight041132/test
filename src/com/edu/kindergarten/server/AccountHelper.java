package com.edu.kindergarten.server;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.edu.kindergarten.entity.AccountEntity;
import com.edu.kindergarten.provider.AccountTable;
import com.edu.kindergarten.provider.MyProvider;

public class AccountHelper {
	
	public static boolean saveOrUpdateAccount(Context context,AccountEntity entity){
		boolean ret = false;
		ContentProviderClient provider = MyProvider.instance(context);
		Uri uri = MyProvider.getContentUri(AccountTable.TABLE);
    	ContentValues values = new ContentValues();
    	values.put(AccountTable.USERNAME, entity.userName);
    	values.put(AccountTable.PASSWORD, entity.password);
    	
    	String selection = AccountTable.USERNAME + "=?";
    	String[] selectionArgs = new String[]{entity.userName};
    	
    	try {
    		if (MyProvider.existInTable(context,uri,selection,selectionArgs)) {
    			provider.update(uri, values, selection, selectionArgs);
    		}else {
    			provider.insert(uri, values);
			}
    		
    		ret = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	return ret;
	}
	
	public static String getPassword(Context context,String userName){
		String password = null;
		ContentProviderClient provider = MyProvider.instance(context);
		Uri uri = MyProvider.getContentUri(AccountTable.TABLE);
		String selection = AccountTable.USERNAME + "=?";
    	String[] selectionArgs = new String[]{userName};
		Cursor cursor = null;
		
		try {
			cursor = provider.query(uri, null, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				password = cursor.getString(cursor.getColumnIndex(AccountTable.PASSWORD));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if (cursor != null) {
				cursor.close();
			}
		}
	
		return password;
	}
}
