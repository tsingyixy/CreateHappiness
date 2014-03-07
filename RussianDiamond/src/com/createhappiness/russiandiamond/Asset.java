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
}
class TimeLine{
	private static int _start;
	private static int _end;
	public static void Begin(int start){
		_start = start;
	}
	public static void Finish(int end){
		_end = end;
	}
	public static int Interval(){
		return _end - _start;
	}
}
