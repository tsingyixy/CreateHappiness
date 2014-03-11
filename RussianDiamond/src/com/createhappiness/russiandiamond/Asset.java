package com.createhappiness.russiandiamond;

import android.graphics.Bitmap;
/**
 * 
 * @author tsingyi
 * 资源类包含了游戏中用到的所有资源，如启动画面，音乐等
 */
public class Asset {
      public static Bitmap loadingImage;               //启动画面
      public static Bitmap mainImage;                   //主菜单画面
      public static Bitmap bgImage ;                     //背景图
      public static Bitmap diamondImage;                //已经落下的方块图
      public static Bitmap playerImage;                 //正在落下的方块图
      public static Bitmap endingImage;                     //游戏结束画面
      public static float scaleX;                         //缩放比例X,Y
      public static float scaleY;
      public static int frequency ;                         //画面刷新频率， 1000/frequency = frame(帧数)
      public static boolean alwaysRight;                   //是否按住左,右移
      public static boolean alwayLeft;
      public static Object lock = new Object();              //用来 同步移动的锁
      public static boolean nextTime;                        //是否产生了一个新方块，因为当新方块产生的时候需要重置按键事件
      public static boolean isLoading;                      //现在画面是否是启动画面，启动画面用来显示LOGO等信息
}
class TimeLine{              //时间类，用来记录事件持续时间
	private static long _start = 0;
	private static long _end = 0;
	public static void Begin(long l){
		_start = l;
	}
	public static void Finish(long end){
		_end = end;
	}
	public static long Interval(){
		return _end - _start;
	}
}
