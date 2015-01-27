package com.edu.kindergarten.config;

import android.os.Environment;


public class Constants {
	
	//status
	public static final int STATUS_BASE = 10;
	public static final int NETWORK_ERROR = STATUS_BASE + 1;
	public static final int ACCOUNT_NOT_REGISTERED = STATUS_BASE + 2;
	public static final int ACCOUNT_PASSWORD_WRONG = STATUS_BASE + 3;
	public static final int ACCOUNT_LOGIN_SUCCEED = STATUS_BASE + 4;
	
	//������url
	public static final String URL_LOGIN = "http://kmarketserver.sinaapp.com/api/account/login";

	//�����Ŀ¼
	public static final String BASE_PATH = "/kindergarten/";

	//ͼƬ����Ŀ¼
	public static final String BASE_IMAGE_CACHE = BASE_PATH + "cache/images/";
	
	//json�ļ�����Ŀ¼
	public static final String BASE_JSON_CACHE = BASE_PATH + "cache/jsons/";
}
