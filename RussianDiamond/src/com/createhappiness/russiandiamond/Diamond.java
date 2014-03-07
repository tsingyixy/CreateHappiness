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
	private static final int SHAPE_TYPE_COUNT = 7;
    private int x;
    private int y;
    private int[][] diamonds;
    private int[][] world;
    private Graphics g;
    public Player(int[][] world,Graphics g){
    	this.world = world;
    	this.g = g;
    	
    	reset();
    }
    public void reset(){
    	x = 7;
    	y= 0;
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
	public int AlwaysRightMove(){
		while(Asset.alwaysRight){
			RightMove();
			try {
				Thread.sleep(330);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
    public int Accerlate(){           //丢下该方块
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
    	return 0;
    }
    public boolean CheckOverlap(int x, int y,int[][] diamonds,int[][] world,int direction){//检查能否移动

        if(direction == -1){        //向左
        	if(x - 1 < 0)           //超出边界不能移动
        		return false;
        	for(int i = 0 ; i < diamonds.length ; ++ i)         //左边已经有了方块不能移动
        		for(int j = 0 ; j < diamonds[i].length ; ++ j)
        			if(y-diamonds.length+i +1 >= 0 && (diamonds[i][j] + world[y-(diamonds.length -1 )+i][x-1+j] == 2))
        				return false;
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
        			if(y+1-diamonds.length+i >=0 && diamonds[i][j] + world[y+1-diamonds.length+i][x+j+1] == 2)
        				return false;
        		return true;
        }
        return true;
    }
    public int Down(){
        if(CheckOverlap(x, y, diamonds, world, 0))
        	y ++;
        else{
            for(int i = 0 ; i < diamonds.length ;++ i)
            	for(int j = 0 ; j < diamonds[i].length ; ++ j){
            		world[y-i][x + j] |= diamonds[i][j];
            	}
            reset();
        }
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
class World implements View.OnTouchListener, Runnable{

	private Player player;
	private int[][] fields;
	private int sum[];
	private Graphics graphics;
	private float oldX;
	private float oldY;
	private boolean holding = false;
	private boolean threadworking = false;
	private boolean downLeft = false;
	private boolean downfunwork = false;
	
	//private boolean isNew;
	public World(Graphics g,SurfaceView view){ 
		this.fields= new int[24][16];
		player = new Player(fields,g);
		sum = new int[24];
		this.graphics = g;
		oldX = 0;
		oldY = 0;
		view.setOnTouchListener(this);
		//isNew = false;
	}
	
	@Override
	public void run() {
		threadworking = true;
		try {
			Thread.sleep(50);
			while (holding) {
				if (downLeft) {
					player.LeftMove();
				} else {
					player.RightMove();
				}
				downfunwork = true;
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		threadworking = false;
	}
	
	public void update() { // 更新游戏逻辑
		if (Asset.frequency == 0) {
			player.update();
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
			downfunwork = false;
			oldX = event.getX();
			oldY = event.getY();
			downLeft = oldX * Asset.scaleX <= 160;
			holding = true;
			if (!threadworking) {
				new Thread(this).start();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (Math.abs(event.getY() - oldY) * Asset.scaleY > 1
					|| Math.abs(event.getX() - oldX) * Asset.scaleX > 1) {
				holding = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			holding = false;
			if (!downfunwork) {
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
				} else if ((Math.abs(event.getY() - oldY) * Asset.scaleY > 20)
						&& event.getY() > oldY) {
					player.Accerlate();
				}
			}
			break;
		default:
			break;
		}
		return true;
	}
}

