package com.createhappiness.russiandiamond;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

/**
 * 游戏主活动
 * 
 * @graphics 绘画句柄
 * @status 游戏正处于何种状态
 * @currentScreen 游戏正呈现哪个画面
 * @_frameBuffer 用于绘图的缓冲区
 * @_diamondView 用于画图的View
 * @author tsingyi
 * 
 */
public class DiamondGameActivity extends Activity implements Game {
	private Graphics graphics;
	private int status;
	private Screen currentScreen;
	private Bitmap _frameBuffer;
	private DiamondView _diamondView;

	public DiamondGameActivity() {
		super();

		status = 0;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels;
		int height = metric.heightPixels;
		int frameWidth = 320; // 默认屏幕分辨率320x480
		int frameHeigth = 480;
		Asset.scaleX = (float) frameWidth / width; // 默认分辨率和实际分辨率之比
		Asset.scaleY = (float) frameHeigth / height;
		_frameBuffer = Bitmap.createBitmap(frameWidth, frameHeigth,
				Bitmap.Config.ARGB_4444);// 创建缓冲区
		graphics = new Graphics(this.getAssets(), _frameBuffer);
		_diamondView = new DiamondView(this, _frameBuffer);
		setContentView(_diamondView);
		this.setScreen(new LoadingScreen(this));
		// Log.i("THQ", "end diamond game oncreate");

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// wakeLock.release();
		// 游戏暂停
		currentScreen.pause();
		_diamondView.pause();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// 游戏启动和暂停返回都会调用
		super.onResume();

		// wakeLock.acquire();
		currentScreen.resume();
		_diamondView.resume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.diamond_game, menu);
		return true;
	}

	@Override
	public Graphics getGraphics() {
		// TODO Auto-generated method stub
		return graphics;
	}

	@Override
	public int getStutas() {
		// TODO Auto-generated method stub
		return status;
	}

	@Override
	public Screen GetCurrentScreen() {
		// TODO Auto-generated method stub
		return currentScreen;
	}

	public SurfaceView getView() {
		return _diamondView;
	}

	@Override
	public void setScreen(Screen s) {
		// TODO Auto-generated method stub
		this.currentScreen = s;
	}

}
