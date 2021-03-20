package com.Apolonis.SpacePatrol;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Message;

public class Monster {
	private Bitmap bitmap;
	private int x;
	private int y;
	private int inc;
	private int deathcounter=0;
	MediaPlayer mp;
	
	public ArrayList<Bitmap> Booms = null;
	//we add the barriermanager so we can know the corridor
	BarrierManager BM;
	float animTime=0;
	//total animation time is one second
	double totalAnimTime = 0.5;
	float numFrames;
	Random r = new Random();
	
	public Monster(Bitmap decodeResource, int x, int y) {
		this.x=x;
		this.y=y;
		bitmap = decodeResource;
		inc=0;
		
	}

	public void setBarrierManager(BarrierManager BM){
		this.BM= BM;
		mp = MediaPlayer.create(BM.game_panel.getContext(), R.raw.boom);
		mp.setVolume(0.3f, 0.3f);
	}
	
	public void setBoomAnimation(ArrayList<Bitmap> animation){
		Booms = new ArrayList<Bitmap>(animation);
		numFrames=Booms.size();
	}
	
	public Bitmap getBitmap(){
		return bitmap;
	}
	
	public void inccounter(){
		if(x>-bitmap.getWidth()/2 && x<BM.game_panel.ScreenWidth+bitmap.getWidth()/2)
			deathcounter++;
	}

	public void draw(Canvas canvas) {
			if(deathcounter>=1){
				if(MainMenu.sound==1 && !mp.isPlaying())
					mp.start();
				int index = (int) (animTime/totalAnimTime*numFrames);
				if(index<numFrames)
					canvas.drawBitmap(Booms.get(index), x- bitmap.getWidth()/2,y - bitmap.getHeight()/2, null);
			}
			else
				canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	
			
	}
	
	public void setX(int x){
		this.x=x;
		
	}
	public int getdeathcounter(){
		return deathcounter;
	}
	public void update(float dt) {
		
		if (x<-bitmap.getWidth()/2 )
		{	//the monster appears at the other side of the screen
			incX(); 
		}
		
		
		if(deathcounter>=1 && animTime>0.5){
			Message msg = BM.game_panel.game.handler.obtainMessage();
			msg.what=3;
			BM.game_panel.game.handler.sendMessage(msg);
			incX();
			deathcounter=0;
			animTime=0;
		}
		//to make the monster moves
		
			x -= BM.game_panel.ShipSpeed*dt;
		if(deathcounter>=1){
			animTime+=dt;
			
		}
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

	public int getX() {
		return x;
	}
	
	public void randomizeY(){
		y=bitmap.getHeight() + r.nextInt(BM.game_panel.Screenheight-2*bitmap.getHeight()); 
	}
	
	public void incX(){
		x+= BM.game_panel.ScreenWidth*6/5;
		randomizeY();
	}
}
