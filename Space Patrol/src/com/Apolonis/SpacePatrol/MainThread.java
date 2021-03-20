package com.Apolonis.SpacePatrol;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

//mainthread class, inherits Thread
public class MainThread extends Thread {
	//surfaceholder variable that will keep our main surface holder
	private SurfaceHolder surfaceholder;
	//gamepanel variable so that our thread can call for it 
	private GamePanel gamepanel;
	//to show if our thread is running or not
	private boolean running;
	float Dt;
	//constructor with 2 parameters, surfaceholder and gamepanel
	public MainThread(SurfaceHolder holder, GamePanel gamePanel) {
		//filling parameters
		this.gamepanel=gamePanel;
		this.surfaceholder=holder;
		Dt=0;
	}
	//a method to pass the running state of the game
	//to the running variable in mainthread
	void Setrunning(boolean running){
		this.running=running;
	}
	//"run" method where we will implement our infinite loop
	@Override
	public void run(){
		//the canvas which we will draw on
		Canvas canvas;
		try {
			synchronized(this){
			wait(500);}
		} catch (InterruptedException e) {
			
		}
		//the famous infinite loop
		while(running){
			//if the game is not paused
			if(!gamepanel.Pause_game){
				long startdraw= System.currentTimeMillis();
				canvas = null;
				try{
					canvas = this.surfaceholder.lockCanvas();
							synchronized (surfaceholder) {
								gamepanel.update(Dt);
								gamepanel.draw(canvas);
							}
				}
				
				finally{
					if(canvas!=null){
						surfaceholder.unlockCanvasAndPost(canvas);
					}
				}
				
				long enddraw = System.currentTimeMillis();
				Dt=(enddraw - startdraw)/1000.f;
			}
		}
	}
}
