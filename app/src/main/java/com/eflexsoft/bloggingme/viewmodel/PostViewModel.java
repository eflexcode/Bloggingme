package com.eflexsoft.bloggingme.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.bloggingme.repository.PostRepository;

public class PostViewModel extends AndroidViewModel {

    PostRepository repository;
    public MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();

    public PostViewModel(@NonNull Application application) {
        super(application);

        repository = new PostRepository(application);

    }

    public void sendPost(String title, String body, Uri postImg) {
        repository.sendPost(title, body, postImg);
    }

    public LiveData<Boolean> isSuccessLiveData() {
        isSuccess = repository.isSuccess;

        return isSuccess;
    }
}
