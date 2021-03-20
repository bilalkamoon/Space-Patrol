package com.Apolonis.SpacePatrol;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;

public class RareObject {
	private Bitmap bitmap;
	private int x;
	private int y;
	//we add the barriermanager so we can know the corridor
	BarrierManager BM;
	Random z = new Random();
	Random r = new Random();
	MediaPlayer mp;
	int type;
	
	
	public RareObject(Bitmap decodeResource, int x, int y, int type) {
		this.x=x;
		this.y=y;
		bitmap = decodeResource;
		this.type=type;
	}
	
	public void setBarrierManager(BarrierManager BM){
		this.BM= BM;
		mp = MediaPlayer.create(BM.game_panel.getContext(), R.raw.item_pickup);
		mp.setVolume(0.4f, 0.4f);
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
		if (x<-bitmap.getWidth()/2)
		{	
			//the coin appears at the other side of the screen
			randomizeX(type);
			//y is random but inside the corridor
			randomizeY();
		}
		//to make the coin moves
		x -=BM.game_panel.ShipSpeed*dt;
	}

	public void setX(int i) {
		this.x=i;
	}

	public void setY(int i) {
		this.y=i;
	}
	
	public void randomizeX(int j){
		x=(z.nextInt(22)+3)*BM.game_panel.ScreenWidth + j*BM.game_panel.ScreenWidth/4;
		randomizeY();
	}
	public void incX(){
		x+=BM.game_panel.ScreenWidth;
	}
	public void randomizeY(){
		y=bitmap.getHeight() + r.nextInt(BM.game_panel.Screenheight-2*bitmap.getHeight()); 
	}
}

