package com.edu.kindergarten.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.edu.kindergarten.utils.LogUtils;
 
public class MyProvider extends ContentProvider {
	private static final String TAG = "MyProvider";
	private DatabaseHelper mDatabaseHelper = null;
	private static ContentProviderClient mProvider;
	private static List<Table> tables = new ArrayList<Table>();
	
	public static final String DATABASE_NAME = "kindergarten.db";
	public static final String AUTHORITY = "kindergarten";
	public static final String CONTENT_AUTHORITY_SLASH = "content://" + AUTHORITY + "/";
	public static AtomicInteger matchId = new AtomicInteger(100);
	public static final UriMatcher URI_MATCHER = 
			new UriMatcher(UriMatcher.NO_MATCH);
	
	static {
		//add table
		tables.add(new LogTable());
		tables.add(new AccountTable());
	}
	
	public MyProvider() {
		// TODO Auto-generated constructor stub
	}
	
	static class DatabaseHelper extends SQLiteOpenHelper {
		
		Context mContext;
		
		public DatabaseHelper(Context context){
			super(context,DATABASE_NAME,null,getDatabaseVersion(context));
			mContext = context;
			setWriteAheadLoggingEnabled(true);
		}
		
		@Override
		public void onCreate(final SQLiteDatabase db){
			Log.d(TAG,"DatabaseHelper.onCreate");
			updateDatabase(mContext, db, 0, getDatabaseVersion(mContext));
		}
		
		@Override
		public void onUpgrade(final SQLiteDatabase db,final int oldV,final int newV){
			Log.d(TAG,"DatabaseHelper.onUpgrade");
			updateDatabase(mContext, db, oldV, newV);
		}
		
		@Override
		public synchronized SQLiteDatabase getWritableDatabase(){
			SQLiteDatabase db = null;
			try{
				db = super.getWritableDatabase();
			}catch (Exception e){
				Log.e(TAG,"failed to open writable database,exception:" + e);
				e.printStackTrace();
			}
			
			return db;
		}
		
		@Override
		public synchronized SQLiteDatabase getReadableDatabase(){
			SQLiteDatabase db = null;
			try{
				db = super.getReadableDatabase();
			}catch (Exception e){
				Log.e(TAG,"failed to open readable database,exception:" + e);
				e.printStackTrace();
			}
			
			return db;
		}
	}
	
	public static ContentProviderClient instance(Context context){
		synchronized (MyProvider.class) {
			if (mProvider == null) {
				mProvider = context.getApplicationContext().getContentResolver().acquireContentProviderClient(AUTHORITY);
			}
		}
		
		return mProvider;
	}
	
	@Override
	public boolean onCreate(){
		Log.d(TAG,"MyProvider.onCreate");
		final Context context = getContext();
		
		synchronized (this) {
			if (mDatabaseHelper == null) {
				mDatabaseHelper = new DatabaseHelper(context);
			}
		}
		
		return true;
	}
	
