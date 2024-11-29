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

        Animation translateAnim = AnimationUtils.loadAnimation(this, R.anim.translate);
        Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale);

        findViewById(R.id.appLogo).startAnimation(translateAnim);
        findViewById(R.id.appLogo).startAnimation(scaleAnim);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, 3000);
    }
}
