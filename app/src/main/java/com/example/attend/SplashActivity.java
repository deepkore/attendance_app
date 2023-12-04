package com.example.attend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 2000; //2seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This code will run after the splashTimeout
                Intent loginIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(loginIntent);
                finish(); // Close the splash activity so the user can't go back to it
            }
        }, SPLASH_TIMEOUT);
    }
}