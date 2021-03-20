package com.Apolonis.SpacePatrol;

import java.util.ArrayList;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


//gamepanel class is inherited from surfaceview, not activity
//it implements SurfaceHolder Callbacks
//actions are handled in a separate thread
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
	public float ShipSpeed;
	//we create the mainthread class, and define a variable "thread"
	public MainThread thread;
	
	//if the game is paused or not
	public boolean Pause_game;
	//background variable
	private Background background;
	boolean game_finished_loading=false;
	public Shotmanager SM;
	private Ship ship;
	private Bonus coin;
	private Monster monster_blue;
	private Monster monster_red;
	private Monster monster_green;
	private Monster monster_purple;
	private Monster monster_orange;
	public int ScreenWidth;
	public int Screenheight;
	public Game game;
	public BarrierManager BM;
	public ArrayList<Monster> Monsters;
	public ArrayList<Heart> Hearts;
	public Heart heart0,heart1,heart2,heart3;
	public int deathcounter=0;
	public float hitcounter=0;
	public RareObject clock;
	public RareObject rocket;
	public RareObject heart;
	public RareObject shield;
	boolean clock_timer = false;
	float clock_counter=0;
	int rockets_owned=0;
	MediaPlayer mp;
	boolean has_rockets=false;
	int level,shipnum;
	Level level_propreties;
	
	public Fired_Missle rocket_fired;
	
	
	
	//constructor that has 2 parameters, context and game
	//the context will be passed when called from inside the game
	public GamePanel(Context context, Game game, int ScreenWidth, int ScreenHeight,int level,int shipnum) {
		super(context);
		this.level=level;
		this.shipnum = shipnum;
		//the gamepanel will hold all classes of game elements
		//to create an endless loop, we use the thread
		//the following the 3 lines to allow us to interecept
		//the surface view event
		getHolder().addCallback(this);
		this.ScreenWidth = ScreenWidth;
		this.Screenheight = ScreenHeight;
		//thread is a new instance of mainthread
		//it has 2 parameters, "getholder" and "this" so he knows his parent
		thread = new MainThread(getHolder(),this);
		setFocusable(true);
		level_propreties = new Level(context, level, shipnum);
		background = new Background(level_propreties.bg, ScreenWidth, ScreenHeight, this);
		
		ship = new Ship(level_propreties.ship,100,1,ScreenWidth, ScreenHeight,level_propreties.ship_type,level_propreties.ship_array);
		ShipSpeed = ScreenWidth/2.f;
		
		
		
			SM = new Shotmanager(level_propreties.shot,this, ship);
		
		
		BM = new BarrierManager(BitmapFactory.decodeResource(getResources(), R.drawable.shot),this);
		BM.setScreen(ScreenWidth, ScreenHeight);
		coin = new Bonus(BitmapFactory.decodeResource(getResources(), R.drawable.coin_glow),ScreenWidth*5/4,-200);
		coin.setBarrierManager(BM);
		coin.randomizeY();
		clock = new RareObject(level_propreties.clock,5000,-200,1);
		clock.setBarrierManager(BM);
		clock.randomizeX(1);
		clock.randomizeY();
		rocket = new RareObject(level_propreties.rocket_tilted,5000,-200,0);
		rocket.setBarrierManager(BM);
		rocket.randomizeX(0);
		rocket.randomizeY();
		heart = new RareObject(level_propreties.heart, 0, 0,2);
		heart.setBarrierManager(BM);
		heart.randomizeX(2);
		heart.randomizeY();
		shield = new RareObject(level_propreties.shield, 0, 0,3);
		shield.setBarrierManager(BM);
		shield.randomizeX(3);
		shield.randomizeY();
		rocket_fired = new Fired_Missle(level_propreties.rocket_fired,5,0, ScreenWidth);
		

		
		Monsters = new ArrayList<Monster>();
		monster_green = new Monster(level_propreties.monster_green,ScreenWidth,-200);
		monster_blue = new Monster(level_propreties.monster_blue,ScreenWidth*6/5,-200);
		monster_red = new Monster(level_propreties.monster_red,ScreenWidth*7/5,-200);
		monster_orange = new Monster(level_propreties.monster_orange,ScreenWidth*8/5,-200);
		monster_purple = new Monster(level_propreties.monster_purple,ScreenWidth*9/5,-200);
		
		Monsters.add(monster_blue);
		Monsters.add(monster_red);
		Monsters.add(monster_orange);
		Monsters.add(monster_purple);
		Monsters.add(monster_green);
		Hearts = new ArrayList<Heart>();
		heart3= new Heart(level_propreties.heart_full,0,70);
		heart2= new Heart(level_propreties.heart_full,0,70);
		heart1= new Heart(level_propreties.heart_full,0,70);
		heart0= new Heart(level_propreties.heart_full,35+level_propreties.heart_full.getWidth()/2,70);
		heart1.setX(heart0.x + heart0.getBitmap().getWidth() +10);
		heart2.setX(heart1.x + heart0.getBitmap().getWidth() +10);
		heart3.setX(heart2.x + heart0.getBitmap().getWidth() +10);
		Hearts.add(heart0);
		Hearts.add(heart1);
		Hearts.add(heart2);
		if(MainMenu.extra_heart==1)
			Hearts.add(heart3);
		
		
		//we declare and array of bitmaps
		ArrayList<Bitmap> animation = new ArrayList<Bitmap>();
		//we fill the array with the animations
		animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom1));
		animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom2));
		animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom3));
		animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom4));
		ship.setBoomAnimation(animation);
		ArrayList<Bitmap> animation_big = new ArrayList<Bitmap>();
		//we fill the array with the animations
		animation_big.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom1_big));
		animation_big.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom2_big));
		animation_big.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom3_big));
		animation_big.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom4_big));
		rocket_fired.setBoomAnimation(animation_big);
		for(int i=0;i<5;i++){
			Monsters.get(i).setBarrierManager(BM);
			Monsters.get(i).randomizeY();
			Monsters.get(i).setBoomAnimation(animation);
		}
		this.game=game;
		
		
		
	}
	//we create an OnTouchEven handler since our game will
	//receive touch from the player anywhere on the screen
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(event.getAction()==MotionEvent.ACTION_DOWN)
			ship.up=true;
		if(event.getAction()==MotionEvent.ACTION_UP)
			ship.up=false;
			
		/*
		if(event.getAction()==MotionEvent.ACTION_UP)
			ship.increasespeed();
			*/
		return true;
		
	};
	//draw method to keep all our drawings
	public void draw(Canvas canvas){
		if(!Pause_game)
			if(canvas != null){
					//to fix the fuzziness between the barriers and the background
					// canvas.drawColor(Color.BLACK);
					background.draw(canvas);
					ship.draw(canvas);
					for(int i=0;i<5;i++)
						Monsters.get(i).draw(canvas);
					coin.draw(canvas);
					SM.draw(canvas);
					for(int i=0;i<Hearts.size();i++)
						Hearts.get(i).draw(canvas);
					clock.draw(canvas);
					rocket.draw(canvas);
					rocket_fired.draw(canvas);
					heart.draw(canvas);
					shield.draw(canvas);
			}
	}
	//update method to update the gameplay
	void update(float dt){
		
		ship.update(dt);
		if(ship.finished_dying){
			
			Message msg = BM.game_panel.game.handler.obtainMessage();
			msg.what=2;
			BM.game_panel.game.handler.sendMessage(msg);
		}
		if (!ship.death) {
			background.update(dt);
			SM.update(dt);
			coin.update(dt);
			rocket_fired.update(dt);
			if(rocket_fired.x>ScreenWidth/2)
				for(int i=0; i<5; i++)
					Monsters.get(i).inccounter();
				
				
			for(int j=0; j<5;j++){
				Monsters.get(j).update(dt);
				for(int i=0;i<SM.shots.size();i++){
				ArrayList<Point> temp = new ArrayList<Point>(SM.shots.get(i).GetArray());
				if(Monsters.get(j).bump(temp.get(0), temp.get(1), temp.get(2),
						temp.get(3))){
						if(Monsters.get(j).getdeathcounter()<1){
						SM.shots.get(i).setX(-200);
						SM.shots.get(i).setY(-200); }
						Monsters.get(j).inccounter();
				}
			}
				ArrayList<Point> temp2 = new ArrayList<Point>(Monsters.get(j).GetArray());	
				if(!ship.hit && Monsters.get(j).getdeathcounter()<1)
					if (ship.bump(temp2.get(0), temp2.get(1), temp2.get(2),
						temp2.get(3))){
						deathcounter++;
						ship.setHit(true);
						ship.setBitmap(level_propreties.ship_faded,level_propreties.ship_array_faded);
						
					}
			}
			if(ship.hit){
				hitcounter+=dt;
			}
			
			
			
			if(hitcounter>3){
				ship.setHit(false);
				ship.setBitmap(level_propreties.ship,level_propreties.ship_array);
				hitcounter=0;
			}
			
			
			for(int i=0; i<deathcounter;i++)
				Hearts.get(Hearts.size()-1-i).setBitmap(level_propreties.heart_empty);
			for(int i=Hearts.size()-1;i>deathcounter-1 ;i--)
				Hearts.get(Hearts.size()-1-i).setBitmap(level_propreties.heart_full);
			if(deathcounter==Hearts.size()){
				
				ship.death=true;
				MediaPlayer mp = MediaPlayer.create(game, R.raw.boom);
				mp.setVolume(0.3f, 0.3f);
				if(!mp.isPlaying() && MainMenu.sound==1)
					mp.start();
				//sending a message to the handler of the main game activity
				//that we crashed
				
					
				
				}
			
			//we fill an array of points and check for
			//collision with the coin
			ArrayList<Point> coin_point = new ArrayList<Point>(coin.GetArray());
			if (ship.bump(coin_point.get(0), coin_point.get(1), coin_point.get(2),
					coin_point.get(3))){
				//if we collide, we make the coin disappear
				//by moving it outside the screen
				coin.incX();
				coin.randomizeY();
				
				//sending a message to the handler of the main game activity
				//that we got a coin
				Message msg = BM.game_panel.game.handler.obtainMessage();
				msg.what=0;
				BM.game_panel.game.handler.sendMessage(msg);
				
			}
			clock.update(dt);
			ArrayList<Point> clock_point = new ArrayList<Point>(clock.GetArray());
			 if (ship.bump(clock_point.get(0), clock_point.get(1), clock_point.get(2),
				clock_point.get(3)))
			{
				ShipSpeed=ShipSpeed/2;
				clock_timer = true;
				clock.randomizeX(1);
				if(!clock.mp.isPlaying() && MainMenu.sound==1)
					 clock.mp.start();
			}
			 
			if(clock_timer){
				clock_counter+=dt;
				if(clock_counter>5){
					clock_counter=0;
					clock_timer=false;
					ShipSpeed=ShipSpeed*2;
				}
			}
			
				
			
			
			rocket.update(dt);
			ArrayList<Point> rocket_point = new ArrayList<Point>(rocket.GetArray());
			 if (ship.bump(rocket_point.get(0), rocket_point.get(1), rocket_point.get(2),
				rocket_point.get(3))){
				 rocket.randomizeX(0);;
				 Message msg = BM.game_panel.game.handler.obtainMessage();
				 msg.what=4;
				 BM.game_panel.game.handler.sendMessage(msg);
				 if(!rocket.mp.isPlaying() && MainMenu.sound==1)
					 rocket.mp.start();
			 }
			 
			 heart.update(dt);
			 ArrayList<Point> heart_point = new ArrayList<Point>(heart.GetArray());
			 if (ship.bump(heart_point.get(0), heart_point.get(1), heart_point.get(2),heart_point.get(3))){
				 heart.randomizeX(2);
				 if(deathcounter>0)
						deathcounter--;
				 if(!heart.mp.isPlaying() && MainMenu.sound==1)
					 heart.mp.start();
			 }
			 shield.update(dt);
			 ArrayList<Point> shield_point = new ArrayList<Point>(shield.GetArray());
			 if (ship.bump(shield_point.get(0), shield_point.get(1), shield_point.get(2),shield_point.get(3))){
				 shield.randomizeX(2);
				 if(ship.hit)
					 hitcounter=0;
				 ship.setHit(true);
				 ship.setBitmap(level_propreties.ship_faded,level_propreties.ship_array_faded);
				 if(!shield.mp.isPlaying() && MainMenu.sound==1)
					 shield.mp.start();
			 }
			 
			 
		//not intersection
			 boolean repeat;
			 do{
				 repeat=false;
				 
				 for(int i=0;i<5;i++){ //for each monster
					 if(Monsters.get(i).getX()>0) {
						 for(int j=1;j<5 && j!=i;j++){
							 ArrayList<Point> other_monster = new ArrayList<Point>(Monsters.get(j).GetArray());
							 if(Monsters.get(i).bump(other_monster.get(0), other_monster.get(1), other_monster.get(2), other_monster.get(3))){
								 Monsters.get(j).incX();
								 repeat=true;
						 }}
						 ArrayList<Point> other_coin = new ArrayList<Point>(coin.GetArray());
						 if(Monsters.get(i).bump(other_coin.get(0), other_coin.get(1), other_coin.get(2), other_coin.get(3))){
							 coin.randomizeY();
							 repeat=true;
						 }
						 ArrayList<Point> other_clock = new ArrayList<Point>(clock.GetArray());
						 if(Monsters.get(i).bump(other_clock.get(0), other_clock.get(1), other_clock.get(2), other_clock.get(3))){
							 clock.incX();
							 clock.randomizeY();
						 repeat=true;
					 }
						 ArrayList<Point> other_rocket = new ArrayList<Point>(rocket.GetArray());
						 if(Monsters.get(i).bump(other_rocket.get(0), other_rocket.get(1), other_rocket.get(2), other_rocket.get(3))){
							 rocket.incX();
							 rocket.randomizeY();
						 repeat=true;
						 }
						 
						 ArrayList<Point> other_heart = new ArrayList<Point>(heart.GetArray());
						 if(Monsters.get(i).bump(other_heart.get(0), other_heart.get(1), other_heart.get(2), other_heart.get(3))){
							 heart.incX();
							 heart.randomizeY();
						 repeat=true;
						 }
						 ArrayList<Point> other_shield = new ArrayList<Point>(shield.GetArray());
						 if(Monsters.get(i).bump(other_shield.get(0), other_shield.get(1), other_shield.get(2), other_shield.get(3))){
							 shield.incX();
							 shield.randomizeY();
						 repeat=true;
						 }
					 }
				 }
		}while(repeat);
	}}
	//because the class implements SurfaceHolder.Callback, 3 methods are needed
	// surfaceChanged, surfaceCreated and surfaceDestroyed
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(!thread.isAlive()){
			thread = new MainThread(getHolder(),this);
			thread.Setrunning(true);
			thread.start();}
		
	}
	
	public void sethasrockets(boolean b){
		has_rockets=b;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry= true;
		thread.Setrunning(false);
		while(retry){
			try{
				thread.join();
				retry=false;
			} catch (InterruptedException e){}
				
		}
		
	}
	
	
	
	public void setFireRocket() {
		rocket_fired.setFired();
		rocket_fired.setX(ship.x);
		rocket_fired.setY(ship.y + ship.getheight()/2 + rocket_fired.getBitmap().getHeight()/2 +5);
		
	}

	
}
