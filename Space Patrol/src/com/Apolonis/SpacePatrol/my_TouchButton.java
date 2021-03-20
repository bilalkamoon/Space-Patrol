package com.Apolonis.SpacePatrol;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

public class my_TouchButton implements OnTouchListener {
	View IV;
	
	public my_TouchButton(View ImageBtn){
		IV = ImageBtn;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		//on touch, change image to "button pressed"
		case MotionEvent.ACTION_DOWN:
			IV.setBackgroundResource(R.drawable.buttonshape_pressed);
			break;
		//when we stop touch, return to original state
		case MotionEvent.ACTION_UP:
			IV.setBackgroundResource(R.drawable.buttonshape);
			break;
		//when we move outside, return to original state too	
		case MotionEvent.ACTION_OUTSIDE:
			IV.setBackgroundResource(R.drawable.buttonshape);
			break;
		default:
			IV.setBackgroundResource(R.drawable.buttonshape);
			break;}
		return false;
	
	}
}