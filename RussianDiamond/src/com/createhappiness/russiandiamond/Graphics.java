package com.createhappiness.russiandiamond;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * 绘画句柄，包括了一个画布和一个缓冲区即资源句柄 提供游戏所需的各种绘图功能
 * 
 * @author tsingyi
 * 
 */
public class Graphics {
	// public SurfaceHolder holder;
	private Canvas canvas;
	private Bitmap frameBuffer;
	private Paint pen;
	private AssetManager assets;

	public Graphics(AssetManager asset, Bitmap buffer) {
		this.frameBuffer = buffer;
		this.canvas = new Canvas(frameBuffer);
		this.assets = asset;
		this.pen = new Paint();
		pen.setColor(Color.BLUE);
	}

	public void DrawRect(Rect r) {
		canvas.drawRect(r, pen);
	}

	public Bitmap CreateImage(String filepath) { // 加载图像资源
		InputStream in = null;
		try {
			in = assets.open(filepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("can not open file" + filepath);
		}
		// return (null != in) ? BitmapFactory.decodeStream(in):null;
		return BitmapFactory.decodeStream(in);
	}

	public void DrawImage(Bitmap image, int left, int top) { // 绘制图像到缓冲区
		// Bitmap bitmap = image.getImage();
		canvas.drawBitmap(image, left, top, null);
	}

	public void DrawText(String text, int left, int top) {// 绘制字幕到缓冲区
		canvas.drawText(text, left, top, pen);
	}

	public void Clear() { // 用白色清屏
		canvas.drawColor(Color.WHITE);
	}
}
