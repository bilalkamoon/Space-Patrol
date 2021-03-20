package com.Apolonis.SpacePatrol;



import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.games.Games;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas.VertexMode;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CalendarContract.Colors;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Game extends Activity {
	
	final static int UPDATE_SCORE = 0;
	final static int DEATH = 1;
	final static int LOSE = 2;
	final static int KILL = 3;
	final static int GOT_ROCKET = 4;
	final static int FIRED_ROCKET = 5;
	
	
	//defining layouts to be used
	RelativeLayout Rel_main_game;
	//views created from dedicated xml files
	View PauseButton;
	View PauseMenu;
	View WinDialogue;
	View LoseDialogue;
	View HighScoreDialogue;
	LinearLayout VL;
	ImageView coin_view;
	ImageView monster_view;
	TextView coin_text;
	TextView lose;
	TextView monster_text;
	LinearLayout HL1;
	LinearLayout HL2;
	InterstitialAd mInterstitialAd;
	int ad_counter,rate_counter;
	SharedPreferences prefs;
	int ad;
	int level,ship;
	Level level_prop;
	
	int[] location = new int[2];
	
	
	MediaPlayer main_mp;
	
	//defining gamepanel class
	GamePanel game_panel;
	
	
	int score;
	int monsterskilled=0;
	static int rockets_owned=0;
	int ScreenHeight,ScreenWidth;
	
	TextView txtmonsterskilled;
	TextView txtscore;
	RelativeLayout RL;
	TextView txtscoreview;
	TextView txt_rockets_owned;
	RelativeLayout RL1;
	RelativeLayout RL2;
	ImageView pause_button;
	Editor editor;
	int highscore;
	MainMenu main_menu;
	LinearLayout LL_rocket;
	
	//a handler to send messages between activities
	//[should be "final"]
	final Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what==UPDATE_SCORE){
				i_get_coin();
			}
			if(msg.what==DEATH){
				//we send to the handler 3 seconds later that we lost
				postDelayed(new Runnable(){
					@Override
					public void run(){
						Message msg = handler.obtainMessage();
						msg.what=LOSE;
						handler.sendMessage(msg);
					}
				}, 2000);
			}
			if(msg.what==LOSE){
				i_lose();
			}
			if(msg.what==KILL)
				incmonsterskilled();
			
			if(msg.what==GOT_ROCKET)
				i_got_rocket();
			
			
			super.handleMessage(msg);
		}


		
	};
	
	//defining 3 onclicklisteners and their actions
	
	//when clicking on Main Menu button
	OnClickListener MainMenu_List = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			game_panel.thread.Setrunning(false);
			Game.this.finish();
			if(main_mp.isPlaying())
				main_mp.stop();
			
				ad_counter++;
				editor.putInt("ad_counter",ad_counter);
				editor.commit();
				rate_counter++;
				editor.putInt("rate_counter", rate_counter);
				editor.commit();
			
			if(ad_counter==3){
				ad_counter=0;
				editor.putInt("ad_counter",ad_counter);
				editor.commit();
				if(mInterstitialAd.isLoaded()){
					mInterstitialAd.show();
					ad=MainMenu.sound;
					MainMenu.setSound(0);
				}
				
			}
			
			if(rate_counter==50){
				rate_counter=0;
				editor.putInt("rate_counter", rate_counter);
				editor.commit();
				MainMenu.setRateV();
				MainMenu.setlevelviewclickable(false);
			}
					
				
				
				
			}
			
		};
	
    OnClickListener fire_listener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if(rockets_owned!=0){
				game_panel.setFireRocket();
				rockets_owned--;
				txt_rockets_owned.setText("x"+ rockets_owned);
				if(rockets_owned==0)
					LL_rocket.setBackgroundResource(R.drawable.red_button_small);
			}
		}
	};
	
	//when clicking on Continue button
    OnClickListener Cont_List = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			PauseMenu.setVisibility(View.GONE);
			pause_button.setVisibility(View.VISIBLE);
			game_panel.Pause_game=false;
			if(!main_mp.isPlaying() && MainMenu.sound==1)
				main_mp.start();
			
		}
	};
	//when clicking the Pause button
	OnClickListener pause_click = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			pause_button.setVisibility(View.GONE);
			PauseMenu.setVisibility(View.VISIBLE);
			game_panel.Pause_game=true;
			if(main_mp.isPlaying())
				main_mp.pause();
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		level= MainMenu.level;
		ship= MainMenu.ship;
		level_prop = new Level(this,level,ship);
		//linking to xml
		setContentView(R.layout.game);
		//we define the whole layout as variable because we need to use it (make it appear/disappear)
		Rel_main_game = (RelativeLayout) findViewById(R.id.main_game_rl);
		prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
		highscore = prefs.getInt("highscore", 0);
		score=prefs.getInt("score",0);
		ad_counter=prefs.getInt("ad_counter",0);
		rate_counter = prefs.getInt("rate_counter", 0);
		editor = prefs.edit();
		
		//code to get the screen's metrics
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		final int heights = dm.heightPixels;
		final int widths = dm.widthPixels;
		ScreenHeight = heights;
		ScreenWidth = widths;
		
		//we define the game_panel variable, and we pass some parameters
		//to our selves, like the appplication context
		game_panel = new GamePanel(getApplicationContext(),this, widths, heights,level,ship);
		//adding game panel view
		Rel_main_game.addView(game_panel);
		
		Typeface Custom = Typeface.createFromAsset(getAssets(), "font.ttf");
		
		/*
		//score
		//adding new relative view
		RelativeLayout RR = new RelativeLayout(this);
		//setting btn image as background
		RR.setBackgroundResource(R.drawable.btn);
		//?
		RR.setGravity(Gravity.CENTER);
		//setting the size
		Rel_main_game.addView(RR);
		
		//setting position
		RR.setX(0);
		
		LinearLayout LL = new LinearLayout(this);
		
		LL.setOrientation(LinearLayout.VERTICAL);
		RR.addView(LL);
		
		//defining new text view
		txtscore= new TextView(this);
		//defining font
		
		//setting font
		txtscore.setTypeface(Custom);
		txtscore.setPadding(10, 30, 10, 5);
		txtscore.setTextColor(Color.YELLOW);
		txtscore.setText("Score: " + score);
		//adding the text view to our relative view
		LL.addView(txtscore);
		txtmonsterskilled= new TextView(this);
		txtmonsterskilled.setTypeface(Custom);
		txtmonsterskilled.setTextColor(Color.YELLOW);
		txtmonsterskilled.setPadding(10, 5, 10, 10);
		txtmonsterskilled.setText("Monsters Killed: " + monsterskilled);
		LL.addView(txtmonsterskilled);
		*/
		
		// monster_text.setY(monster_view.getTop()+monster_view.getHeight()/2);
		
		
		
		
		//creating an inflater, to add layouts inside layout
		LayoutInflater myInflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
		/*
		//inflating the pause button
		PauseButton = myInflater.inflate(R.layout.pause, null,false);	
		//setting position of pause button
		PauseButton.setX(widths-120);
		PauseButton.setY(0);
		//adding the pause button view
		Rel_main_game.addView(PauseButton);
		
		//defining the pause button as an image view so we can apply my_touchbutton
		ImageView pauseImage = (ImageView) PauseButton.findViewById(R.id.pauseimage2);
		PauseButton.setOnTouchListener(new my_TouchButton(pauseImage));
		
		//setting the size of the pause button
		PauseButton.getLayoutParams().height=120;
		PauseButton.getLayoutParams().width=120;
		*/
		//inflating the pause menu
		PauseMenu = myInflater.inflate(R.layout.pause_menu, null, false);
		//adding the pause menu view
		Rel_main_game.addView(PauseMenu,widths*3/4,heights*3/4);
		PauseMenu.setX(widths/8);
		PauseMenu.setY(heights/8);
		
		Button continue_btn = (Button) PauseMenu.findViewById(R.id.mm_btn);
		Button mainmenu_btn = (Button) PauseMenu.findViewById(R.id.mainmenu_btn);
		continue_btn.setTypeface(Custom);
		mainmenu_btn.setTypeface(Custom);
		continue_btn.setTransformationMethod(null);
		mainmenu_btn.setTransformationMethod(null);

		PauseMenu.setVisibility(View.GONE);
		
		//defining continue and main_menu buttons as image views
		//so we can apply to them the my_touchbutton class
		
		continue_btn.setOnTouchListener(new my_TouchButton(continue_btn));
		mainmenu_btn.setOnTouchListener(new my_TouchButton(mainmenu_btn));
		//assigning the click listeners to the buttons
		continue_btn.setOnClickListener(Cont_List);
		mainmenu_btn.setOnClickListener(MainMenu_List);
		// PauseButton.setOnClickListener(pause_click);
		
		
		
		
		
		//Lose dialogue
		LoseDialogue = myInflater.inflate(R.layout.lose, null,false);
		Rel_main_game.addView(LoseDialogue,widths*3/4,heights*3/4);
		LoseDialogue.setX(widths/8);
		LoseDialogue.setY(heights/8);
		//defining the button as an image view so we can apply my_touchbutton
		Button Lose_to_main = (Button) LoseDialogue.findViewById(R.id.mm_btn);
		//this line to make the button change color when clicked
		Lose_to_main.setOnTouchListener(new my_TouchButton(Lose_to_main));
		Lose_to_main.setOnClickListener(MainMenu_List);
		lose = (TextView) LoseDialogue.findViewById(R.id.win);
		
		lose.setTypeface(Custom);
		Lose_to_main.setTypeface(Custom);
		Lose_to_main.setTransformationMethod(null);
		LoseDialogue.setVisibility(View.GONE);
		
		HighScoreDialogue = myInflater.inflate(R.layout.highscore, null,false);
		Rel_main_game.addView(HighScoreDialogue,widths*3/4,heights*3/4);
		HighScoreDialogue.setX(widths/8);
		HighScoreDialogue.setY(heights/8);
		//defining the button as an image view so we can apply my_touchbutton
		ImageView high_to_main = (ImageView) HighScoreDialogue.findViewById(R.id.immain);
		//this line to make the button change color when clicked
	//	high_to_main.setOnTouchListener(new my_TouchButton(high_to_main));
		high_to_main.setOnClickListener(MainMenu_List);
		TextView any = (TextView) HighScoreDialogue.findViewById(R.id.go_mainmenu);
		any.setTypeface(Custom);
		HighScoreDialogue.setVisibility(View.GONE);
		
		
		
		txtscoreview = (TextView) HighScoreDialogue.findViewById(R.id.highscoretxt);
		txtscoreview.setTypeface(Custom);
		
		
		
        
        AdView mAdView2 = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);
        
        AdView mAdView3 = (AdView) findViewById(R.id.adView3);
        AdRequest adRequest3 = new AdRequest.Builder().build();
        mAdView3.loadAd(adRequest3);
        
		
        AdView mAdView = (AdView) findViewById(R.id.adView5);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        
        mInterstitialAd = new InterstitialAd(this);
      //test //  mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdUnitId("ca-app-pub-3841738411630014/3680093403"); 
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed(){
                requestNewInterstitial();
                ad_closed();
            }
        });

        requestNewInterstitial();
        
        LL_rocket = new LinearLayout(this);
		Rel_main_game.addView(LL_rocket);
		LL_rocket.setGravity(Gravity.CENTER);
		LL_rocket.setBackgroundResource(R.drawable.red_button_small);
		LL_rocket.setX(ScreenWidth - LL_rocket.getBackground().getMinimumWidth() - 10);  
		LL_rocket.setY(ScreenHeight - LL_rocket.getBackground().getMinimumHeight() - 10);
		ImageView rocket = new ImageView(this);
		rocket.setBackgroundResource(level_prop.rocket_small);
		LL_rocket.addView(rocket);
		txt_rockets_owned = new TextView(this);
		txt_rockets_owned.setTextColor(Color.WHITE);
		LL_rocket.addView(txt_rockets_owned);
		txt_rockets_owned.setText("x"+ rockets_owned);
		LL_rocket.setOnClickListener(fire_listener);
		
		RL = new RelativeLayout(this);
		Rel_main_game.addView(RL);
		RL.setX(10);
		RL.setY(100);
		VL = new LinearLayout(this);
		RL.addView(VL);
		VL.setOrientation(LinearLayout.VERTICAL);
		RL1 = new RelativeLayout(this);
		VL.addView(RL1);
		HL1 = new LinearLayout(this);
		RL1.addView(HL1);
		HL1.setGravity(Gravity.CENTER);
		coin_view = new ImageView(this);
		coin_view.setBackgroundResource(R.drawable.coin_noglow);
		HL1.addView(coin_view);
		
		coin_text = new TextView(this);
		coin_text.setText("" + score);
		coin_text.setTextColor(Color.WHITE);
		coin_text.setTextSize(20);
		HL1.addView(coin_text);
		// coin_text.setY(coin_view.getTop()+coin_view.getHeight()/2);
		RL2 = new RelativeLayout(this);
		VL.addView(RL2);
		HL2 = new LinearLayout(this);
		HL2.setGravity(Gravity.CENTER);
		RL2.addView(HL2);
		ImageView monster_view = new ImageView(this);
		monster_view.setBackgroundResource(level_prop.monster_small);
		HL2.addView(monster_view);
		
		monster_text = new TextView(this);
		monster_text.setText("" + monsterskilled);
		if(highscore!=0)
			monster_text.setText(monsterskilled + " (" + highscore +")");
		monster_text.setTextColor(Color.WHITE);
		monster_text.setTextSize(20);
		monster_text.setPadding(5, 0, 0, 0);
		HL2.addView(monster_text);
		
		pause_button = new ImageView(this);
		pause_button.setBackgroundResource(R.drawable.pause_button);
		Rel_main_game.addView(pause_button);
		pause_button.setX(ScreenWidth - pause_button.getBackground().getMinimumWidth());
		pause_button.setY(10);
		pause_button.setOnClickListener(pause_click);
        
		main_mp = MediaPlayer.create(Game.this, R.raw.music);
		main_mp.setVolume(0.3f, 0.3f);
		if(MainMenu.sound==1)
			main_mp.start();
		main_mp.setLooping(true);
		
		
	}
	
	

	protected void ad_closed() {
		
		MainMenu.setSound(ad);
        Game.this.finish();
		
	}



	protected void reset_ad_counter() {
		this.ad_counter=0;
		
	}



	protected void inc_ad_counter() {
		this.ad_counter=ad_counter+1;
		
	}



	protected void i_got_rocket() {
		rockets_owned++;
		txt_rockets_owned.setText("x"+ rockets_owned);
		LL_rocket.setBackgroundResource(R.drawable.red_button_small2);
		
	}

	protected void incmonsterskilled(){
		
		monsterskilled++;
		MainMenu.incmon();
			if(monsterskilled<highscore){
				if(highscore!=0)
					monster_text.setText(monsterskilled + " (" + highscore +")");
				else
					monster_text.setText("" + monsterskilled);
			}
			else{
				if(highscore!=0)
					monster_text.setText(monsterskilled + " (" + monsterskilled + ")");
				else
					monster_text.setText("" + monsterskilled);
				editor.putInt("highscore", monsterskilled);
				editor.commit();
			}
			
		
	}
	//overriding the back button press
	//so it would pause the game instead
	@Override
	public void onBackPressed() {
		if(PauseMenu.getVisibility()==View.VISIBLE){
			PauseMenu.setVisibility(View.GONE);
			pause_button.setVisibility(View.VISIBLE);
			game_panel.Pause_game=false;
			if(!main_mp.isPlaying() && MainMenu.sound==1)
				main_mp.start();
		}
		else{
		pause_button.setVisibility(View.GONE);
		PauseMenu.setVisibility(View.VISIBLE);
		game_panel.Pause_game=true;
		if(main_mp.isPlaying())
			main_mp.pause();
		}
	}
	
	@Override
	public void onPause() {
		pause_button.setVisibility(View.GONE);
		PauseMenu.setVisibility(View.VISIBLE);
		game_panel.Pause_game=true;
		if(main_mp.isPlaying())
			main_mp.pause();
		super.onPause();
	}
	
	@Override
	public void onResume(){
		if(!main_mp.isPlaying() && MainMenu.sound==1)
			main_mp.start();
		super.onResume();
	}
	
	//to stop the music when the game is stopped
	@Override
	protected void onStop(){
		if(main_mp.isPlaying())
			main_mp.stop();
		super.onStop();
	}
	
	protected void i_lose() {
		game_panel.Pause_game=true;
		if(main_mp.isPlaying())
			main_mp.stop();
		main_mp = MediaPlayer.create(Game.this, R.raw.lose);
		if(MainMenu.sound==1)
			main_mp.start();
	
	
		if(monsterskilled>highscore){
		lose.setText("Congrats! You got a new HighScore: " + monsterskilled);
	}
		LoseDialogue.setVisibility(View.VISIBLE);
		
		
	}
	
	private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
        //	.addTestDevice("521401BED6D43E963EC82B3DC7F0CDB2")
        	
        	.build();

        mInterstitialAd.loadAd(adRequest);
    }

	protected void i_get_coin() {
		
		score+=50;
		coin_text.setText(""+ score);
		editor.putInt("score", score);
		editor.commit();
		MediaPlayer mp = MediaPlayer.create(Game.this, R.raw.coin);
		mp.setVolume(0.3f, 0.3f);
		if(MainMenu.sound==1)
			mp.start();
	}
/*
	private void i_win() {
		if(main_mp.isPlaying())
			main_mp.stop();
		main_mp = MediaPlayer.create(Game.this, R.raw.win);
		main_mp.start();
		game_panel.Pause_game=true;
		WinDialogue.setVisibility(View.VISIBLE);
	}
	*/
	
}
