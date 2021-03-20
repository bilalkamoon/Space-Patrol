package com.Apolonis.SpacePatrol;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {
	
	Bitmap bgbitmap;
	//coordinates
	int x,y;
	//screen width
	int screenwidth,screenheight;
	//the max nb of bg that can be placed on the screen
	int count_bg;
	
	GamePanel root_gamepanel;
	//constructor
	public Background(Bitmap bitmap, int screen_w, int screenheight, GamePanel game_panel){
	this.bgbitmap = bitmap;
	this.screenheight = screenheight;
	this.x=0;
	this.y=screenheight-bgbitmap.getHeight();
	this.screenwidth = screen_w;
	count_bg = screenwidth/bgbitmap.getWidth()+1;
	this.root_gamepanel = game_panel;
	
	
	}
	
	public void draw(Canvas canvas){
		for(int i=0; i<count_bg+1;i++){
			if(canvas != null)
				canvas.drawBitmap(bgbitmap, bgbitmap.getWidth()*i+x, y, null);
		}
		if (Math.abs(x)>bgbitmap.getWidth())
		{
			x = x +bgbitmap.getWidth();
		}
	}
	
	public void update(float dt){
		x = (int) (x - root_gamepanel.ShipSpeed*dt);
	}
}
