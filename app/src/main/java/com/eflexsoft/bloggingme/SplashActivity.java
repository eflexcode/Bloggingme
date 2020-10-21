package com.eflexsoft.bloggingme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               if (FirebaseAuth.getInstance().getCurrentUser() == null){
                   startActivity(new Intent(SplashActivity.this,OptionsActivity.class));
                   finish();
               }else {
                   startActivity(new Intent(SplashActivity.this,MainActivity.class));
                   finish();
               }
            }
        },3000);

    }
}