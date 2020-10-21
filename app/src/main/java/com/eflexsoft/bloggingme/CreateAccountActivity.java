package com.eflexsoft.bloggingme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eflexsoft.bloggingme.databinding.ActivityCreateAccountBinding;
import com.eflexsoft.bloggingme.viewmodel.CreateAccountViewModel;

public class CreateAccountActivity extends AppCompatActivity {

    CreateAccountViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_account);

        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);

        ActivityCreateAccountBinding activityCreateAccountBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_account);
        activityCreateAccountBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activityCreateAccountBinding.createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
            }
        });

        activityCreateAccountBinding.loginContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = activityCreateAccountBinding.email.getText().toString();
                String password = activityCreateAccountBinding.password.getText().toString();
                String passwordConfirm = activityCreateAccountBinding.passwordConfirm.getText().toString();
                String firstName = activityCreateAccountBinding.nameFirst.getText().toString();
                String lastName = activityCreateAccountBinding.nameLast.getText().toString();

                if (firstName.trim().isEmpty()) {
                    activityCreateAccountBinding.nameFirst.setError("missing");
                } else if (lastName.trim().isEmpty()) {
                    activityCreateAccountBinding.nameLast.setError("missing");
                } else if (email.trim().isEmpty()) {
                    activityCreateAccountBinding.email.setError("missing");
                } else if (password.isEmpty()) {
                    activityCreateAccountBinding.password.setError("missing");
                } else if (password.length() < 6) {
                    activityCreateAccountBinding.password.setError("at 6 least characters");
                } else if (passwordConfirm.isEmpty()) {
                    activityCreateAccountBinding.passwordConfirm.setError("missing");
                } else if (!password.equals(passwordConfirm)) {
                    activityCreateAccountBinding.password.setError("miss match");
                    activityCreateAccountBinding.passwordConfirm.setError("miss match");
                } else {
                    activityCreateAccountBinding.progressR.setVisibility(View.VISIBLE);
                    activityCreateAccountBinding.progressR.setAlpha(1);
                    activityCreateAccountBinding.progressR.animate().scaleX(1f);
                    activityCreateAccountBinding.progressR.animate().scaleY(1f);

                    viewModel.doCreateAccount(firstName,lastName,email,password);

                }

            }
        });

        viewModel.isSuccessLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    activityCreateAccountBinding.progressR.setVisibility(View.VISIBLE);
                    activityCreateAccountBinding.progressR.setAlpha(1);
                    activityCreateAccountBinding.progressR.animate().scaleX(1f);
                    activityCreateAccountBinding.progressR.animate().scaleY(1f);
                } else {

                    activityCreateAccountBinding.progressR.animate().scaleX(0f);
                    activityCreateAccountBinding.progressR.animate().scaleY(0f);
                    activityCreateAccountBinding.progressR.setAlpha(0);
                    activityCreateAccountBinding.progressR.setVisibility(View.GONE);
                }
            }
        });

    }
}