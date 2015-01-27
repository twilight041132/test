package com.edu.kindergarten.ui;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import com.edu.kindergarten.config.Constants;
import com.edu.kindergarten.config.ImageLoaderConfig;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		if (!ImageLoader.getInstance().isInited()) {
			ImageLoaderConfig.initImageLoader(this, Constants.BASE_IMAGE_CACHE);
		}
	}
}
