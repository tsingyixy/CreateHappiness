package com.createhappiness.russiandiamond;

import android.graphics.Rect;
/**
 * 这个类代表了画在缓冲区的内容，在主循环的时候会显示在屏幕上
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
		Asset.bgImage = g.CreateImage("bgIamge path");
		Asset.diamondImage = g.CreateImage("diamondIamge path");
		Asset.loadingImage = g.CreateImage("loadingImage path");
		Asset.mainImage = g.CreateImage("bgImage path");
		//game.setScreen(this);
		game.setScreen(new MainScreen(game));
		return 0;
	}
	public int present(){
		g.DrawImage(Asset.loadingImage, 0,0);
		return 0;
	}
}
class MainScreen extends Screen{
	public MainScreen(Game game){
		super(game);
	}
}
class GameScreen extends Screen{
	public GameScreen(Game game){
		super(game);
	}
}
class EndScreen extends Screen{
	public EndScreen(Game game){
		super(game);
	}
}
