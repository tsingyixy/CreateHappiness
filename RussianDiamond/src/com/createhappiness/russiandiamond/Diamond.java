package com.createhappiness.russiandiamond;

import java.util.Random;
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
    	Init(Math.abs(r.nextInt() % 6));
    	//Init(1);
    }
    public void reset(){
    	x = 7;
    	y= 0;
    	Random r = new Random();
    	Init(Math.abs(r.nextInt() % 6));
    	//Init(1);
    }
    private void Init(int shape){
    	switch (shape) {
		case 0:                    //box
	    	diamonds = new int[][]{{1,1},{1,1}};
			break;
		case 1:
			diamonds = new int[][]{{1,1,1,1},{0,0,0,0}};
			break;
		case 2:
			diamonds = new int[][]{{1,1,0},{0,1,1}};
			break;
		case 3:
			diamonds = new int[][]{{1,1},{0,1},{0,1}};
			break;
		case 4:
			diamonds = new int[][]{{1,1,1},{1,0,1}};
			break;
		case 5:
			diamonds = new int[][]{{0,1,0},{1,1,1}};
			break;
		default:
			break;
		}


    }
	public int LeftMove(){
		boolean stop = false;
		if (x > 0){
			for(int i = 0 ; i < diamonds.length ;++ i)
			{
				if(y-i >= 0 && (diamonds[diamonds.length-1-i][0] + world[y-i][x-1]) == 2)
				{
					stop = true;
					break;
				}
			}
		}
	   else{
		   stop = true;
	   }
	   if(!stop)
            x -= 1;
		return 0;
		
	}
	public int RightMove(){
		boolean stop = false;
		if(x+diamonds[0].length < world[0].length)
		{
			for(int i = 0 ; i < diamonds.length ;++ i)
			{
				if(y-i >= 0 && (diamonds[diamonds.length-1-i][diamonds[0].length - 1] + world[y-i][x + diamonds[0].length - 1]) == 2)
				{
					stop = true;
					break;
				}
			}
		}
		else{
			stop = true;
		}
		if(!stop)
            x += 1;
		return 0;
	}
    public int Transformation(){

    	int temp = 0;
    	int len = diamonds.length - 1;
    	if(x + len < world[0].length)
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
    public int Accerlate(){           //down more quickly
    	return 0;
    }
    public int Down(){
    	boolean stop = false;
        if(y < world.length - 1)
        	for(int i = 0 ; i < diamonds[0].length; ++ i){
        		if (world[y+1][x + i] + diamonds[0][i] == 2)
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
            		world[y-i][x + j] |= diamonds[i][j];
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
class World implements  View.OnTouchListener{
	private Player player;
	private int[][] fields;
	private int sum[];
	private Graphics graphics;
	private float oldX;
	private float oldY;
	private boolean isMove;
	//private boolean isNew;
	public World(Graphics g,SurfaceView view){ 
		this.fields= new int[24][16];
		player = new Player(fields,g);
		sum = new int[24];
		this.graphics = g;
		oldX = 0;
		oldY = 0;
		isMove = false;
		view.setOnTouchListener(this);
		//isNew = false;
	}
	public void update(){ //更新游戏逻辑
	    if (Asset.frequency == 0)   
		    {
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

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			oldX = event.getX();
			oldY = event.getY();
            break;
		case MotionEvent.ACTION_MOVE:
            if(Math.abs(event.getX() - oldX) > Math.abs(event.getY() - oldY))
            	isMove = true;
			break;
		case MotionEvent.ACTION_UP:
			if(isMove)
				player.Transformation();
			else if(oldX * Asset.scaleX <= 160)
			    player.LeftMove();
			else
				player.RightMove();
			isMove = false;
			break;
		default:
			break;
		}
		return true;
	}
}

