package com.edu.kindergarten.config;

import android.os.Environment;


public class Constants {

	// SD Card目录
	public static final String SD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();

	//缓存根目录
	public static final String BASE_PATH = "/kindergarten/";

	//图片缓存目录
	public static final String BASE_IMAGE_CACHE = BASE_PATH + "cache/images/";
	
	//json文件缓存目录
	public static final String BASE_JSON_CACHE = BASE_PATH + "cache/jsons/";
}
