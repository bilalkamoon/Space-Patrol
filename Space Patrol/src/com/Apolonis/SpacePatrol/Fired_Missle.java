package com.Apolonis.SpacePatrol;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
	
public class Fired_Missle {
	private static Bitmap bitmap;
	public int x,y,ScreenWidth;
	public ArrayList<Bitmap> Booms = null;
	float animTime=0;
	//total animation time is one second
	float totalAnimTime = 1;
	float numFrames;
	boolean fired=false;
	
	
	public Fired_Missle(Bitmap bitmap, int x, int y, int ScreenWidth){
		this.bitmap = bitmap;
		this.x=x;
		this.y=y;
		this.ScreenWidth = ScreenWidth;
		
	}
	
	public void setBoomAnimation(ArrayList<Bitmap> animation){
		Booms = new ArrayList<Bitmap>(animation);
		numFrames=Booms.size();
	}
	
	public void setFired(){
		this.fired=true;
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
		if(fired){
			if(x>= ScreenWidth/2){
				int index = (int) (animTime/totalAnimTime*numFrames);
				if(index<numFrames){
					canvas.drawBitmap(Booms.get(index), x- bitmap.getWidth()/2,y - bitmap.getHeight()/2, null);
				}
			}
			else
				canvas.drawBitmap(bitmap, x - (bitmap.getWidth()/2), y - (bitmap.getHeight()/2), null);
		}
				
		
	}
	
	
	
	public void update(float dt){
		if(fired){
			if(x>= ScreenWidth/2){
				animTime+=dt;
				if(animTime>1){
					animTime=0;
					fired=false;
					x=-200;
				}}
			else
				x = (int) (x + ScreenWidth*dt);
			}
		}
	
	
}
