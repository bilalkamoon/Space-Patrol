package com.Apolonis.SpacePatrol;

import android.graphics.Canvas;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Panel {
	int coin=0;
	int monsterskilled=0;
	int health=2;
	int x,y;
	LinearLayout VL;
	ImageView coin_view;
	ImageView monster_view;
	TextView coin_text;
	TextView monster_text;
	LinearLayout HL1;
	LinearLayout HL2;
	
	public Panel(int x, int y){
		this.x=x;
		this.y=y;
	}

	public void inccoin(){
		coin++;
	}

	public void incmonsterskilled(){
		monsterskilled++;
	}

	public void draw(Canvas canvas){
		
		
	}
	
	public void update(float dt){
		
	}
}
