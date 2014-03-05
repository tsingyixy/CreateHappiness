package com.createhappiness.russiandiamond;

import android.view.SurfaceView;

/**
 * 定义的游戏接口
 * 定义共有的需要实现的部分功能
 * @author tsingyi
 *
 */
public interface Game {
      public Graphics getGraphics();            //获取绘图句柄
     /// public Screen getScreen();
      public int getStutas();                     //获取游戏状态
      public Screen GetCurrentScreen();           //获取现在绘画的帧
      public void setScreen(Screen s);          //设置现在绘画的帧
	  public SurfaceView getView();           //获取当前使用的视图
}
