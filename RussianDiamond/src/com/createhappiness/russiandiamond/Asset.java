package com.createhappiness.russiandiamond;

import android.graphics.Bitmap;
/**
 * 
 * @author tsingyi
 * 资源类包含了游戏中用到的所有资源，如启动画面，音乐等
 */
public class Asset {
      public static Bitmap loadingImage;
      public static Bitmap mainImage;
      public static Bitmap bgImage ;
      public static Bitmap diamondImage;
      public static Bitmap playerImage;
      public static float scaleX;
      public static float scaleY;
      public static int frequency ;
      public static boolean alwaysRight;
      public static boolean alwayLeft;
      public static Object lock = new Object();
      public static boolean nextTime = false;
}
class TimeLine{
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
