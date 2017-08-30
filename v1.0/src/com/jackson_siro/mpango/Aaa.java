package com.jackson_siro.mpango;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Aaa extends Activity {
	private long ms=0;
	private long splashTime=5000;
	private boolean splashActive = true;
	private boolean paused=false;
	RelativeLayout MySong;
	
	private TextView mytext;
	private ImageView myimage;
		
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aaa);
	    
		mytext = (TextView) findViewById(R.id.text);
	    myimage = (ImageView) findViewById(R.id.image);
	      
	    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
	    myimage.startAnimation(animation1);
	  
	    Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade1);
	    mytext.startAnimation(animation2);
	    
	  		Thread mythread = new Thread() {
				public void run() {
					try {
						while (splashActive && ms < splashTime) {
							if(!paused)
								ms=ms+100;
							sleep(100);
						}
					} catch(Exception e) {}
					finally {
						Intent intent = new Intent(Aaa.this, WelcomeAct.class);
						startActivity(intent);
						finish();  						 
					}
				}
			};
			mythread.start();
			
		 } 
			
	}
	