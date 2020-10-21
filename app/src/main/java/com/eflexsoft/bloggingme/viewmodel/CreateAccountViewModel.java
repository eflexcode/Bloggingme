package com.eflexsoft.bloggingme.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.bloggingme.repository.CreateAccountRepository;

public class CreateAccountViewModel extends AndroidViewModel {

    CreateAccountRepository repository;

    public MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();

    public CreateAccountViewModel(@NonNull Application application) {
        super(application);

        repository = new CreateAccountRepository(application);

    }

    public void doCreateAccount(String firstName, String lastName, String email, String password) {
        repository.doCreateAccount(firstName, lastName, email, password);
    }

    public LiveData<Boolean> isSuccessLiveData() {
        isSuccess = repository.isSuccess;

        return isSuccess;
    }

}
