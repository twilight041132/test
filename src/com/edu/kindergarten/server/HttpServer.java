package com.edu.kindergarten.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;

import com.edu.kindergarten.config.Constants;
import com.edu.kindergarten.entity.AccountEntity;
import com.edu.kindergarten.provider.AccountTable;
import com.edu.kindergarten.utils.JsonUtils;
import com.edu.kindergarten.utils.LogUtils;

public class HttpServer {
	private static final String TAG = "HttpServer";
	
	public static final String KEY_STATUS = "status";
	public static final String KEY_ERR_CODE = "errcode";
	public static final String KEY_ERR_MSG = "errmsg";

	public static AccountEntity login(AccountEntity from){
		AccountEntity to = new AccountEntity();
		String url = Constants.URL_LOGIN;
		boolean ok = false;
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(AccountTable.USERNAME, from.userName));
			params.add(new BasicNameValuePair(AccountTable.PASSWORD, from.password));

			LogUtils.d(TAG,"NetworkServer requestAccount,url:" + url +
					",user name:" + from.userName + 
					",password:" + from.password);
			
			JSONObject json = JsonUtils.getJSON(url,params);
			LogUtils.d(TAG,"HttpServer,login, json:" + json);
			setAccountEntity(to,json);
			ok = true;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "zxj,requestAccount," + e.getMessage());
			e.printStackTrace();
		}
		
		Log.d(TAG,"login, is network ok? " + ok);
		if (!ok) {
			to.status = Constants.NETWORK_ERROR;
		}
		
		return to;
	}
	
	private static void setAccountEntity(AccountEntity entity,JSONObject json){
		
		try {
			entity.status = json.getInt(KEY_STATUS);
			entity.userName = json.getString(AccountTable.USERNAME);
			entity.password = json.getString(AccountTable.PASSWORD);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
