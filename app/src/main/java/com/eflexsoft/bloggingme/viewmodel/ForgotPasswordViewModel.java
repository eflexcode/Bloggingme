package com.eflexsoft.bloggingme.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.bloggingme.repository.ForgotPasswordRepository;
import com.eflexsoft.bloggingme.repository.LoginRepository;

public class ForgotPasswordViewModel extends AndroidViewModel {

    ForgotPasswordRepository loginRepository;

   public MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();

    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);

        loginRepository = new ForgotPasswordRepository(application);

    }

    public void doPasswordReset(String email) {
        loginRepository.doPasswordReset(email);
    }

    public LiveData<Boolean> isSuccessLiveData() {
        isSuccess = loginRepository.isSuccess;

        return isSuccess;
    }

}
