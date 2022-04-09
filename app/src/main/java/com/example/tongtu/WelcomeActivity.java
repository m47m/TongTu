package com.example.tongtu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class WelcomeActivity extends AppCompatActivity {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this,R.layout.activity_welcome,null);
        setContentView(view);

        //pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref = this.getSharedPreferences("login_message",MODE_PRIVATE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f,1.0f);
        alphaAnimation.setDuration(500);
        view.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                boolean islogin = pref.getBoolean("islogin",false);
                Intent intent_login_or_main = new Intent();
                if(islogin){
                    //已经登录
                    intent_login_or_main.setClass(WelcomeActivity.this,MainActivity.class);

                }else{
                    //未登录
                    intent_login_or_main.setClass(WelcomeActivity.this,LoginActivity.class);

                }
                startActivity(intent_login_or_main);
                finish();



            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}