package com.createhappiness.russiandiamond;

import android.graphics.Rect;
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
class LoadingScreen extends Screen{
	private Graphics g;
	public static Rect screen;
	public LoadingScreen(Game game){
		super(game);	
 
	}
	public int update(){            
		this.g = game.getGraphics();
		//Asset.bgImage = g.CreateImage("bgIamge path");
		Asset.diamondImage = g.CreateImage("blue_diamond.png");

		//Asset.mainImage = g.CreateImage("bgImage path");
		Asset.playerImage = g.CreateImage("red_diamond.png");
		game.setScreen(new MainScreen(game));

		
		return 0;
	}
	public int present(){
		//g.DrawImage(Asset.loadingImage, 0,0);
		//Asset.playerImage = g.CreateImage("red_diamond.png");
		//Asset.diamondImage = g.CreateImage("blue_diamond.png");

		return 0;
	}
}
class MainScreen extends Screen{
	public MainScreen(Game game){
		super(game);
	}
	public int update(){
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
	public int update(){
		world.update();
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
}
