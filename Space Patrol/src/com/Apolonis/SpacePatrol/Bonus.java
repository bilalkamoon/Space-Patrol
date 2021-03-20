package com.Apolonis.SpacePatrol;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class Bonus {
	private Bitmap bitmap;
	private int x;
	private int y;
	//we add the barriermanager so we can know the corridor
	BarrierManager BM;
	Random r= new Random(); 
	
	
	public Bonus(Bitmap decodeResource, int x, int y) {
		this.x=x;
		this.y=y;
		bitmap = decodeResource;
	
	}
	
	public void setBarrierManager(BarrierManager BM){
		this.BM= BM;
	}
	
	public Bitmap getBitmap(){
		return bitmap;
	}
	
	public ArrayList<Point> GetArray() {
		//deifning the corners of the coin
		Point TL = new Point(), TR = new Point(), BL = new Point(), BR = new Point();
		//setting the x and y value for  each point
		TL.x = x-bitmap.getWidth() / 2;
		TL.y = y - bitmap.getHeight() / 2;
		
		TR.x = x+bitmap.getWidth() / 2;
		TR.y = y - bitmap.getHeight() / 2;
		
		BL.x = x-bitmap.getWidth() / 2;
		BL.y = y + bitmap.getHeight() / 2;
		
		BR.x = x+bitmap.getWidth() / 2;
		BR.y = y+bitmap.getHeight() / 2;
		
		//adding the four points in an array and returning it
		ArrayList<Point> temp = new ArrayList<Point>();
		temp.add(TL);
		temp.add(TR);
		temp.add(BR);
		temp.add(BL);
		return temp;
	}

	public void draw(Canvas canvas){
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}
	
	public void update(float dt){
		//if the coin went outside the screen
		if (x<-BM.game_panel.ScreenWidth/4)
		{	//the coin appears at the other side of the screen
			x=BM.game_panel.ScreenWidth+bitmap.getWidth()/2;
			
			//y is random but inside the corridor
			y=bitmap.getHeight() + r.nextInt(BM.game_panel.Screenheight-2*bitmap.getHeight());  
		}
		//to make the coin moves
		x -=BM.game_panel.ShipSpeed*dt;
	}

	public void setX(int i) {
		this.x=i;
	}
	public void randomizeY(){
		y=bitmap.getHeight() + r.nextInt(BM.game_panel.Screenheight-2*bitmap.getHeight());
	}
	
	public void incX(){
		x+=BM.game_panel.getWidth()*5/4;
	}
	public void setY(int i) {
		this.y=i;
	}
}

