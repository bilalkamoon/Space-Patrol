package com.Apolonis.SpacePatrol;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
	
public class Shot {
	private static Bitmap bitmap;
	public int x,y;
	Shotmanager SM;
	float shot_speed,speed_upgrade;
	
	
	
	public Shot(Bitmap bitmap, int x, int y,float speed_upgrade){
		this.bitmap = bitmap;
		this.x=x;
		this.y=y;
		this.speed_upgrade=speed_upgrade;
	}
	
	public void setmanager(Shotmanager SM){
		this.SM = SM;
		shot_speed=SM.game_panel.ScreenWidth*speed_upgrade/2;
	}
	
	public static Bitmap getBitmap() {
		return bitmap;
	}
	
	public void setY(int y) {
		this.y=y;
		
	}
	
	public void setX(int x){
		this.x=x;
	}
	
	public void draw(Canvas canvas) {
		//drawing at the center of the barrier
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth()/2)+SM.ship.getwidth()/2, y - (bitmap.getHeight()/2), null);
		
	}
	
	
	
	public void update(float dt){
		x = (int) (x + shot_speed*dt);
	}
	
	public ArrayList<Point> GetArray() {
		//deifning the corners of the barrier
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
}
