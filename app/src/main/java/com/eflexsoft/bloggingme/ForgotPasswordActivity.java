package com.eflexsoft.bloggingme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.eflexsoft.bloggingme.databinding.ActivityForgotPasswordBinding;
import com.eflexsoft.bloggingme.viewmodel.ForgotPasswordViewModel;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPasswordActivity extends AppCompatActivity {

    ForgotPasswordViewModel viewModel;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_forgot_password);

        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        ActivityForgotPasswordBinding activityForgotPasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);


        activityForgotPasswordBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activityForgotPasswordBinding.loginContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = activityForgotPasswordBinding.email.getText().toString();

                if (email.trim().isEmpty()) {
                    activityForgotPasswordBinding.email.setError("missing");
                    return;
                }

                activityForgotPasswordBinding.progressR.setVisibility(View.VISIBLE);
                activityForgotPasswordBinding.progressR.setAlpha(1);
                activityForgotPasswordBinding.progressR.animate().scaleX(1f);
                activityForgotPasswordBinding.progressR.animate().scaleY(1f);

                viewModel.doPasswordReset(email);

            }
        });
        
        viewModel.isSuccessLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ForgotPasswordActivity.this)
                            .setMessage("A password reset email have been sent to " + activityForgotPasswordBinding.email.getText().toString())
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                    AlertDialog alertDialog1 = alertDialog.create();

                    activityForgotPasswordBinding.progressR.animate().scaleX(0f);
                    activityForgotPasswordBinding.progressR.animate().scaleY(0f);
                    activityForgotPasswordBinding.progressR.setAlpha(0);
                    activityForgotPasswordBinding.progressR.setVisibility(View.GONE);
                    alertDialog1.show();

                } else {

                    activityForgotPasswordBinding.progressR.animate().scaleX(0f);
                    activityForgotPasswordBinding.progressR.animate().scaleY(0f);
                    activityForgotPasswordBinding.progressR.setAlpha(0);
                    activityForgotPasswordBinding.progressR.setVisibility(View.GONE);
                }

            }
        });

    }
}