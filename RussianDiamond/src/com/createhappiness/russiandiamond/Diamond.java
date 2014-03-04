package com.createhappiness.russiandiamond;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
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
    	x = 15;
    	y= 0;
    	this.world = world;
    	this.g = g;
    	Random r = new Random();
    	Init(r.nextInt() % 6);
    }
    public void reset(){
    	x = 15;
    	y= 0;
    	Random r = new Random();
    	Init(r.nextInt() % 6);
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
		if ((x < world[0].length - 1) && world[y][x+world[0].length] != 1)
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
        if(y + 1 < world.length)
        	for(int i = 0 ; i < diamonds[0].length; ++ i){
        		if (world[y + 1][x + i] == 1)
        		{
        			stop = true;
        			break;
        		}

        	}
            this.y += 1;
        if(stop){
            for(int i = 0 ; i < diamonds.length ;++ i)
            	for(int j = 0 ; j < diamonds[i].length ; ++ j){
            		world[j + y][x + i] = diamonds[j][i];
            	}
        }
        return 0;
    }
    public void update(){
    	Down();

    }
    public void present(){
        for(int i = 0 ; i < diamonds.length ;++ i)
        	for(int j = 0 ; j < diamonds[i].length ; ++ j){
    	        g.DrawImage(Asset.playerImage, (x+i)*20, (y+j)*20);
        	}
    }
}
/**
 * 
 * @author tsingyi
 * 整个游戏世界，包括下落的方块和已经落下的方块
 */
class World{
	private Player player;
	private int[][] fields;
	private int sum[];
	private Graphics graphics;
	//private boolean isNew;
	public World(Graphics g){ 
		this.fields= new int[24][16];
		player = new Player(fields,g);
		sum = new int[24];
		this.graphics = g;
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
}

