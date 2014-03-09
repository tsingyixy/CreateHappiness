package com.createhappiness.russiandiamond;

import java.util.Random;

import android.util.Log;
import android.view.*;
/**
 * 
 * @author tsingyi
 *方块类，具有坐标和方向属性
 */
public class Diamond {
	//public static Pool<Diamond> pool = new Pool<Diamond>();
    public int x;
    public int y;
	public Diamond() {
		 this.x = 0;
		 this.y = 0;
	//	 pool.push(this);

	}
	public void SetPosition(int x ,int y){
		this.x = x;
		this.y = y;
	}
    public Diamond(int x,int y){
        this.x = x;
        this.y = y;
       // pool.push(this);
    }
}
/**
 * 
 * @author tsingyi
 * 正在下落的方块即玩家可以操控的
 */
class Player{
	private static final int SHAPE_TYPE_COUNT = 7;
    private int x;
    private int y;
    private int[][] diamonds;
    private int[][] world;
//    private boolean isAccerlate;
    private Graphics g;
    public Player(int[][] world,Graphics g){
    	this.world = world;
    	this.g = g;
    	x = 7;
    	y= 0;
//    	isAccerlate = false;
    	Random r = new Random();
    	Init(Math.abs(r.nextInt() % SHAPE_TYPE_COUNT));
    }
    public void reset(){
    	Asset.nextTime = true;
    	x = 7;
    	y= 0;
//    	isAccerlate = false;
    	Random r = new Random();
    	Init(Math.abs(r.nextInt() % SHAPE_TYPE_COUNT));
    	//Init(1);
    }
    private void Init(int shape){
    	switch (shape) {
		case 0://正方形
	    	diamonds = new int[][]{{1,1},{1,1}};
			break;
		case 1://长条
			diamonds = new int[][]{{1,1,1,1}};
			break;
		case 2://正Z
			diamonds = new int[][]{{1,1,0},{0,1,1}};
			break;
		case 3://反Z
			diamonds = new int[][]{{0,1,1},{1,1,0}};
			break;
		case 4://正7
			diamonds = new int[][]{{1,1},{0,1},{0,1}};
			break;
		case 5://反7
			diamonds = new int[][]{{1,1},{1,0},{1,0}};
			break;
		case 6://T塔
			diamonds = new int[][]{{0,1,0},{1,1,1}};
			break;
		default:
			break;
		}
    }
	synchronized public int LeftMove(){

            if(CheckOverlap(x, y, diamonds, world, -1))
        	    x -=1;

		return 0;
		
	}
	synchronized public int RightMove(){

		    if(CheckOverlap(x, y, diamonds, world, 1))
                x += 1;

		return 0;
	}
//	public int AlwaysRightMove(){
//		while(Asset.alwaysRight){
//			RightMove();
//			try {
//				Thread.sleep(330);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return 0;
//	}
    public int Transformation(){

    	int temp = 0;
    	int len = diamonds.length - 1;
    	if(CheckOverlap(x, y, diamonds, world, 2))
    	{
    		int[][] _diamond = new int[diamonds[0].length][diamonds.length];
            for(int i = 0 ; i < diamonds.length ;++ i)
            	for(int j = 0 ; j < diamonds[i].length ; ++ j){
            		_diamond[j][i] = diamonds[i][j];
            	} 
            for(int i = 0 ; i < _diamond.length ;++ i)
            	for(int j = 0 ; j < _diamond[i].length / 2 ; ++ j){
            		temp = _diamond[i][j];
            		_diamond[i][j] = _diamond[i][len -j];
            		_diamond[i][len -j] = temp;
            	}
            diamonds = _diamond;
    	}
    	return 0;
    }
    public int Accerlate(){           //丢下该方块
//    	isAccerlate = true;
    	if(!Asset.nextTime){
    		while(CheckOverlap(x, y, diamonds, world, 0))
    		{ 
    		    Down();
    		    try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    	}
//    	isAccerlate = false;
    	return 0;
    }
    public synchronized boolean CheckOverlap(int x, int y,int[][] diamonds,int[][] world,int direction){//检查能否移动
		//synchronized (Asset.lock) {
        if(direction == -1){        //向左
        	if(x - 1 < 0)           //超出边界不能移动
        		return false;
        	for(int i = 0 ; i < diamonds.length ; ++ i)         //左边已经有了方块不能移动
        		for(int j = 0 ; j < diamonds[i].length ; ++ j)
        			{
        			    if(y-i >= 0 && (diamonds[i][j] + world[y-i][x-1+j] == 2))
        			    	return false;
        			} 				
        	return true;
        }
        if(direction == 0){             //向下
        	if(y + 1 > world.length - 1)//超出底边不能移动
        		return false;
        	for(int i = 0 ; i < diamonds.length ; ++ i)
        	    for(int j = 0 ; j < diamonds[i].length ; ++ j)
        			if(y+1-i >=0 && diamonds[i][j] + world[y+1-i][x+j] == 2)
        				return false;
        		return true;
        		
        }
        if(direction == 1){             //向右
        	if(x + diamonds[0].length > world[0].length - 1)//超出右边不能移动
        		return false;
        	for(int i = 0 ; i < diamonds.length ; ++ i)         //下边已经有了方块不能移动
         		for(int j = 0 ; j < diamonds[i].length ; ++j)
        			{
         			    if(y-i >=0 && diamonds[i][j] + world[y-i][x+j+1] == 2)
         			   	    return false;
        			}
        			
        		return true;
        }
        if(direction == 2){
        	int wid = diamonds.length;
        	//int height = diamonds[0].length;
        	if(x+wid>world[0].length)
        		return false;
        	if (CheckOverlap(x-1, y, diamonds, world, 1)){
        		for(int i = 0 ; i < diamonds.length ; ++ i)        
             		for(int j = 0 ; j < diamonds[i].length ; ++j)
            			{
             			 if(y - j >= 0 && (diamonds[i][j] + world[y-j][x+i] == 2))
             				 return false;
            			}
        		return true;
        	}
        		
        	
        }
		//}
        return true;
    }
    synchronized public int Down(){

            if(CheckOverlap(x, y, diamonds, world, 0))
        	    y ++;
            else{
            	if(y - diamonds.length<0)
            		return -1;
                for(int i = 0 ; i < diamonds.length ;++ i)
                	for(int j = 0 ; j < diamonds[i].length ; ++ j){
            	    	world[y-i][x + j] |= diamonds[i][j];
            	    }
               reset();
            }

        return 0;
    }
    public int update(){
//    	if(!isAccerlate)
    	return Down();
    }
    public void present(){
        for(int i = 0 ; i < diamonds.length ;++ i)
        	for(int j = 0 ; j < diamonds[i].length ; ++ j){
        		if(diamonds[i][j] == 1)
    	            g.DrawImage(Asset.playerImage, (x+j)*20, (y-i)*20);
        	}
    }
}
/**
 * 
 * @author tsingyi
 * 整个游戏世界，包括下落的方块和已经落下的方块
 */
class World implements View.OnTouchListener{

