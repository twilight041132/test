package com.edu.kindergarten.provider;

import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

public abstract class Table {
	
	protected SparseArray<String> matchMap = null;
	
	/**
	 * 创建表
	 * @param SQLiteDatabase
	 * @return boolean
	 */
	public abstract boolean create(SQLiteDatabase db);
	
	/**
	 * 更新表
	 * @param SQLiteDatabase
	 * @param fromVersion
	 * @return boolean
	 */
	public abstract boolean update(SQLiteDatabase db,int fromVersion);
	
	/**
	 * 获取表名
	 * @return table name
	 */
	public abstract String getName();
	
	/**
	 * 设置UriMatcher映射表
	 */
	public abstract void setUriMatchMap();
	
	/**
	 * UriMatcher映射表
	 */
	public SparseArray<String> getUriMatchMap(){
		return matchMap;
	}
	
	/**
	 * 通过match id查找表名
	 */
	public boolean isMatch(int match){
		if (matchMap != null && matchMap.size() > 0) {
			int len = matchMap.size();
			for (int i = 0; i < len; i++) {
				if (match == matchMap.keyAt(i)) {
					return true;
				}
			}
		}
		
		return false;
	}
}
