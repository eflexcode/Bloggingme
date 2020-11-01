package com.eflexsoft.bloggingme.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.eflexsoft.bloggingme.repository.SearchRepository;

public class SearchViewModel extends AndroidViewModel {

    SearchRepository repository;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository = new SearchRepository(application);
    }

    public void addLike(String postId) {
        repository.addLike(postId);
    }

    public void removeLike(String postId) {
        repository.removeLike(postId);
    }


}
