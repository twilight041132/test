package com.edu.kindergarten.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;


public class ImageUtils {
	/**
	 * 根据宽度等比例缩放图片
	 * 
	 * @param defaultBitmap
	 * @param width
	 * @return
	 */
	public static Bitmap resizeImageByWidth(Bitmap defaultBitmap,
			int targetWidth) {
		int rawWidth = defaultBitmap.getWidth();
		int rawHeight = defaultBitmap.getHeight();
		float targetHeight = targetWidth * (float) rawHeight / (float) rawWidth;
		float scaleWidth = targetWidth / (float) rawWidth;
		float scaleHeight = targetHeight / (float) rawHeight;
		Matrix localMatrix = new Matrix();
		localMatrix.postScale(scaleHeight, scaleWidth);
		return Bitmap.createBitmap(defaultBitmap, 0, 0, rawWidth, rawHeight,
				localMatrix, true);
	}
	
	public static void displayImage(String imageUrl, ImageView imageView){
		ImageLoader.getInstance().displayImage(imageUrl, imageView);
	}

}