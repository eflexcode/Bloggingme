package com.eflexsoft.bloggingme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.eflexsoft.bloggingme.databinding.ActivityCommentsBinding;
import com.eflexsoft.bloggingme.model.Post;
import com.eflexsoft.bloggingme.viewmodel.CommentsViewModel;

public class CommentsActivity extends AppCompatActivity {

    ActivityCommentsBinding activityCommentsBinding;

    Uri imageUri;
    Intent intent;

    CommentsViewModel commentsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_comments);

        activityCommentsBinding = DataBindingUtil.setContentView(this, R.layout.activity_comments);

        commentsViewModel = new ViewModelProvider(this).get(CommentsViewModel.class);

        intent = getIntent();
        String id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        String postId = intent.getStringExtra("postId");
        String postImage = intent.getStringExtra("postImage");
        String date = intent.getStringExtra("date");
        long likes = intent.getLongExtra("likes", 0);
        long comments = intent.getLongExtra("comments", 0);

        Post post = new Post(title, body, id, postId, postImage, date, likes, comments);

        activityCommentsBinding.setPost(post);

        activityCommentsBinding.commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String s1 = s.toString();

                if (s1.isEmpty()) {
                    activityCommentsBinding.send.setImageResource(R.drawable.ic_send_stroke);
                } else {
                    activityCommentsBinding.send.setImageResource(R.drawable.ic_send_bold);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityCommentsBinding.addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFirstImage();
            }
        });

        activityCommentsBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = activityCommentsBinding.commentText.getText().toString();
                if (!commentText.isEmpty()) {

                    commentsViewModel.sendComment(commentText, "", imageUri);

                }
            }
        });

    }

    public void pickFirstImage() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 7);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {
            activityCommentsBinding.commentImg.setVisibility(View.VISIBLE);
            imageUri = data.getData();
            activityCommentsBinding.commentImg.setImageURI(imageUri);

        }

    }

}