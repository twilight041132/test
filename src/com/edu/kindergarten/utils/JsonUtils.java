package com.edu.kindergarten.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * @author frankiewei.
 * Json封装的工具类.
 */
public class JsonUtils {
	
	private static final String TAG = "JsonUtils";
	public static final int HTTP_GET = 100;
	public static final int HTTP_POST = HTTP_GET + 1;
	
	/**
	 * 获取json内容
	 * @param  url
	 * @return JSONArray
	 * @throws JSONException 
	 * @throws ConnectionException 
	 */
	public static JSONObject getJSON(String url) 
			throws JSONException, Exception {
		return new JSONObject(sendRequest(url, HTTP_GET, null));
	}
	
	/**
	 * 获取json内容
	 * @param  url
	 * @return JSONArray
	 * @throws JSONException 
	 * @throws ConnectionException 
	 */
	public static JSONArray getJSONArray(String url) 
			throws JSONException, Exception {
		return new JSONArray(sendRequest(url, HTTP_GET, null));
	}
	
	/**
	 * 获取json内容
	 * @param  url
	 * @param int requestMethod
	 * @param List<NameValuePair> params
	 * @return JSONArray
	 * @throws JSONException 
	 * @throws ConnectionException 
	 */
	public static JSONObject getJSON(String url, List<NameValuePair> params) 
			throws JSONException, Exception {
		return new JSONObject(sendRequest(url, HTTP_POST, params));
	}
	
	/**
	 * 向api发送请求，返回从后台取得的信息。
	 * 
	 * @param url
	 * @param requestMethod
	 * @return String
	 */
	protected static String sendRequest(String url, int requestMethod,List<NameValuePair> params) 
			throws Exception {
		//return sendRequest(url, requestMethod, params, new DefaultHttpClient(new BasicHttpParams()));
		return sendRequest(url, requestMethod, params, new DefaultHttpClient());
	}
	
	/**
	 * 向api发送get请求，返回从后台取得的信息。
	 * 
	 * @param url
	 * @param client
	 * @return String
	 */
	protected static String sendRequest(String url, int requestMethod, List<NameValuePair> params, DefaultHttpClient client) 
			throws Exception {
		String result = null;
		HttpRequestBase request = null;
		HttpResponse httpResponse = null;
		
		Log.d(TAG, "sendRequest,url = "+ url);
		
		try {
			//getMethod.setHeader("User-Agent", USER_AGENT);
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
			
			if (requestMethod == HTTP_GET) {
				Log.d(TAG, "sendRequest,requestMethod == HTTP_GET");
				HttpGet httpGet = new HttpGet(url);
				request = httpGet;
				httpResponse = client.execute(httpGet);
			}else if (requestMethod == HTTP_POST) {
				Log.d(TAG, "sendRequest,requestMethod == HTTP_POST");
				HttpPost httpPost = new HttpPost(url);
				request = httpPost;
				httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				httpResponse = client.execute(httpPost);
				Log.d(TAG, "sendRequest,get response");
			}

			int status = httpResponse.getStatusLine().getStatusCode();
			Log.d(TAG, "sendRequest,get response,status:" + status);
			if (status == HttpStatus.SC_OK) {
				result = retrieveInputStream(httpResponse.getEntity());
			} else {
				Log.d(TAG, "http status not ok, status:" + status);
			}
		} catch (Exception e) {
			//Log.e(TAG, e.getMessage());
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			if (request != null) {
				request.abort();
			}
		}
		return result;
	}
	
	/**
	 * 处理httpResponse信息,返回String
	 * 
	 * @param httpEntity
	 * @return String
	 */
	protected static String retrieveInputStream(HttpEntity httpEntity) {
				
		int length = (int) httpEntity.getContentLength();	
		//the number of bytes of the content, or a negative number if unknown. 
		//If the content length is known but exceeds Long.MAX_VALUE, a negative number is returned.
		//length==-1，下面这句报错，println needs a message
		if (length < 0) length = 10000;
		StringBuffer stringBuffer = new StringBuffer(length);
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(httpEntity.getContent(), HTTP.UTF_8);
			char buffer[] = new char[length];
			int count;
			while ((count = inputStreamReader.read(buffer, 0, length - 1)) > 0) {
				stringBuffer.append(buffer, 0, count);
			}
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
		} catch (IllegalStateException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return stringBuffer.toString();
	}
}
