package com.Apolonis.SpacePatrol;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

public class Level extends SurfaceView {
	Bitmap ship,ship_faded,shield,monster_blue,monster_green,monster_red,
	monster_purple,monster_orange,clock,rocket_tilted,rocket_fired,heart,bg,heart_full,heart_empty,shot;
	int monster_small,rocket_small,ship_type;
	ArrayList<Bitmap> ship_array,ship_array_faded;
	
	
	public Level(Context context, int level, int shipnum){
		super(context);
		if(level==1){
			shield = BitmapFactory.decodeResource(getResources(), R.drawable.shield);
			monster_blue = BitmapFactory.decodeResource(getResources(), R.drawable.blue_monster_small);
			monster_green = BitmapFactory.decodeResource(getResources(), R.drawable.green_monster_small);
			monster_red = BitmapFactory.decodeResource(getResources(), R.drawable.red_monster_small);
			monster_orange = BitmapFactory.decodeResource(getResources(), R.drawable.orange_monster_small);
			monster_purple = BitmapFactory.decodeResource(getResources(), R.drawable.purple_monster_small);
			clock = BitmapFactory.decodeResource(getResources(), R.drawable.clock_small);
			rocket_tilted = BitmapFactory.decodeResource(getResources(), R.drawable.rocket_small_tilted);
			rocket_fired = BitmapFactory.decodeResource(getResources(), R.drawable.rocket_small);
			heart = BitmapFactory.decodeResource(getResources(), R.drawable.heart_small);
			bg = BitmapFactory.decodeResource(getResources(), R.drawable.game_fon);
			monster_small = R.drawable.green_monster_very_small;
			rocket_small = R.drawable.rocket_very_small;
			heart_full = BitmapFactory.decodeResource(getResources(), R.drawable.heart_very_small);
			heart_empty = BitmapFactory.decodeResource(getResources(), R.drawable.heart_faded_very_small);
			
			
		}
		if(level==2){
			
			shield = BitmapFactory.decodeResource(getResources(), R.drawable.shield);
			monster_blue = BitmapFactory.decodeResource(getResources(), R.drawable.blue_ghost);
			monster_green = BitmapFactory.decodeResource(getResources(), R.drawable.green_ghost);
			monster_red = BitmapFactory.decodeResource(getResources(), R.drawable.red_ghost);
			monster_orange = BitmapFactory.decodeResource(getResources(), R.drawable.orange_ghost);
			monster_purple = BitmapFactory.decodeResource(getResources(), R.drawable.purple_ghost);
			clock = BitmapFactory.decodeResource(getResources(), R.drawable.clock_small);
			rocket_tilted = BitmapFactory.decodeResource(getResources(), R.drawable.rocket_small_tilted);
			rocket_fired = BitmapFactory.decodeResource(getResources(), R.drawable.rocket_small);
			heart = BitmapFactory.decodeResource(getResources(), R.drawable.cherry);
			bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg_2);
			monster_small = R.drawable.dead_ghost;
			rocket_small= R.drawable.rocket_very_small;
			heart_full = BitmapFactory.decodeResource(getResources(), R.drawable.cherry_small);
			heart_empty = BitmapFactory.decodeResource(getResources(), R.drawable.cherry_small_faded);
		}
		if(shipnum==1){
			shot = BitmapFactory.decodeResource(getResources(), R.drawable.shot);
			ship_type=1;
			ship = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship_small);
			ship_faded = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship_small_faded);
			ship_array=null;
			ship_array_faded=null;
		}
		if(shipnum==2){
			ship_type=2;
			ship_array = new ArrayList<Bitmap>();
			ship_array.add(BitmapFactory.decodeResource(getResources(), R.drawable.pac_man1));
			ship_array.add(BitmapFactory.decodeResource(getResources(), R.drawable.pac_man2));
			ship_array.add(BitmapFactory.decodeResource(getResources(), R.drawable.pac_man3));
			ship = BitmapFactory.decodeResource(getResources(), R.drawable.pac_man1);
			ship_faded = BitmapFactory.decodeResource(getResources(), R.drawable.pac_man1_faded);
			ship_array_faded = new ArrayList<Bitmap>();
			ship_array_faded.add(BitmapFactory.decodeResource(getResources(), R.drawable.pac_man1_faded));
			ship_array_faded.add(BitmapFactory.decodeResource(getResources(), R.drawable.pac_man2_faded));
			ship_array_faded.add(BitmapFactory.decodeResource(getResources(), R.drawable.pac_man3_faded));
			shot= BitmapFactory.decodeResource(getResources(), R.drawable.pacmanshot);
		}
		
	}
	
	
	
	
}
