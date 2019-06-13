package com.breja.breja_br.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.breja.breja_br.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = getIntent();
                    Bundle i = intent.getExtras();
                    double latPoint = i.getDouble("lat");
                    double lngPoint = i.getDouble("lng");
                    Intent l = new Intent(getBaseContext(),MainActivity.class);
                    l.putExtra("lat",latPoint);
                    l.putExtra("lng",lngPoint);
                    startActivity(l);
                    finish();
                }
            }, 9000);
        }

    }
