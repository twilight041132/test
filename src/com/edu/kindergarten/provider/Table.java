package com.edu.kindergarten.provider;

import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

public abstract class Table {
	
	protected SparseArray<String> matchMap = null;
	
	/**
	 * ������
	 * @param SQLiteDatabase
	 * @return boolean
	 */
	public abstract boolean create(SQLiteDatabase db);
	
	/**
	 * ���±�
	 * @param SQLiteDatabase
	 * @param fromVersion
	 * @return boolean
	 */
	public abstract boolean update(SQLiteDatabase db,int fromVersion);
	
	/**
	 * ��ȡ����
	 * @return table name
	 */
	public abstract String getName();
	
	/**
	 * ����UriMatcherӳ���
	 */
	public abstract void setUriMatchMap();
	
	/**
	 * UriMatcherӳ���
	 */
	public SparseArray<String> getUriMatchMap(){
		return matchMap;
	}
	
	/**
	 * ͨ��match id���ұ���
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
