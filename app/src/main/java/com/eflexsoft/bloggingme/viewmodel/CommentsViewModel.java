package com.eflexsoft.bloggingme.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.eflexsoft.bloggingme.repository.CommentsRepository;

public class CommentsViewModel extends AndroidViewModel {

    CommentsRepository repository;

    public CommentsViewModel(@NonNull Application application) {
        super(application);
        repository = new CommentsRepository(application);
    }

    public void sendComment(String commentText, String postId, Uri imageUri) {
        repository.sendComment(commentText, postId, imageUri);
    }

}
