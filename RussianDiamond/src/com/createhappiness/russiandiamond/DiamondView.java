package com.createhappiness.russiandiamond;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
/**
 *游戏绘画主循环
 *@_render 游戏主循环线程
 *@_game 获取的游戏句柄，用于获得其中的资源句柄
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
		//Rect = 
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
	//������ѭ���߳�
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
	//����������Ϸ��ѭ�����̣߳����ϵ��ظ����ڴ�֡��ȡ��һ֡���Ƶ�
	//������
	class RenderingThread extends Thread{
        public boolean runing;
        private SurfaceHolder _holder;
       /// Rect rect;
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
					canvas.drawBitmap(_frameBuffer, null, canvas.getClipBounds(), null);
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
