package com.jackson_siro.mpango;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AppStart extends AppCompatActivity {
    private ImageView imgicon, imgcopyright;
    private long ms=0, splashTime=5000;
    private boolean splashActive = true, paused=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_start);
        imgicon = findViewById(R.id.imgicon);
        imgcopyright = findViewById(R.id.imgcopyright);

        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        imgicon.startAnimation(animation1);

        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade1);
        imgcopyright.startAnimation(animation2);

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
                startActivity(new Intent(AppStart.this, BbWelcome.class));
            }
            }
        };
        mythread.start();
    }
}
