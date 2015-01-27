package com.edu.kindergarten.provider;

import com.edu.kindergarten.utils.LogUtils;

import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

public class AccountTable extends Table {
	private static final String TAG = "AccountTable";
	
	public static final String TABLE = "account";
	
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	@Override
	public boolean create(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists " + TABLE);
		db.execSQL("create table if not exists " + TABLE + "(_id integer primary key," +
				"username text," +
				"password text)");
		LogUtils.d(TAG, "create table:" + TABLE);
		return true;
	}

	@Override
	public void setUriMatchMap() {
		// TODO Auto-generated method stub
		//…Ë÷√UriMatcher”≥…‰
		if (matchMap == null) {
			matchMap = new SparseArray<String>();
			matchMap.put(MyProvider.matchId.incrementAndGet(), TABLE);
			
			int len = matchMap.size();
			for (int i = 0; i < len; i++) {
				MyProvider.URI_MATCHER.addURI(MyProvider.AUTHORITY, 
						matchMap.valueAt(i), matchMap.keyAt(i));
			}
		}
	}

	@Override
	public boolean update(SQLiteDatabase db, int fromVersion) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return TABLE;
	}

}
