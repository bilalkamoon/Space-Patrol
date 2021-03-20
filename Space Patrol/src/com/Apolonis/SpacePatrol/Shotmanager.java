package com.Apolonis.SpacePatrol;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;

public class Shotmanager {
	Ship ship;
	Bitmap bitmap;
	public GamePanel game_panel;
	int n;
	float counter=0;
	MediaPlayer mp;
	
	ArrayList<Shot> shots= null;

	public Shotmanager(Bitmap decodeResource, GamePanel gamePanel,Ship ship) {
		bitmap = decodeResource;
		game_panel = gamePanel;
		this.ship=ship;
		shots = new ArrayList<Shot>();
		n= (int) game_panel.ScreenWidth/bitmap.getWidth();
		mp = MediaPlayer.create(gamePanel.getContext(),R.raw.shot);
		mp.setVolume(0.4f, 0.4f);
		
	}
	
	public void draw(Canvas canvas){
		if(shots!=null)
			for(int i=0;i<shots.size();i++)
				shots.get(i).draw(canvas);
	}
	
	public void update(float dt){
		counter+=dt;
		
		if(counter>0.8/MainMenu.speed_upgrade){
			if(MainMenu.shotnb==1){
				Shot shot = new Shot(bitmap,ship.x,ship.y,MainMenu.speed_upgrade);
				shot.setmanager(this);
				shots.add(shot);
			}
			else if(MainMenu.shotnb==2){
				Shot shot1= new Shot(bitmap,ship.x,ship.y+ship.getheight()/4,MainMenu.speed_upgrade);
				Shot shot2= new Shot(bitmap,ship.x,ship.y-ship.getheight()/4,MainMenu.speed_upgrade);
				shot1.setmanager(this);
				shot2.setmanager(this);
				shots.add(shot1);
				shots.add(shot2);
			}
				
			if(MainMenu.sound==1 && !mp.isPlaying())
				mp.start();
			
			counter=0;
		}
		
		if(shots!=null)
			for(int i=0;i<shots.size();i++){
				if(shots.get(i).x > game_panel.ScreenWidth){
					shots.remove(i);
					
				}
				shots.get(i).update(dt);
			}
		
	}

}
