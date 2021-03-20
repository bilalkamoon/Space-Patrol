package com.Apolonis.SpacePatrol;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class Ship {
	public Bitmap bitmap;
	public int x,y;
	private int ScreenHeight;
	public ArrayList<Bitmap> Booms = null;
	boolean death;
	float vertSpeed;
	boolean up;
	boolean finished_dying=false;
	
	float animTime=0;
	//total animation time is one second
	float totalAnimTime = 1;
	float numFrames;
	int deathcounter=0;
	public boolean hit=false;
	int type;
	public ArrayList<Bitmap> array = null;
	float gifanimtime=0;
	float giftotalanimtime=0.6f;
	float gifnumframes;
	
	
	public Ship(Bitmap decodeResource, int x, int y, int screenWidth, int screenHeight,int type,ArrayList<Bitmap> array) {
		this.bitmap=decodeResource;
		this.x=x;
		this.y=y;
		death=false;
		vertSpeed = 0;
		this.type=type;
		this.array=array;
		ScreenHeight= screenHeight;
		if(type==2){
			gifnumframes=array.size();
		}
		
		
	}
		public void setBoomAnimation(ArrayList<Bitmap> animation){
			Booms = new ArrayList<Bitmap>(animation);
			numFrames=Booms.size();
		}
		
		public int getwidth(){
			return bitmap.getWidth();
		}
		
		public int getheight(){
			return bitmap.getHeight();
		}
		
		public void draw(Canvas canvas){
			
				if(!death){
					if(type==1)
						canvas.drawBitmap(bitmap, x- bitmap.getWidth()/2,y - bitmap.getHeight()/2, null);
					else{
						int index = (int) (gifanimtime/giftotalanimtime*gifnumframes);
						if(index<gifnumframes){
							canvas.drawBitmap(array.get(index), x - array.get(index).getWidth()/2, y - array.get(index).getHeight()/2,null);
						}
					}
						
				}
			else{
				// animTime/totalAnimeTime will go from 0 to 1
				// as index will go from 0 to 4
				int index = (int) (animTime/totalAnimTime*numFrames);
				if(index<numFrames){
					canvas.drawBitmap(Booms.get(index), x- bitmap.getWidth()/2,y - bitmap.getHeight()/2, null);
				}
			}
			
		}
		
		public void update(float dt){
			if(!death){
				if( y> ScreenHeight-bitmap.getHeight()/2){
				vertSpeed=1;
				y= ScreenHeight-bitmap.getHeight()/2-1;
				}
				if(y<bitmap.getHeight()/2){
					vertSpeed=-1;
					y=bitmap.getHeight()/2+1;
				}
				else{
					vertSpeed += ScreenHeight*dt*1.5;
				
				
				  if(up)
				 	vertSpeed -= ScreenHeight*dt*3.5;
					
				
				}
				y+= vertSpeed*dt;
				if(type==2){
					if(gifanimtime<giftotalanimtime)
						gifanimtime+=dt;
					else
						gifanimtime=0;
				}
					
				
			}
			else{
				
				if(animTime<1)
					animTime+=dt;
				else
					finished_dying=true;
					
			}
		}
		
		public boolean bump(Point OTL, Point OTR, Point OBR, Point OBL){
			Point BL = new Point();
			Point BR = new Point();
			Point TL = new Point();
			Point TR = new Point();
			
			ArrayList<Point> PointList = new ArrayList<Point>();
			PointList.add(OTL);
			PointList.add(OTR);
			PointList.add(OBR);
			PointList.add(OBL);
			
			getPoint(TL,TR,BR,BL);
			
			for (int i = 0; i<4; i++){
				if (BR.x>=PointList.get(i).x)
					if (TL.x<=PointList.get(i).x)
						if(PointList.get(i).y>=TL.y)
							if(PointList.get(i).y<=BR.y)
								return true;
			}
			PointList.clear();
			PointList.add(TL);
			PointList.add(TR);
			PointList.add(BR);
			PointList.add(BL);
			for (int i = 0; i<4; i++){
				if (OBR.x>=PointList.get(i).x)
					if (OTL.x<=PointList.get(i).x)
						if(PointList.get(i).y>=OTL.y)
							if(PointList.get(i).y<=OBR.y)
								return true;
			}
			return false;
		}
		private void getPoint(Point TL, Point TR, Point BR, Point BL) {
			TL.x = x-bitmap.getWidth() / 2;
	 	TL.y = y - bitmap.getHeight() / 2;
			
			TR.x = x+bitmap.getWidth() / 2;
			TR.y = y - bitmap.getHeight() / 2;
			
			BL.x = x-bitmap.getWidth() / 2;
			BL.y = y + bitmap.getHeight() / 2;
			
			BR.x = x+bitmap.getWidth() / 2;
			BR.y = y+bitmap.getHeight() / 2;
		}
		public void increasespeed() {
			vertSpeed = -ScreenHeight;
			
		}
		
		public void setBitmap(Bitmap bitmap,ArrayList<Bitmap> array){
			if(type==1)
				this.bitmap = bitmap;
			else
				this.array=array;
		}
		
		public void setHit(boolean b){
			this.hit=b;
		}
}
