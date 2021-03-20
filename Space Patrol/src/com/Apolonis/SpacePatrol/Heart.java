package com.Apolonis.SpacePatrol;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Heart {
	Bitmap bitmap;
	int x,y;
	public Heart(Bitmap decodeResource, int x, int y) {
		bitmap = decodeResource;
		this.x=x;
		this.y=y;
	}
	
	public void draw(Canvas canvas){
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}

	public void update(float dt){
		
	}
	
	public void setX(int x){
		this.x=x;
	}
	
	public Bitmap getBitmap(){
		return bitmap;
	}
	
	public void setBitmap(Bitmap bitmap){
		this.bitmap=bitmap;
	}
}