	private Player player;
	private int[][] fields;
	private int sum[];
	private Graphics graphics;
	private float oldX;
	private float oldY;
	private boolean pressDown;
//	private boolean glide;
	
	//private boolean isNew;
	public World(Graphics g,SurfaceView view){ 
		this.fields= new int[24][16];
		player = new Player(fields,g);
		sum = new int[24];
		this.graphics = g;
		oldX = 0;
		oldY = 0;
		pressDown = false;
		Asset.nextTime = false;
		Log.i("THQ", Asset.nextTime==true?"true":"false");
//		glide = false;
		view.setOnTouchListener(this);
		//isNew = false;
	}
	
	
	public int update() { // 更新游戏逻辑
		//Log.i("THQ", Asset.nextTime==true?"true":"false");
		if (Asset.frequency == 0) {
			if(player.update() == -1)
				return -1;
			for (int j = 0; j < fields.length; ++j) {
				sum[j] = 0;
				for (int i = 0; i < fields[j].length; ++i) {
					sum[j] += fields[j][i];
				}
			}
			for (int i = 0; i < sum.length; ++i) {
				if (sum[i] == fields[0].length)
					for (int j = i; j >= 1; j--)
						fields[j] = fields[j - 1];
			}
		}
		return 0;
	}
	
	public void present(){//画图到缓冲区
		player.present();
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if(fields[i][j] == 1)
					graphics.DrawImage(Asset.diamondImage, j*20, i*20);
			}
		}
	   try {
			Thread.sleep(33);
			Asset.frequency = (Asset.frequency+1) % 10;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		if(Asset.nextTime)               //新出一个形状的时候如果上次按住不放，在这里重置控制条件
		{
			pressDown = false;
//			glide = false;
		}
		if(pressDown)
			TimeLine.Finish(System.currentTimeMillis());
        if(pressDown && TimeLine.Interval() > 600)
        	{
        	    if(oldX * Asset.scaleX <= 160)
        	    	player.LeftMove();
        	    else
        		    player.RightMove();
        	    try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
//        if(glide){
//        	player.Down();
//        	try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			oldX = event.getX();
			oldY = event.getY();
			if(!pressDown)
			{
				pressDown = true;
				TimeLine.Begin(System.currentTimeMillis());
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if ((Math.abs(event.getX() - oldX) * Asset.scaleX > 20) || (Math
					.abs(event.getY() - oldY)) * Asset.scaleY > 20)
			    {
				    pressDown = false;
//				    if(event.getY() > oldY)
//				        glide = true;
			    }

			break;
		case MotionEvent.ACTION_UP:
			pressDown = false;
			Asset.nextTime = false;
//			glide = false;
				if (((Math.abs(event.getX() - oldX) * Asset.scaleX < 20) && (Math
						.abs(event.getY() - oldY)) * Asset.scaleY < 20)
						&& (oldX * Asset.scaleX <= 160)) {
					player.LeftMove();
				} else if (((Math.abs(event.getX() - oldX) * Asset.scaleX < 20) && (Math
						.abs(event.getY() - oldY)) * Asset.scaleY < 20)
						&& (oldX * Asset.scaleX > 160)) {
					player.RightMove();
				} else if ((Math.abs(event.getY() - oldY) * Asset.scaleY > 20)
						&& event.getY() < oldY) {
					player.Transformation();
				}
				else if ((Math.abs(event.getY() - oldY) * Asset.scaleY > 20)
						&& event.getY() > oldY) {
					player.Accerlate();
				}
				Asset.nextTime = false;
			break;
		default:
			break;
		}
		return true;
	}
}

