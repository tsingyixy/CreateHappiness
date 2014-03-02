package com.createhappiness.russiandiamond;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
/**
 * 继承自SurfaceView的类，主要用于游戏主循环，不停的更新画面
 * @author tsingyi
 *
 */
public class DiamondView extends SurfaceView implements Callback {
    
	private Bitmap _frameBuffer;
	private Game _game;
	private SurfaceHolder _holder;
	private RenderingThread _render;
	public DiamondView(Context context,Bitmap buffer) {
		super(context);
		// TODO Auto-generated constructor stub
		this._frameBuffer = buffer;
		this._game = (Game)context;
		this._holder = getHolder();
		_holder.addCallback(this);
		_render = new RenderingThread(_holder);
	}

	public DiamondView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DiamondView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//启动主循环县城
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		this._render.runing = true;
		this._render.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		this._render.runing = false;
		
	}
	//定义用于游戏主循环的线程，不断的重复从内存帧中取出一帧绘制到
	//画布上
	class RenderingThread extends Thread{
        public boolean runing;
        private SurfaceHolder _holder;
        public RenderingThread(SurfaceHolder holder){
        	this.runing = false;
        	this._holder = holder;
        }
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(runing){
				synchronized (_holder) {
					Canvas canvas = _holder.lockCanvas();
					_game.GetCurrentScreen().update();
					_game.GetCurrentScreen().present();
					canvas.drawBitmap(_frameBuffer, null, getClipBounds(), null);
					_holder.unlockCanvasAndPost(canvas);
				}
			}
		}
		
	}
	public void resume() {
		// TODO Auto-generated method stub
		_render.runing = true;
	}
	public void pause(){
		_render.runing = false;
	}

}
