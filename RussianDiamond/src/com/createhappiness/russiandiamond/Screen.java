package com.createhappiness.russiandiamond;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.Surface;
import android.view.SurfaceHolder;
/**
 * 在缓冲区绘制的游戏中的每一帧
 * @author tsingyi
 *
 */
public abstract class Screen {
	protected final Game game;           
	public Screen(Game g){
		this.game = g;
	}
	public int update(){           //update logic
		return 0;
	}
	public int present(){             //rendering
		return 0;
	}
	public void resume(){
         
	}
	public void pause(){
		
	}
}
class LoadingScreen extends Screen{        //启动画面
	private Graphics g;
	public static Rect screen;
	public LoadingScreen(Game game){
		super(game);	
	}
	public int update(){            //加载所有资源
		this.g = game.getGraphics();
		//Asset.bgImage = g.CreateImage("bgIamge path");
		Asset.diamondImage = g.CreateImage("blue_diamond.png");
		Asset.loadingImage = g.CreateImage("Loading.png");
		//Asset.mainImage = g.CreateImage("bgImage path");
		Asset.playerImage = g.CreateImage("red_diamond.png");
		Asset.endingImage = g.CreateImage("ending.png");
		Asset.frequency = 0;	
		Asset.isLoading = true;
		return 0;
	}
	public int present(){               //绘制启动画面，这里顺序需要改变，否则先加载后显示，没有效果
		game.getGraphics().Clear();
		game.getGraphics().DrawImage(Asset.loadingImage, 0, 0);
		game.setScreen(new MainScreen(game));
		return 0;
	}
}
class MainScreen extends Screen{         //游戏菜单画面，待定
	public MainScreen(Game game){
		super(game);
	}
	public int update(){      //启动游戏画面
		game.setScreen(new GameScreen(game,new World(game.getGraphics(),game.getView())));
		return 0;
	}
}
class GameScreen extends Screen{
	private World world;
	public GameScreen(Game game,World w){
		super(game);
		this.world = w;
	}
	public int update(){         //更新游戏逻辑，如果游戏结束则返回结束画面
		if(world.update() == -1)
			game.setScreen(new EndScreen(game));
		return 0;
	}
	public int present(){     
		game.getGraphics().Clear();
		world.present();
		return 0;
	}
}
class EndScreen extends Screen{
	public EndScreen(Game game){
		super(game);
	}
	public int update(){
		return 0;
	}
	public int present(){
		game.getGraphics().Clear();
		game.getGraphics().DrawImage(Asset.endingImage, 0, 0);
		return 0;
	}
}
