package com.createhappiness.russiandiamond;
/**
 * 定义的游戏接口
 * 定义共有的需要实现的部分功能
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
