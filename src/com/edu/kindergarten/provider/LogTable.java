package com.edu.kindergarten.provider;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import com.edu.kindergarten.utils.LogUtils;

public class LogTable extends Table {
	private static final String TAG = "LogTable";
	
	public static final String TABLE = "log";
	
	@Override
	public void setUriMatchMap() {
		// TODO Auto-generated method stub
		//…Ë÷√UriMatcher”≥…‰
		if (matchMap == null) {
			matchMap = new SparseArray<String>();
			matchMap.put(MyProvider.matchId.incrementAndGet(), TABLE);
			matchMap.put(MyProvider.matchId.incrementAndGet(), TABLE + "/*");
			
			int len = matchMap.size();
			for (int i = 0; i < len; i++) {
				MyProvider.URI_MATCHER.addURI(MyProvider.AUTHORITY, 
						matchMap.valueAt(i), matchMap.keyAt(i));
			}
		}
	}

	public static final String TIME = "time";
	public static final String MESSAGE = "message";
	
	public LogTable(){
		setUriMatchMap();
	}

	@Override
	public boolean create(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		if (db == null) {
			return false;
		}
		
		db.execSQL("drop table if exists " + TABLE);
		db.execSQL("create table if not exists " + TABLE + "(_id integer primary key," +
				"time text," +
				"message text)");
		LogUtils.d(TAG, "create table:" + TABLE);
		return true;
	}

	@Override
	public boolean update(SQLiteDatabase db,int fromVersion) {
		// TODO Auto-generated method stub
		if (db == null) {
			return false;
		}
		
		if (fromVersion < 3) {
			try {
				db.execSQL("alter table " + TABLE + " add column age integer;");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			LogUtils.d(TAG, "updateDatabase, fromVersion < 3");
		}
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return TABLE;
	}
	
	private static final int DB_LOG_SIZE = 500;
	public static void write(SQLiteDatabase db, String message){
		if (message == null || db == null) {
			return;
		}
		
		try {
			db.execSQL("insert or replace into " + TABLE +
					"(time,message) values (strftime('%Y-%m-%d %H:%M:%f','now','localtime'),?);",
					new String[]{message});
			
			//delete all but the last 500 rows
			db.execSQL("delete from " + TABLE + " where rowid in " +
					"(select rowid from log order by rowid desc limit " + DB_LOG_SIZE + ",-1);");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
