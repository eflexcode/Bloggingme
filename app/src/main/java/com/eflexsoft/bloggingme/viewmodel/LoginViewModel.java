package com.eflexsoft.bloggingme.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eflexsoft.bloggingme.repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {

    LoginRepository loginRepository;

   public MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);

        loginRepository = new LoginRepository(application);

    }

    public void doLogin(String email, String password) {
        loginRepository.doSignIn(email, password);
    }

    public LiveData<Boolean> isSuccessLiveData() {
        isSuccess = loginRepository.isSuccess;

        return isSuccess;
    }

}
