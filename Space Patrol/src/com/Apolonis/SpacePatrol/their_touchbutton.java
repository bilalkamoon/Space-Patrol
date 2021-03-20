package com.Apolonis.SpacePatrol;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class their_touchbutton implements OnTouchListener {
	RelativeLayout IV;
	
	public their_touchbutton(RelativeLayout ImageBtn){
		IV = ImageBtn;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		//on touch, change image to "button pressed"
		case MotionEvent.ACTION_DOWN:
			IV.setBackgroundResource(R.drawable.btn_s);
			break;
		//when we stop touch, return to original state
		case MotionEvent.ACTION_UP:
			IV.setBackgroundResource(R.drawable.btn);
			break;
		//when we move outside, return to original state too	
		case MotionEvent.ACTION_OUTSIDE:
			IV.setBackgroundResource(R.drawable.btn);
			break;
		default:
			IV.setBackgroundResource(R.drawable.btn);
			break;}
		return false;
	
	}
}