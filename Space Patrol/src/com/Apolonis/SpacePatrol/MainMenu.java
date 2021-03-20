package com.Apolonis.SpacePatrol;




import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.google.example.games.basegameutils.BaseGameUtils;


public class MainMenu extends Activity implements
GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
	static GoogleApiClient mGoogleApiClient;
	private static int RC_SIGN_IN = 9001;

	private boolean mResolvingConnectionFailure = false;
	private boolean mAutoStartSignInFlow = true;
	private boolean mSignInClicked = false;
	boolean mExplicitSignOut = false;
	boolean mInSignInFlow = false;
	
	/*
	Activity mActivity = null;

    // app context
    Context mAppContext = null;
    GoogleApiClient.Builder mGoogleApiClientBuilder = null;
    GoogleApiClient mGoogleApiClient = null;
    public final static int CLIENT_NONE = 0x00;
    public final static int CLIENT_GAMES = 0x01;
    public final static int CLIENT_PLUS = 0x02;
    public final static int CLIENT_SNAPSHOT = 0x08;
    public final static int CLIENT_ALL = CLIENT_GAMES | CLIENT_PLUS
            | CLIENT_SNAPSHOT;
    GamesOptions mGamesApiOptions = GamesOptions.builder().build();

    // What clients were requested? (bit flags)
    int mRequestedClients = CLIENT_NONE;
    Handler mHandler;
    GameHelperListener mListener = null;
    */
	//defining layouts to be used
	
	Button mutebutton;
	SharedPreferences prefs;
	static int sound;
	static Editor editor;
	Button start_btn,upgrade_btn,exit_btn,btn0,btn1,btn2,sign_in_button,achievemnts_btn;
	
	static Button back2;
	View ship_view;
	static RelativeLayout level_view;
	View coming_soon;
	View upgrade_view;
	View not_enough;
	View price1;
	View price2;
	View price3;
	static View rate,cover;
	RelativeLayout r3;
	static RelativeLayout rl32;
	static RelativeLayout main_view;
	TextView textView2, textView22,txtview4,shot_level_text,shot_speed_price,shot_type_text,shots_nb_text,sign_in_text,achievements_txt;
	ImageView img3;
	int score;
	static int shotnb,level,ship;
	int shot_level;
	static int extra_heart;
	static float speed_upgrade;
	int shot_speed_prices[];
	protected int mRequestedClients;
	static MediaPlayer MainMenuMusic;
	boolean levels[];
	boolean ships[];
	ImageView enl_ship, ImageView012;
	LinearLayout s1price,l1price;
	boolean first_time,want_signin;
	
	//initiating onCreate algorithm
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Create the Google Api Client with access to the Play Games services
		
		
		
	    mGoogleApiClient = new GoogleApiClient.Builder(this)
	            .addConnectionCallbacks(this)
	            .addOnConnectionFailedListener(this)
	            .addApi(Games.API).addScope(Games.SCOPE_GAMES)
	            // add other APIs and scopes here as needed
	            .build();
	    mRequestedClients = BaseGameActivity.CLIENT_GAMES;
	    
	    
		//setting the main view to use
		setContentView(R.layout.activity_main_menu);
		
		//defining the layouts that we need to use
		
		ImageView main_title =  (ImageView) findViewById(R.id.title_img);
	 
			
		 if(Locale.getDefault().getISO3Language().equals("ara"))
			 main_title.setBackgroundResource(R.drawable.main_title_ar);
		 else if(Locale.getDefault().getISO3Language().equals("fre") || Locale.getDefault().getISO3Language().equals("fra"))
			 main_title.setBackgroundResource(R.drawable.main_title_fr);
		 else
			 main_title.setBackgroundResource(R.drawable.main_title);
		 
		start_btn = (Button) findViewById(R.id.mm_btn);
		upgrade_btn = (Button) findViewById(R.id.settings_btn);
		exit_btn = (Button) findViewById(R.id.exit_btn);
		
		
		start_btn.setOnTouchListener(new my_TouchButton(start_btn));
		upgrade_btn.setOnTouchListener(new my_TouchButton(upgrade_btn));
		exit_btn.setOnTouchListener(new my_TouchButton(exit_btn));
		
		//changing font
		Typeface Custom = Typeface.createFromAsset(getAssets(), "font.ttf");
		start_btn.setTypeface(Custom);
		upgrade_btn.setTypeface(Custom);
		exit_btn.setTypeface(Custom);
		
		
		//we give the btn the propreties of our my_touchbutton class
		//and we pass to it the image view variable
     //   Btn.setOnTouchListener(new my_TouchButton(ImageBtn));
		
        
    	
		//onClick
		start_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//creating and starting intent to a new activity
				// Intent myIntent = new Intent(MainMenu.this,Game.class);
				// startActivity(myIntent);
			
					
	                
				ship_view.setVisibility(View.VISIBLE);
				r3.setVisibility(View.GONE);
				
			}
			
		});
		
		upgrade_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				upgrade_view.setVisibility(View.VISIBLE);
				r3.setVisibility(View.GONE);
			}
		});
		
		exit_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
							finish();
}
		});
		
		
	
	mutebutton = (Button) findViewById(R.id.mute_button);
	prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
	editor = prefs.edit();
	sound = prefs.getInt("sound", 1);
	if(sound==1)
		mutebutton.setBackgroundResource(R.drawable.mute_button);
	else
		mutebutton.setBackgroundResource(R.drawable.unmute_button2);
	speed_upgrade=prefs.getFloat("speed_upgrade", 1f);
	extra_heart=prefs.getInt("extra_heart", 0);
	shotnb = prefs.getInt("shotnb", 1);
	
	
	mutebutton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if(sound==1){
				mutebutton.setBackgroundResource(R.drawable.unmute_button2);
				sound=0;
				if(MainMenuMusic.isPlaying())
					MainMenuMusic.pause();
			}
			else{
				mutebutton.setBackgroundResource(R.drawable.mute_button);
				sound=1;
				if(!MainMenuMusic.isPlaying())
					MainMenuMusic.start();
			}
			
			editor.putInt("sound", sound);
			editor.commit();
		}
	});
	DisplayMetrics dm = new DisplayMetrics();
	this.getWindowManager().getDefaultDisplay().getMetrics(dm);
	r3 = (RelativeLayout) findViewById(R.id.r3);
	
	LayoutInflater myInflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
	main_view = (RelativeLayout) findViewById(R.id.linear);
	main_view.setEnabled(false);
	
	ship_view = myInflater.inflate(R.layout.spaceship, null, false);
		
		main_view.addView(ship_view,dm.widthPixels,dm.heightPixels);
		ship_view.setVisibility(View.GONE);
		textView2 = (TextView) ship_view.findViewById(R.id.rate_us);
		textView2.setTypeface(Custom);
		// score = prefs.getInt("score", 0);
		// textView2.setText("" + score);
		TextView textView3 = (TextView) ship_view.findViewById(R.id.textView3);
		textView3.setTypeface(Custom);
		TextView textView02 = (TextView) ship_view.findViewById(R.id.TextView02);
		textView02.setTypeface(Custom);
		Button back = (Button) ship_view.findViewById(R.id.mm_btn);
		back.setTypeface(Custom);
		
		ships = new boolean[10];
		ships[2] = prefs.getBoolean("ships2", false);
		
			
		back.setOnTouchListener(new my_TouchButton(back));
		TextView enl_text = (TextView) ship_view.findViewById(R.id.enl_text);
		enl_text.setTypeface(Custom);
		enl_ship = (ImageView) ship_view.findViewById(R.id.enl_ship);
		s1price = (LinearLayout) ship_view.findViewById(R.id.s1price);
		RelativeLayout enl_view = (RelativeLayout) ship_view.findViewById(R.id.enl_view);
		if(ships[2]){
			enl_ship.setImageResource(R.drawable.pac_man_ship);
			s1price.setVisibility(View.GONE);
		}
		enl_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(ships[2]){
				ship=2;
				ship_view.setVisibility(View.GONE);
				level_view.setVisibility(View.VISIBLE);}
				else{
					if(score>=10000){
						enl_ship.setImageResource(R.drawable.pac_man_ship);
						s1price.setVisibility(View.GONE);
						score-=10000;
						ships[2]=true;
						editor.putBoolean("ships2", ships[2]);
						editor.putInt("score", score);
						editor.commit();
						textView2.setText(""+score);
						textView22.setText(""+score);
						txtview4.setText(""+score);
					}
					else{
						not_enough.setVisibility(View.VISIBLE);
					}
				}
			}
			
		});
		
		ImageView ImageView02 = (ImageView) ship_view.findViewById(R.id.ImageView02);
		ImageView imageView1 = (ImageView) ship_view.findViewById(R.id.img0);
		ImageView02.setAdjustViewBounds(true);
		enl_ship.setAdjustViewBounds(true);
		imageView1.setAdjustViewBounds(true);
		ImageView02.setMaxHeight(dm.heightPixels/3);
		enl_ship.setMaxHeight(dm.heightPixels/3);
		imageView1.setMaxHeight(dm.heightPixels/3);
		level_view = (RelativeLayout) myInflater.inflate(R.layout.level, null, false);
		
		main_view.addView(level_view,dm.widthPixels,dm.heightPixels);
		level_view.setVisibility(View.GONE);
		textView22 = (TextView) level_view.findViewById(R.id.rate_us);
		textView22.setTypeface(Custom);
		// textView22.setText("" + score);
		TextView textView32 = (TextView) level_view.findViewById(R.id.textView3);
		textView32.setTypeface(Custom);
		TextView textView022 = (TextView) level_view.findViewById(R.id.TextView02);
		textView022.setTypeface(Custom);
		back2 = (Button) level_view.findViewById(R.id.mm_btn);
		back2.setTypeface(Custom);
		back2.setOnTouchListener(new my_TouchButton(back2));
		
		ImageView ImageView022 = (ImageView) level_view.findViewById(R.id.ImageView02);
		ImageView012 = (ImageView) level_view.findViewById(R.id.ingress_level);
		TextView ingress_text = (TextView) level_view.findViewById(R.id.ingress_text);
		ingress_text.setTypeface(Custom);
		ImageView imageView12 = (ImageView) level_view.findViewById(R.id.img0);
		ImageView022.setAdjustViewBounds(true);
		ImageView012.setAdjustViewBounds(true);
		imageView12.setAdjustViewBounds(true);
		ImageView022.setMaxHeight(dm.heightPixels/3);
		ImageView012.setMaxHeight(dm.heightPixels/3);
		imageView12.setMaxHeight(dm.heightPixels/3);
		levels = new boolean[10];
		levels[2]= prefs.getBoolean("levels2", false);
		l1price = (LinearLayout) level_view.findViewById(R.id.l1price);
		if(levels[2]){
			ImageView012.setImageResource(R.drawable.pacman_level);
			l1price.setVisibility(View.GONE);
		}
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ship_view.setVisibility(View.GONE);
				r3.setVisibility(View.VISIBLE);
			}
		});
		back2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				level_view.setVisibility(View.GONE);
				ship_view.setVisibility(View.VISIBLE);
				
			}
		});
	
		
		
		RelativeLayout rl3 = (RelativeLayout) ship_view.findViewById(R.id.rl3);
		rl3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ship=1;
				ship_view.setVisibility(View.GONE);
				level_view.setVisibility(View.VISIBLE);
				
			}
		});
		rl32 = (RelativeLayout) level_view.findViewById(R.id.rl3);
		rl32.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				level=1;
				Intent myIntent = new Intent(MainMenu.this,Game.class);
				startActivity(myIntent);
				
			}
		});
		RelativeLayout ingress_layout = (RelativeLayout) level_view.findViewById(R.id.ingress_layout);
		ingress_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(levels[2]){
					level=2;
					Intent myIntent = new Intent(MainMenu.this,Game.class);
					startActivity(myIntent);}
					else{
						if(score>=10000){
							ImageView012.setImageResource(R.drawable.pacman_level);
							l1price.setVisibility(View.GONE);
							score-=10000;
							levels[2]=true;
							editor.putBoolean("levels2", levels[2]);
							editor.putInt("score", score);
							editor.commit();
							textView2.setText(""+score);
							textView22.setText(""+score);
							txtview4.setText(""+score);
						}
						else{
							not_enough.setVisibility(View.VISIBLE);
						}
					}
				
			}
		});
		upgrade_view = myInflater.inflate(R.layout.upgrade, null, false);
		
		main_view.addView(upgrade_view,dm.widthPixels,dm.heightPixels);
		
		upgrade_view.setVisibility(View.GONE);
		ImageView img0 = (ImageView) upgrade_view.findViewById(R.id.img0);
		img0.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				upgrade_view.setVisibility(View.GONE);
				r3.setVisibility(View.VISIBLE);
			}
		});
		ImageView img1 = (ImageView) upgrade_view.findViewById(R.id.img1);
		ImageView img2 = (ImageView) upgrade_view.findViewById(R.id.img2);
		img3 = (ImageView) upgrade_view.findViewById(R.id.ingress_level);
		
		img1.setMaxHeight(dm.heightPixels/3);
		img2.setMaxHeight(dm.heightPixels/3);
		img3.setMaxHeight(dm.heightPixels/3);
		TextView txtview0 = (TextView) upgrade_view.findViewById(R.id.TextView04);
		TextView txtview1 = (TextView) upgrade_view.findViewById(R.id.TextView02);
		TextView txtview2 = (TextView) upgrade_view.findViewById(R.id.did_you_enjoy);
		TextView txtview3 = (TextView) upgrade_view.findViewById(R.id.textView3);
		txtview4 = (TextView) upgrade_view.findViewById(R.id.textView4);
		txtview0.setTypeface(Custom);
		txtview1.setTypeface(Custom);
		txtview2.setTypeface(Custom);
		txtview3.setTypeface(Custom);
		txtview4.setTypeface(Custom);
		btn0 = (Button) upgrade_view.findViewById(R.id.Button01);
		btn1 = (Button) upgrade_view.findViewById(R.id.Button02);
		btn2 = (Button) upgrade_view.findViewById(R.id.mm_btn);
		btn0.setTypeface(Custom);
		btn1.setTypeface(Custom);
		btn2.setTypeface(Custom);
		btn0.setOnTouchListener(new my_TouchButton(btn0));
		btn1.setOnTouchListener(new my_TouchButton(btn1));
		btn2.setOnTouchListener(new my_TouchButton(btn2));
		shot_level_text = (TextView) upgrade_view.findViewById(R.id.shot_level);
		shot_type_text = (TextView) upgrade_view.findViewById(R.id.shot_type);
		
		shots_nb_text = (TextView) upgrade_view.findViewById(R.id.shots_nb);
		shot_level_text.setTypeface(Custom);
		shot_type_text.setTypeface(Custom);
		shots_nb_text.setTypeface(Custom);
		shot_level = prefs.getInt("shot_level", 2);
		shot_level_text.setText(getString(R.string.level) +" "+ (shot_level-1));
		shot_speed_prices = new int[9];
		shot_speed_prices[0]=1000;
		shot_speed_prices[1]=2000;
		shot_speed_prices[2]=5000;
		shot_speed_prices[3]=10000;
		shot_speed_prices[4]=20000;
		shot_speed_prices[5]=40000;
		shot_speed_prices[6]=60000;
		shot_speed_prices[7]=80000;
		shot_speed_prices[8]=100000;
		shot_speed_price = (TextView) upgrade_view.findViewById(R.id.TextView03);
		if(shot_level!=11)
			shot_speed_price.setText(""+shot_speed_prices[shot_level-2]);
		
		not_enough = myInflater.inflate(R.layout.not_enough, null, false);
		main_view.addView(not_enough,dm.widthPixels/2,dm.heightPixels/2);
		not_enough.setVisibility(View.GONE);
		not_enough.setX(dm.widthPixels/4);
		not_enough.setY(dm.heightPixels/4);
		TextView not_enough_txt = (TextView) not_enough.findViewById(R.id.not_enough_txt);
		Button not_enough_ok = (Button) not_enough.findViewById(R.id.ok);
		not_enough_txt.setTypeface(Custom);
		not_enough_ok.setTypeface(Custom);
		price1 = (LinearLayout) upgrade_view.findViewById(R.id.price1);
		price2 = (LinearLayout) upgrade_view.findViewById(R.id.price2);
		price3 = (LinearLayout) upgrade_view.findViewById(R.id.price3);
		
		if(shot_level==11){
			btn1.setVisibility(View.GONE);
			shot_level_text.setText(getString(R.string.max_level));
			price1.setVisibility(View.GONE);
			}
		if(extra_heart==1){
			price2.setVisibility(View.GONE);
			btn2.setVisibility(View.GONE);
			shot_type_text.setText(getString(R.string.upgraded));
		}
		if(shotnb==2){
			price3.setVisibility(View.GONE);
			shots_nb_text.setText(getString(R.string.max_number));
			btn0.setVisibility(View.GONE);
		}
		not_enough_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				not_enough.setVisibility(View.GONE);
				
			}
		});
		
		
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(score<shot_speed_prices[shot_level-2]){
					not_enough.setVisibility(View.VISIBLE);
				}
			else{
				score-=shot_speed_prices[shot_level-2];
				editor.putInt("score", score);
				editor.commit();
				textView2.setText(""+score);
				textView22.setText(""+score);
				txtview4.setText(""+score);
				speed_upgrade += 0.09;
				shot_level++;
				editor.putInt("shot_level", shot_level);
				editor.putFloat("speed_upgrade", speed_upgrade);
				editor.commit();
				shot_level_text.setText(getString(R.string.level) + " "+ (shot_level-1));
				
				if(shot_level==11){
					btn1.setVisibility(View.GONE);
					shot_level_text.setText(getString(R.string.max_level));
					price1.setVisibility(View.GONE);
				}
				else
					shot_speed_price.setText(""+shot_speed_prices[shot_level-2]);
			}
				
			}
		});
		
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(score<75000)
					not_enough.setVisibility(View.VISIBLE);
				else{
					score-=75000;
					extra_heart=1;
					editor.putInt("extra_heart", 1);
					editor.putInt("score",score);
					editor.commit();
					textView2.setText(""+score);
					textView22.setText(""+score);
					txtview4.setText(""+score);
					price2.setVisibility(View.GONE);
					btn2.setVisibility(View.GONE);
					shot_type_text.setText(getString(R.string.upgraded));
					if(mGoogleApiClient.hasConnectedApi(Games.API))
						Games.Achievements.unlock(mGoogleApiClient, getString(R.string.achievement_the_hulk));
				}
			}
		});
		
		btn0.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(score<100000)
					not_enough.setVisibility(View.VISIBLE);
				else{
					score-=100000;
					shotnb=2;
					editor.putInt("shotnb", 2);
					editor.putInt("score", score);
					editor.commit();
					textView2.setText(""+score);
					textView22.setText(""+score);
					txtview4.setText(""+score);
					price3.setVisibility(View.GONE);
					shots_nb_text.setText(getString(R.string.max_number));
					btn0.setVisibility(View.GONE);
					if(mGoogleApiClient.hasConnectedApi(Games.API))
						Games.Achievements.unlock(mGoogleApiClient, getString(R.string.achievement_double_the_trouble));
				}
				
				
			}
		});
		
		
		
		AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        
        AdView mAdView5 = (AdView) findViewById(R.id.adView5);
        AdRequest adRequest5 = new AdRequest.Builder().build();
        mAdView5.loadAd(adRequest5);
        
        sign_in_button = (Button) findViewById(R.id.sign_in_button);
        sign_in_button.setOnTouchListener(new my_TouchButton(sign_in_button));
        sign_in_text = (TextView) findViewById(R.id.did_you_enjoy);
        sign_in_text.setTypeface(Custom);
        if(mGoogleApiClient != null && mGoogleApiClient.hasConnectedApi(Games.API))
        	sign_in_text.setText(getString(R.string.sign_out));
        sign_in_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(mGoogleApiClient != null && mGoogleApiClient.hasConnectedApi(Games.API))
					signOutclicked();
				else
					signInClicked();
			}
		}); 
        
	rate = myInflater.inflate(R.layout.rate, null, false);
	main_view.addView(rate, dm.widthPixels/4*3, dm.heightPixels/4*3);
	rate.setX(dm.widthPixels/8);
	rate.setY(dm.heightPixels/8);
	rate.setVisibility(View.GONE);
	TextView did_you = (TextView) rate.findViewById(R.id.did_you_enjoy);
	TextView rate_us = (TextView) rate.findViewById(R.id.rate_us);
	did_you.setTypeface(Custom);
	rate_us.setTypeface(Custom);
	Button button1 = (Button) rate.findViewById(R.id.maybe_later);
	button1.setTypeface(Custom);
	button1.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			rate.setVisibility(View.GONE);
			setlevelviewclickable(true);
			
		}
	});
	
	RatingBar ratingbar = (RatingBar) rate.findViewById(R.id.ratingBar1);
	Button lets_go = (Button) rate.findViewById(R.id.lets_go);
	lets_go.setTypeface(Custom);
	
	
	ratingbar.setOnTouchListener(new View.OnTouchListener() {
		
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			rate.setVisibility(View.GONE);	 
			 setlevelviewclickable(true);
			try {
                Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                Uri.parse("https://play.google.com/store/apps/details?id=com.Apolonis.SpacePatrol"));
                startActivity(viewIntent);
    }catch(Exception e) {
        
    }
			 
			return false;
		}
	});
	lets_go.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			rate.setVisibility(View.GONE);	 
			 setlevelviewclickable(true);
			try {
                Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                Uri.parse("https://play.google.com/store/apps/details?id=com.Apolonis.SpacePatrol"));
                startActivity(viewIntent);
    }catch(Exception e) {
        
    }
			 
		}
	});
	
	achievemnts_btn = (Button) findViewById(R.id.achievemnts_button);
	achievements_txt = (TextView) findViewById(R.id.achievements_txt);
	achievements_txt.setTypeface(Custom);
	achievemnts_btn.setOnTouchListener(new my_TouchButton(achievemnts_btn));
	achievemnts_btn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if(mGoogleApiClient!=null && mGoogleApiClient.hasConnectedApi(Games.API))
				startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient),1);
			
		}
	});
	first_time=prefs.getBoolean("first_time", true);
	want_signin = prefs.getBoolean("want_signin", false);
	if(first_time || want_signin)
		signInClicked();
	first_time=false;
	editor.putBoolean("first_time", false);
	editor.commit();
	}
	//we put the music code here so the music would play each time
	//we go to the main menu, and not only when we first create it
	@Override
	public void onStart(){
		sound=prefs.getInt("sound", 1);
		MainMenuMusic =MediaPlayer.create(MainMenu.this, R.raw.main);
		MainMenuMusic.setVolume(0.3f, 0.3f);
		if(sound==1){
		MainMenuMusic.start();
		MainMenuMusic.setLooping(true);
		}
		score = prefs.getInt("score", 0);
		textView2.setText(""+score);
		textView22.setText(""+score);
		txtview4.setText(""+score);
		
	//	mGoogleApiClient.connect(2);
		
		super.onStart();
	}
	
	//to stop the music when we leave the main menu
	@Override
	public void onStop(){
		if(MainMenuMusic.isPlaying())
			MainMenuMusic.stop();
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		if(mGoogleApiClient.hasConnectedApi(Games.API))
			mGoogleApiClient.disconnect();
		super.onDestroy();
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
	    if (mResolvingConnectionFailure) {
	        // already resolving
	        return;
	    }

	    // if the sign-in button was clicked or if auto sign-in is enabled,
	    // launch the sign-in flow
	    if (mSignInClicked || mAutoStartSignInFlow) {
	        mAutoStartSignInFlow = false;
	        mSignInClicked = false;
	        mResolvingConnectionFailure = true;

	        // Attempt to resolve the connection failure using BaseGameUtils.
	        // The R.string.signin_other_error value should reference a generic
	        // error string in your strings.xml file, such as "There was
	        // an issue with sign-in, please try again later."
	        if (!BaseGameUtils.resolveConnectionFailure(this,
	                mGoogleApiClient, connectionResult,
	                RC_SIGN_IN, "Error")) {
	            mResolvingConnectionFailure = false;
	        } 
	    }

	    sign_in_text.setText(getString(R.string.sign_in));
	}
	
	protected void onActivityResult(int requestCode, int resultCode,Intent intent) {
	    if (requestCode == RC_SIGN_IN) {
	        mSignInClicked = false;
	        mResolvingConnectionFailure = false;
	        if (resultCode == RESULT_OK) {
	        	mGoogleApiClient.connect();
	        } else {
	            // Bring up an error dialog to alert the user that sign-in
	            // failed. The R.string.signin_failure should reference an error
	            // string in your strings.xml file that tells the user they
	            // could not be signed in, such as "Unable to sign in."
	            BaseGameUtils.showActivityResultError(this,
                requestCode, resultCode, R.string.sign_in_failed);
	        }
	    }
	}
	private void signInClicked() {
	    mSignInClicked = true;
	    mGoogleApiClient.connect();
	}
	
	// Call when the sign-out button is clicked
	private void signOutclicked() {
	    mSignInClicked = false;
	    mExplicitSignOut = true;
        if (mGoogleApiClient != null && mGoogleApiClient.hasConnectedApi(Games.API)) {
            Games.signOut(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            
        }
		sign_in_text.setText(getString(R.string.sign_in));
		
		
	}	

	@Override
	public void onConnectionSuspended(int i) {
	    // Attempt to reconnect
	    mGoogleApiClient.connect();
	}
	
	public void onBackPressed(){
		if(rate.getVisibility()==View.VISIBLE){
			rate.setVisibility(View.GONE);
			setlevelviewclickable(true);
			return;
		}
		if(not_enough.getVisibility()==View.VISIBLE){
			not_enough.setVisibility(View.GONE);
			return;
		}
		
		if(level_view.getVisibility()==View.VISIBLE){
			level_view.setVisibility(View.GONE);
			ship_view.setVisibility(View.VISIBLE);
			return;
		}
		if(ship_view.getVisibility()==View.VISIBLE){
			ship_view.setVisibility(View.GONE);
			r3.setVisibility(View.VISIBLE);
			return;
		}
		
		
		if(upgrade_view.getVisibility()==View.VISIBLE){
			upgrade_view.setVisibility(View.GONE);
			r3.setVisibility(View.VISIBLE);
			return;
		}
		super.onBackPressed();
		
	}
	
	static void setSound(int i){
		sound=i;
		editor.putInt("sound",i);
		editor.commit();
		if(sound==0 && MainMenuMusic.isPlaying())
			MainMenuMusic.pause();
		if(sound==1 && !MainMenuMusic.isPlaying()){
			MainMenuMusic.start();
			MainMenuMusic.setLooping(true);
		}
		
	}
	
	public void onSignInSucceeded() {
	    sign_in_text.setText(getString(R.string.sign_out));
	    
	}
	 
	public void onSignInFailed() {
		sign_in_text.setText(getString(R.string.sign_in));
	}
	
	public void onConnected(Bundle arg0) {
		sign_in_text.setText(getString(R.string.sign_out));
		want_signin=true;
	    editor.putBoolean("want_signin", true);
	    editor.commit(); 
	}
	
	public static void setRateV(){
		rate.setVisibility(View.VISIBLE);
	}
	public static void setlevelviewclickable(boolean b){
		back2.setEnabled(b);
		rl32.setEnabled(b);
		
	}
	
	public static void incmon(){
		if(mGoogleApiClient.hasConnectedApi(Games.API)){	
		Games.Achievements.increment(mGoogleApiClient, "CgkIoeWni-EPEAIQAg", 1);
		Games.Achievements.increment(mGoogleApiClient, "CgkIoeWni-EPEAIQAw", 1);
		Games.Achievements.increment(mGoogleApiClient, "CgkIoeWni-EPEAIQBA", 1);
		Games.Events.increment(mGoogleApiClient, "CgkIoeWni-EPEAIQBw", 1);
		}
	}
	
		
	}
	
