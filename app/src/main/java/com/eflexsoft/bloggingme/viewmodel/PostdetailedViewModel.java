package com.eflexsoft.bloggingme.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.bloggingme.model.User;
import com.eflexsoft.bloggingme.repository.HomeFragmentRepository;
import com.eflexsoft.bloggingme.repository.PostDetailedRepository;

public class PostdetailedViewModel extends AndroidViewModel {

    PostDetailedRepository repository;

    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLiked = new MutableLiveData<>();

    public PostdetailedViewModel(@NonNull Application application) {
        super(application);

        repository = new PostDetailedRepository(application);

    }

    public void getUserDetails() {

    }

    public LiveData<User> getUserMutableLiveData(String id) {
        repository.getUserDetails(id);
        userMutableLiveData = repository.userMutableLiveData;
        return userMutableLiveData;
    }

    public LiveData<Boolean> getIsLiked(String postId) {
        repository.getIsLiked(postId);
        isLiked = repository.isLiked;
        return isLiked;
    }

    public void addLike(String postId) {
        repository.addLike(postId);
    }

    public void removeLike(String postId) {
        repository.removeLike(postId);
    }

}