	private static void updateDatabase(Context context,SQLiteDatabase db, int fromVersion, int toVersion){
		
		int dbVersion = getDatabaseVersion(context);
		Log.d(TAG,"updateDatabase,fromVersion:" + fromVersion + ",toVersion:" + toVersion);
		if (toVersion != dbVersion) {
			Log.e(TAG,"Illegal update request, toVersio:" + toVersion + ",dbVersion:" + dbVersion);
			throw new IllegalArgumentException();
		}else if (fromVersion > toVersion) {
			Log.e(TAG,"Illegal update request,can't downgrade from " + fromVersion + " to " + toVersion);
			throw new IllegalArgumentException();
		}
		
		long startTime = SystemClock.currentThreadTimeMillis();
		
		if (fromVersion < 2) {
			try {
				for (int i = 0; i < tables.size(); i++) {
					tables.get(i).create(db);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		if (fromVersion < 3) {
			try {
				for (int i = 0; i < tables.size(); i++) {
					tables.get(i).update(db,fromVersion);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			Log.d(TAG, "updateDatabase, fromVersion < 3");
		}
		
		long elapsedSeconds = (SystemClock.currentThreadTimeMillis() - startTime)/1000000;
		LogTable.write(db, "database upgraded from " + fromVersion + " to " + toVersion + ",elapsed seconds:" + elapsedSeconds);
		Log.d(TAG, "database upgraded from " + fromVersion + " to " + toVersion + ",elapsed seconds:" + elapsedSeconds);
	}
	
	private String getTable(int match){
		String table = "";
		
		if (tables.size() > 0) {
			for(Table t : tables){
				if (t.isMatch(match)) {
					table = t.getName();
					Log.d(TAG, "getTable,table:" + table);
					break;
				}
			}
		}
		
		return table;
	}
	
	@Override
	public Cursor query(Uri uri,String[] projection,String selection,
			String[] selectionArgs,String sortOrder){
		Log.d(TAG,"MyProvider.query, uri:" + uri);
		int match = URI_MATCHER.match(uri);
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		if(db == null)return null;
		
		String table = getTable(match);
		
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(table);
		
		//query
		//c = db.query(table, projection, selection, selectionArgs, null, null, sortOrder);
		Cursor c = qb.query(db,projection, selection, selectionArgs, null, null, sortOrder);
		
		return c;
	}
	
	@Override
	public int update(Uri uri,ContentValues values,String selection,String[] selectionArgs){
		Log.d(TAG,"MyProvider.update, uri:" + uri);
		int count = 0;
		int match = URI_MATCHER.match(uri);
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		if(db == null)return 0;
		
		String table = getTable(match);
		
		//update
		count = db.update(table, values, selection, selectionArgs);
		
		String notifyChange = uri.getQueryParameter("notifyChange");
		if("disable".equals(notifyChange)){
			return count;
		}
		
		if(count > 0 && !db.inTransaction()){
			getContext().getContentResolver().notifyChange(uri, null);
		}
		
		return count;
	}
	
	@Override
	public Uri insert(Uri uri,ContentValues initialValues){
		Uri newUri = null;
		long rowId = 0;
		int match = URI_MATCHER.match(uri);
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		if(db == null)return null;
		
		Log.d(TAG,"MyProvider.insert, uri:" + uri + ",match:" + match + ",values:"+initialValues);
		
		String table = getTable(match);
		
		//insert
		rowId = db.insert(table,null,initialValues); //TODO
		newUri = ContentUris.withAppendedId(uri, rowId);
		
		String notifyChange = uri.getQueryParameter("notifyChange");
		if("disable".equals(notifyChange)){
			return newUri;
		}
		
		if(newUri != null){
			getContext().getContentResolver().notifyChange(newUri, null);
		}
		
		return newUri;
	}
	
	@Override
	public int delete(Uri uri,String selection,String[] selectionArgs){
		Log.d(TAG,"MyProvider.delete, uri:" + uri);
		int count = 0;
		int match = URI_MATCHER.match(uri);
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		if(db == null)return 0;
		
		String table = getTable(match);
		
		//delete
		count = db.delete(table, selection, selectionArgs);
		String notifyChange = uri.getQueryParameter("notifyChange");
		if("disable".equals(notifyChange)){
			return count;
		}
		
		if(count > 0 && !db.inTransaction()){
			Uri notifyUri = Uri.parse(CONTENT_AUTHORITY_SLASH);
			getContext().getContentResolver().notifyChange(notifyUri, null);
		}

		return count;
	}
	
	@Override
	public String getType(Uri uri){
		switch(URI_MATCHER.match(uri)){
		default:
			break;
		}
		
		return null;
	}
	
	/**
	 * is the specific row already in table
	 * @param context
	 * @param uri
	 * @param selection
	 * @param selectionArgs
	 * @return
	 */
	public static boolean existInTable(Context context,Uri uri,String selection, String[] selectionArgs){
		boolean ret = false;
		ContentProviderClient provider = MyProvider.instance(context);
		Cursor cursor = null;
		
		try {
			cursor = provider.query(uri, null, selection, selectionArgs, null);
			if (cursor != null && cursor.getCount() > 0) {
				ret = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if (cursor != null) {
				cursor.close();
			}
		}
		
		return ret;
	}
	
	public static int getDatabaseVersion(Context context){
		try{
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		}catch(NameNotFoundException e){
			throw new RuntimeException("couldn't get version code for " + context);
		}
	}
	
	public static Uri getContentUri(String table){
		if (TextUtils.isEmpty(table)) {
			LogUtils.e(TAG,"table is empty!!!");
			throw new IllegalArgumentException();
		}
		return Uri.parse(CONTENT_AUTHORITY_SLASH + table);
	}
}
