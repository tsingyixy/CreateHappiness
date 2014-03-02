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
 * 定义图像接口，具有从主活动获取的画布缓冲区和自定义的画笔
 * 以及各种资源的接口
 * @author tsingyi
 *
 */
public class Graphics {
	//public SurfaceHolder holder;
	private Canvas canvas;
	private Bitmap frameBuffer;
	private Paint pen;
	private AssetManager assets;
    public Graphics(AssetManager asset,Bitmap buffer){
    	this.frameBuffer = buffer;
    	this.canvas = new Canvas(frameBuffer);
    	this.assets = asset;
    	this.pen = new Paint();
    	pen.setColor(Color.BLUE);
    }
    public void DrawRect(Rect r){
    	canvas.drawRect(r, pen);
    }
    public Bitmap CreateImage(String filepath){
    	InputStream in = null;
    	try {
			in = assets.open(filepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("can not open file"+filepath);
		}
    	return (null != in) ? BitmapFactory.decodeStream(in):null;
    }
    public void DrawImage(Bitmap image,int left ,int top){
    	//Bitmap bitmap = image.getImage();
    	canvas.drawBitmap(image,left,top,null);
    }
    public void DrawText(String text,int left,int top){
    	canvas.drawText(text, left, top, pen);
    }
}
