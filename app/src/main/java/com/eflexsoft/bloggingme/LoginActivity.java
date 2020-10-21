package com.eflexsoft.bloggingme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.eflexsoft.bloggingme.databinding.ActivityLoginBinding;
import com.eflexsoft.bloggingme.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("Please wait");

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        activityLoginBinding.loginContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = activityLoginBinding.email.getText().toString();
                String password = activityLoginBinding.password.getText().toString();

                if (email.trim().isEmpty()){
                    activityLoginBinding.email.setError("missing");
                }else if (password.trim().isEmpty()){
                    activityLoginBinding.password.setError("missing");
                }else if (password.length()<6){
                    activityLoginBinding.password.setError("at 6 least characters");
                }else {
                    activityLoginBinding.progressR.setVisibility(View.VISIBLE);
                    activityLoginBinding.progressR.setAlpha(1);
                    activityLoginBinding.progressR.animate().scaleX(1f);
                    activityLoginBinding.progressR.animate().scaleY(1f);

                    loginViewModel.doLogin(email,password);

                }



//                activityLoginBinding.loginContinue.startAnimation();
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        activityLoginBinding.loginContinue.stopAnimation();
//                        activityLoginBinding.loginContinue.revertAnimation();
//                    }
//                },3000);


            }
        });

        loginViewModel.isSuccessLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    activityLoginBinding.progressR.setVisibility(View.VISIBLE);
                    activityLoginBinding.progressR.setAlpha(1);
                    activityLoginBinding.progressR.animate().scaleX(1f);
                    activityLoginBinding.progressR.animate().scaleY(1f);
                } else {

                    activityLoginBinding.progressR.animate().scaleX(0f);
                    activityLoginBinding.progressR.animate().scaleY(0f);
                    activityLoginBinding.progressR.setAlpha(0);
                    activityLoginBinding.progressR.setVisibility(View.GONE);
                }
            }
        });

        activityLoginBinding.forgotP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        activityLoginBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        activityLoginBinding.createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            }
        });
    }
}