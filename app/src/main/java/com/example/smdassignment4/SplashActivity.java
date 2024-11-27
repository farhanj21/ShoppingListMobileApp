package com.example.smdassignment4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Load animations
        Animation translateAnim = AnimationUtils.loadAnimation(this, R.anim.translate);
        Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale);

        // Start animations
        findViewById(R.id.appLogo).startAnimation(translateAnim);
        findViewById(R.id.appLogo).startAnimation(scaleAnim);

        // Delay and navigate to LoginActivity
        new Handler().postDelayed(() -> {
            // This ensures that the app navigates to the LoginActivity
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish(); // Close SplashActivity
        }, 3000); // 3 seconds delay for splash screen
    }
}
