package com.edu.kindergarten.config;

import android.os.Environment;


public class Constants {

	// SD CardĿ¼
	public static final String SD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();

	//�����Ŀ¼
	public static final String BASE_PATH = "/kindergarten/";

	//ͼƬ����Ŀ¼
	public static final String BASE_IMAGE_CACHE = BASE_PATH + "cache/images/";
	
	//json�ļ�����Ŀ¼
	public static final String BASE_JSON_CACHE = BASE_PATH + "cache/jsons/";
}
