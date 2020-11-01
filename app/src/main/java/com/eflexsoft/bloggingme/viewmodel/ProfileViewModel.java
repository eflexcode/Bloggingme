package com.eflexsoft.bloggingme.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.bloggingme.model.User;
import com.eflexsoft.bloggingme.repository.ProfileRepository;

public class ProfileViewModel extends AndroidViewModel {

    ProfileRepository repository;
    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);

        repository = new ProfileRepository(application);

    }

    public void getUserDetails() {
        repository.getUserDetails();
    }

    public LiveData<User> getUserMutableLiveData() {
        userMutableLiveData = repository.userMutableLiveData;
        return userMutableLiveData;
    }

}
