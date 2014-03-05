package com.createhappiness.russiandiamond;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        status = 0;
        int frameWidth = 320;
        int frameHeigth = 480; 
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        int height = metric.heightPixels;
        Asset.scaleX = (float)frameWidth / width;
        Asset.scaleY = (float)frameHeigth / height;
        _frameBuffer = Bitmap.createBitmap(frameWidth, frameHeigth, Bitmap.Config.ARGB_4444);
        graphics = new Graphics(this.getAssets(), _frameBuffer);
		Asset.loadingImage = graphics.CreateImage("Loading.png");
	    _diamondView = new DiamondView(this, _frameBuffer);
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
    public SurfaceView getView(){
    	return _diamondView;
    }
	@Override
	public void setScreen(Screen s) {
		// TODO Auto-generated method stub
		this.currentScreen = s;
	}

}
