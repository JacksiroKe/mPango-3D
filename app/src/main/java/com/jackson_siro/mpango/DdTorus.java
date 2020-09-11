package com.jackson_siro.mpango;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class DdTorus extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    private DdTorusView mTorusView;
    // public Twitter twitter;
    // public ShareOnFacebook shareOnFacebook;

    //private int offset = 0;

    // private static final String CONSUMER_KEY = "uz1gdZHSD0i1VURf5qCeIg";
    // private static final String CONSUMER_SECRET =
    // "wfebjZike2QuFQkkgrK2TFalZckRvuH3Bl7oTkCs4";
    // private static final String CALLBACK_URL = "myapp://sharetotwitter";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FrameLayout root = new FrameLayout(this);
        root.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));

        mTorusView = new DdTorusView(this, null);
        root.addView(mTorusView);

        setContentView(root);

        Intent intent = this.getIntent();
        int mode = intent.getIntExtra("com.jackson_siro.mpango.Mode", 1);
        mTorusView.startGame(mode);
    }

    @Override
    public void onPause() {
        super.onPause();
        mTorusView.getControl().pauseGame();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTorusView.getControl().resumeGame();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}