package com.example.doctalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Splash extends AppCompatActivity {


    private  static int SPLASH_TIME_OUT = 5000;


    //Animation

    Animation topAnimation, middleAnimation, bottomAnimation;

    //Hooks
    View first,second,third,fourth,fifth,sixth;
    TextView a;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        middleAnimation = AnimationUtils.loadAnimation(this,R.anim.middle_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);



        first = findViewById(R.id.firstline);
        second = findViewById(R.id.secondline);
        third = findViewById(R.id.thirdline);
        fourth = findViewById(R.id.fourthline);
        fifth = findViewById(R.id.fifthline);
        sixth = findViewById(R.id.sixthline);

        a = findViewById(R.id.a);


        first.setAnimation(topAnimation);
        second.setAnimation(topAnimation);
        third.setAnimation(topAnimation);
        fourth.setAnimation(topAnimation);
        fifth.setAnimation(topAnimation);
        sixth.setAnimation(topAnimation);

        a.setAnimation(middleAnimation);


        // Splash Screen

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        }, 3000);


    }
}