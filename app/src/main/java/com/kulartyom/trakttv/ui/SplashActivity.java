package com.kulartyom.trakttv.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kulartyom.trakttv.R;
import com.kulartyom.trakttv.constans.Constans;


public class SplashActivity extends AppCompatActivity {

    // ===========================================================
    // Constants
    // ===========================================================

    public static final String TAG = SplashActivity.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        splashIntent();

    }
    // ===========================================================
    // Methods
    // ===========================================================

    private void splashIntent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splash = new Intent(SplashActivity.this, SocialActivity.class);
                startActivity(splash);
            }
        }, Constans.SPLASH_TIME_OUT);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
