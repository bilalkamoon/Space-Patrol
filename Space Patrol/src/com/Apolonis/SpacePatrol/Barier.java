package com.Apolonis.SpacePatrol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class Barier {
	
	private Bitmap bitmap;
	private int x,y;
	BarrierManager BM;
	boolean doit;

	public Barier(Bitmap center, int x, int y) {
		this.bitmap = center;
		this.x = x;
		this.y = y;
		
	}

	public void setManager(BarrierManager barrierManager) {
		BM = barrierManager;
		
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setY(int y) {
		this.y=y;
		
	}

	public void draw(Canvas canvas) {
		//drawing at the center of the barrier
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth()/2), y - (bitmap.getHeight()/2), null);
		
	}

	public void update(float dt, boolean upper) {
		//update method to make the barriers that cross the screen
		//get drawn on the other side
		if (x<-bitmap.getWidth()){
			if (upper)
			{	//to make the barriers placement smooth, we will create a random
				//point, to which the barriers will be directed, when they reach
				//that point, we create a new random point
				if (Math.abs(BM.TargetY-BM.dpos)<50)
					doit = true;
				if ((BM.TargetY==-1)||doit){
					BM.TargetY = new Random().nextInt(BM.screenH - BM.dl/2)+BM.dl/4;
				}
				if (BM.dpos<BM.TargetY)
					BM.dpos = BM.dpos + new Random().nextInt(15);
				else
					BM.dpos = BM.dpos - new Random().nextInt(15);	
				y = BM.dpos - BM.dl/2 -bitmap.getHeight()/2;
			}
			else
			{
				y = BM.dpos + BM.dl/2+bitmap.getHeight()/2;
			}	
			x=(int) (x +bitmap.getWidth()*(BM.TopWalls.size()-1));
		}
		
	x = (int) (x - BM.game_panel.ShipSpeed*dt);
		
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
