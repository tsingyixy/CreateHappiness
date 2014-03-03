package com.createhappiness.russiandiamond;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
/**
 * 游戏主活动
 * @graphics 绘画句柄
 * @status 游戏正处于何种状态
 * @currentScreen 游戏正呈现哪个画面
 * @_frameBuffer 用于绘图的缓冲区
 * @_diamondView 用于画图的View
 * @author tsingyi
 *
 */
public class DiamondGame extends Activity implements Game{
    private Graphics graphics;
    private int status;
    private Screen currentScreen;
    private Bitmap _frameBuffer;
    private DiamondView _diamondView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        status = 0;
        int frameWidth = 320;
        int frameHeigth = 480;

        _frameBuffer = Bitmap.createBitmap(frameWidth, frameHeigth, Bitmap.Config.ARGB_4444);
        _diamondView = new DiamondView(this, _frameBuffer);
        graphics = new Graphics(this.getAssets(), _frameBuffer);
        Asset.loadingImage = graphics.CreateImage("loading path");
        this.setScreen(new LoadingScreen(this));
		setContentView(_diamondView);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	//	wakeLock.release();

		currentScreen.pause();
		_diamondView.pause();
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//		wakeLock.acquire();
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

	@Override
	public void setScreen(Screen s) {
		// TODO Auto-generated method stub
		this.currentScreen = s;
	}

}
