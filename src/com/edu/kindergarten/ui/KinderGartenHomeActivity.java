package com.edu.kindergarten.ui;

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.edu.kindergarten.R;
import com.edu.kindergarten.provider.LogTable;
import com.edu.kindergarten.provider.MyProvider;

public class KinderGartenHomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinder_garten_home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.kinder_garten_home, menu);
        return true;
    }
    
    public void onClick(View view){
    	//testTable();
    	startActivity(new Intent(this, AccountLoginActivity.class));
    }
    
    public void testTable(){
    	ContentProviderClient mProvider = MyProvider.instance(this);
    	ContentValues values = new ContentValues();
    	values.put(LogTable.TIME, "2015-01-30");
    	values.put(LogTable.MESSAGE, "hello world");
    	try {
    		mProvider.insert(MyProvider.getContentUri(LogTable.TABLE), values);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	try {
			Cursor cursor = mProvider.query(MyProvider.getContentUri(LogTable.TABLE), null, null, null, null);
			while (cursor != null && cursor.moveToNext()) {
				String time = cursor.getString(cursor.getColumnIndex(LogTable.TIME));
				String message = cursor.getString(cursor.getColumnIndex(LogTable.MESSAGE));
				Log.d("KinderGartenHomeActivity","time:" + time + ",message:" + message);
			}
			
			if (cursor != null) {
				cursor.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
    
}
