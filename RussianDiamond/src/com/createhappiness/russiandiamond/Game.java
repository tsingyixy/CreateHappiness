package com.createhappiness.russiandiamond;
/**
 * 定义游戏类接口，用来获取各种句柄，句柄在
 * 主活动中初始化
 * @author tsingyi
 *
 */
public interface Game {
      public Graphics getGraphics();
     /// public Screen getScreen();
      public int getStutas();
      public Screen GetCurrentScreen();
      public void setScreen(Screen s);
}
