package com.eflexsoft.bloggingme.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.bloggingme.model.User;
import com.eflexsoft.bloggingme.repository.HomeFragmentRepository;

public class FragmentHomeViewModel extends AndroidViewModel {

    HomeFragmentRepository repository;

    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public FragmentHomeViewModel(@NonNull Application application) {
        super(application);

        repository = new HomeFragmentRepository(application);
        getUserDetails();

    }

    public void getUserDetails() {
        repository.getUserDetails();
    }

    public LiveData<User> getUserMutableLiveData() {
        userMutableLiveData = repository.userMutableLiveData;
        return userMutableLiveData;
    }
}
