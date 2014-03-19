package com.createhappiness.russiandiamond;

import java.io.IOException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 暂时不用
 * 
 * @author tsingyi
 * 
 */
public class Image {
	public Bitmap bitmap;
	// public static InputStream in;
	public static AssetManager asset;

	// public Bitmap.Config config;
	public Image(String filepath) {
		try {
			bitmap = BitmapFactory.decodeStream(asset.open(filepath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Image(int width, int height) {
		bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
	}

	public int getHeight() {
		return bitmap.getHeight();
	}

	public int getWidth() {
		return bitmap.getWidth();
	}

	public Bitmap getImage() {
		return bitmap;
	}
}
