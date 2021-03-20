package com.Apolonis.SpacePatrol;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BarrierManager {
	
	Bitmap center;
	int shipHeight;
	int Num;
	int screenH;
	int dl;
	int TargetY =-1;
	int dpos;
	public GamePanel game_panel;
	
	ArrayList<Barier>  TopWalls = null;
	ArrayList<Barier>  BottomWalls = null;

	public BarrierManager(Bitmap decodeResource, GamePanel game_panel) {
		center = decodeResource;
		this.game_panel = game_panel;
	}

	void setShipHeight(int h) {
		this.shipHeight = h;
	}
	//to determine how many barriers to draw on the screen
	//and allocate them
	public void setScreen(int width, int height){
		screenH = height;
		//number of barriers to draw
		Num = (width)/center.getWidth()+4;
		TopWalls = new ArrayList<Barier>();
		BottomWalls = new ArrayList<Barier>();
		for (int i = 0; i<Num+1; i++){
			//width+200 to make the barriers appear behind the screen
			Barier BB = new Barier(center, width+200+center.getWidth()*i, 0);
			BB.setManager (this);
			//we fill the arrays with the barriers we want to draw
			TopWalls.add(BB);
			Barier BBB = new Barier(center, width+200+center.getWidth()*i, 0);
			BBB.setManager (this);
			BottomWalls.add(BBB);
		}
		GenerateY();
	}

	private void GenerateY() {
		int h = center.getHeight()/2;
		//variable of the width of the corridor
		dl = screenH;
		//variable of the middle of the corridor
		dpos =screenH/2;
		//fixed width of the corridor
		int new_dl = screenH*3/5;
		//how much we will deduct each barrier
		int inc =  (dl-new_dl)/Num;
		for (int i = 0; i<Num+1; i++){
			dl = dl - inc;
			h =TopWalls.get(i).getBitmap().getHeight()/2;
			TopWalls.get(i).setY(dpos -dl/2-h);
			BottomWalls.get(i).setY(dpos +dl/2+h);
			
		}
	}
	
	public void draw(Canvas canvas){
		for (int i=0;i<Num+1; i++){
			TopWalls.get(i).draw(canvas);
			BottomWalls.get(i).draw(canvas);
		}
	}
	public void update(float dt){
		for (int i=0;i<Num+1; i++){
			TopWalls.get(i).update(dt, true);
			BottomWalls.get(i).update(dt, false);
		}
	}
}
