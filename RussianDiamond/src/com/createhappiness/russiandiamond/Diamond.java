package com.createhappiness.russiandiamond;

import java.util.Random;
import android.view.*;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener;
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
    private int x;
    private int y;
    private int[][] diamonds;
    private int[][] world;
    private Graphics g;
    public Player(int[][] world,Graphics g){
    	x = 7;
    	y= 0;
    	this.world = world;
    	this.g = g;
    	Random r = new Random();
    	Init(0);
    }
    public void reset(){
    	x = 7;
    	y= 0;
    	Random r = new Random();
    	Init(0);
    }
    private void Init(int shape){
    	switch (shape) {
		case 0:                    //box
	    	diamonds = new int[][]{{1,1},{1,1}};
			break;
		case 1:
			//diamonds.add(diamondPool.)
		default:
			break;
		}


    }
	public int LeftMove(){ 
		if (x > 0 && world[y][x - 1] != 1)
            x -= 1;
		return 0;
		
	}
	public int RightMove(){
		if ((x+ diamonds[0].length <= world[0].length - 1) && world[y][x+diamonds[0].length] != 1)
            x += 1;
		return 0;
	}
    public int Transformation(){
        for(int i = 0 ; i < diamonds.length ;++ i)
        	for(int j = 0 ; j < diamonds[i].length ; ++ j){
        		diamonds[j][i] = diamonds[i][j];
        	} 		
    	return 0;
    }
    public int Accerlate(){           //down more quickly
    	return 0;
    }
    public int Down(){
    	boolean stop = false;
        if(y < world.length - 1)
        	for(int i = 0 ; i < diamonds[0].length; ++ i){
        		if (world[y + 1][x + i] == 1)
        		{
        			stop = true;
        			break;
        		}

        	}
        else
        	stop = true;

        if(stop){
            for(int i = 0 ; i < diamonds.length ;++ i)
            	for(int j = 0 ; j < diamonds[i].length ; ++ j){
            		world[y-j][x + i] = diamonds[j][i];
            	}
            reset();
        }
        else
            this.y += 1;
        return 0;
    }
    public void update(){
    	Down();
    }
    public void present(){
        for(int i = 0 ; i < diamonds.length ;++ i)
        	for(int j = 0 ; j < diamonds[i].length ; ++ j){
    	        g.DrawImage(Asset.playerImage, (x+i)*20, (y-j)*20);
        	}
    }
}
/**
 * 
 * @author tsingyi
 * 整个游戏世界，包括下落的方块和已经落下的方块
 */
class World implements  View.OnTouchListener{
	private Player player;
	private int[][] fields;
	private int sum[];
	private Graphics graphics;
	//private boolean isNew;
	public World(Graphics g,SurfaceView view){ 
		this.fields= new int[24][16];
		player = new Player(fields,g);
		sum = new int[24];
		this.graphics = g;
		view.setOnTouchListener(this);
		//isNew = false;
	}
	public void update(){ //更新游戏逻辑
	       player.update();
	       for(int j = 0 ; j < fields.length ; ++ j )
	       {
	    	   sum[j] = 0;
	    	   for(int i = 0 ; i < fields[j].length ; ++ i)
	    	   {
	    		   sum[j] += fields[j][i];
	    	   }
	       }
	       for(int i = 0 ; i < sum.length; ++ i){
	    	   if (sum[i] == fields[0].length)
	    		   for(int j = i ; j >=1 ; j--)
	    			   fields[j] = fields[j - 1]; 
	       }
	       try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void present(){//画图到缓冲区
		player.present();
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if(fields[i][j] == 1)
					graphics.DrawImage(Asset.diamondImage, j*20, i*20);
			}
		}
	}
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		int action = arg1.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if(arg1.getX() * Asset.scaleX <= 160)
				player.LeftMove();
			else if(arg1.getX() * Asset.scaleX > 160)
				player.RightMove();
			break;

		default:
			break;
		}
		return false;
	}
}

