package com.eflexsoft.bloggingme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.eflexsoft.bloggingme.databinding.ActivityPostAcitivtyBinding;
import com.eflexsoft.bloggingme.room.DraftEntity;
import com.eflexsoft.bloggingme.room.DraftViewModel;
import com.eflexsoft.bloggingme.viewmodel.PostViewModel;
import com.muddzdev.styleabletoast.StyleableToast;

public class PostActivity extends AppCompatActivity {


    DraftViewModel draftViewModel;
    PostViewModel postViewModel;
    Uri imageUri;
    ActivityPostAcitivtyBinding postAcitivtyBinding;
    ProgressDialog verifyProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_post_acitivty);

        postAcitivtyBinding = DataBindingUtil.setContentView(this, R.layout.activity_post_acitivty);

        draftViewModel = new ViewModelProvider(this).get(DraftViewModel.class);
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        verifyProgressBar = new ProgressDialog(this);
        verifyProgressBar.setMessage("Publishing Story...");
        verifyProgressBar.setCancelable(false);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.success_sound);

        postViewModel.isSuccessLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    verifyProgressBar.dismiss();
                    mediaPlayer.start();
                    StyleableToast.makeText(PostActivity.this, "Story published successfully", R.style.StyleableToast).show();
                    finish();
                } else {
                    verifyProgressBar.dismiss();
                }
            }
        });

        postAcitivtyBinding.draft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = postAcitivtyBinding.postTitle.getText().toString();
                String body = postAcitivtyBinding.postBody.getText().toString();

                if (!title.isEmpty()) {

                    DraftEntity draftEntity = new DraftEntity(title, body);

                    draftViewModel.insert(draftEntity);
                    StyleableToast.makeText(PostActivity.this, "Draft saved successfully", R.style.StyleableToast).show();

                    finish();

                } else {

                    StyleableToast.makeText(PostActivity.this, "Cannot save draft without a title", R.style.StyleableToastW).show();

                }
            }
        });

        postAcitivtyBinding.imageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pickFirstImage();

            }
        });

        postAcitivtyBinding.publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = postAcitivtyBinding.postTitle.getText().toString();
                String body = postAcitivtyBinding.postBody.getText().toString();

                if (!title.trim().isEmpty() && !body.trim().isEmpty()) {

                    postViewModel.sendPost(title, body, imageUri);
                    verifyProgressBar.show();
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
            postAcitivtyBinding.imagePost.setVisibility(View.VISIBLE);
            imageUri = data.getData();
            postAcitivtyBinding.imagePost.setImageURI(imageUri);

            postAcitivtyBinding.imageText.setText("Change image");
//            postAcitivtyBinding.imageText.setTextStyle="bold"
        }

    }

    @Override
    public void onBackPressed() {

        String title = postAcitivtyBinding.postTitle.getText().toString();
        String body = postAcitivtyBinding.postBody.getText().toString();

        if (!title.isEmpty()) {

            DraftEntity draftEntity = new DraftEntity(title, body);

            draftViewModel.insert(draftEntity);
            StyleableToast.makeText(PostActivity.this, "draft saved successfully", R.style.StyleableToast).show();

        }

        super.onBackPressed();
    }

}