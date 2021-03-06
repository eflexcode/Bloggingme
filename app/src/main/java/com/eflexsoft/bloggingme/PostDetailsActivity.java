package com.eflexsoft.bloggingme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eflexsoft.bloggingme.databinding.ActivityPostAcitivtyBinding;
import com.eflexsoft.bloggingme.databinding.ActivityPostDetailsBinding;
import com.eflexsoft.bloggingme.model.Post;
import com.eflexsoft.bloggingme.model.User;
import com.eflexsoft.bloggingme.viewmodel.PostdetailedViewModel;

public class PostDetailsActivity extends AppCompatActivity {

    Intent intent;

    PostdetailedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_post_details);

        ActivityPostDetailsBinding detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_post_details);

        detailsBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewModel = new ViewModelProvider(this).get(PostdetailedViewModel.class);

//        Intent intent = new Intent(getContext(), PostDetailsActivity.class);
//        intent.putExtra("id", model.getPosterId());
//        intent.putExtra("title", model.getStoryTitle());
//        intent.putExtra("body", model.getStoryBody());
//        intent.putExtra("postId",model.getPostId());
//        intent.putExtra("postImage",model.getPostImage())
//        startActivity(intent);

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

        if (postImage.equals("none")) {
            detailsBinding.imagePost.setVisibility(View.GONE);
        } else {
            detailsBinding.imagePost.setVisibility(View.VISIBLE);
        }

        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_lift);

        if (detailsBinding.likeSwitcher2.getCurrentView() == null) {

            detailsBinding.likeSwitcher2.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {

                    TextView textView = new TextView(PostDetailsActivity.this);
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                    textView.setTextSize(15);

                    return textView;
                }
            });
        }

        detailsBinding.likeSwitcher2.setInAnimation(in);
        detailsBinding.likeSwitcher2.setOutAnimation(out);

        detailsBinding.likeSwitcher2.setText(String.valueOf(comments));

        if (detailsBinding.likeSwitcher.getCurrentView() == null) {

            detailsBinding.likeSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {

                    TextView textView = new TextView(PostDetailsActivity.this);
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                    textView.setTextSize(15);

                    return textView;
                }
            });
        }

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.like3);
        mediaPlayer.setVolume(1, 1);

        detailsBinding.likeSwitcher.setInAnimation(in);
        detailsBinding.likeSwitcher.setOutAnimation(out);

        detailsBinding.likeSwitcher.setText(String.valueOf(likes));

        detailsBinding.setPost(post);

        viewModel.getUserMutableLiveData(id).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.color.brown);
                    requestOptions.error(R.drawable.no_p);

                    Glide.with(PostDetailsActivity.this).load(user.getProPicUrl()).apply(requestOptions).into(detailsBinding.detailedProPic);

                    detailsBinding.uName.setText(user.getFirstName() + " " + user.getLastName());

                }
            }
        });

        long[] count = {likes};
        boolean[] isLiked = {false};

        viewModel.getIsLiked(postId).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    detailsBinding.starButton.setImageResource(R.drawable.ic_heart_liked);
                    isLiked[0] = true;
                } else {
                    detailsBinding.starButton.setImageResource(R.drawable.ic_heart_not_liked);
                    isLiked[0] = false;
                }
            }
        });

        detailsBinding.starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLiked[0]) {
                    detailsBinding.starButton.setImageResource(R.drawable.ic_heart_not_liked);
                    isLiked[0] = false;
                    count[0] = count[0] - 1;
                    viewModel.removeLike(postId);
//                            mediaPlayer.start();
                } else {
                    detailsBinding.starButton.setImageResource(R.drawable.ic_heart_liked);
                    isLiked[0] = true;
                    count[0] = count[0] + 1;
                    viewModel.addLike(postId);
                    mediaPlayer.start();
                }

                detailsBinding.likeSwitcher.setText(String.valueOf(count[0]));

            }
        });
        detailsBinding.messageComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDetailsActivity.this, CommentsActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("body", body);
                intent.putExtra("postId", postId);
                intent.putExtra("postImage", postImage);
                intent.putExtra("date", date);
                intent.putExtra("likes", likes);
                intent.putExtra("comments", comments);
                startActivity(intent);
            }
        });
        detailsBinding.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plan");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Blogging me share");
                String text = title + "\n\n want to read more? get the app on google play store it's called blogging me";
                intent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(intent, "share with :"));
            }
        });
    }

}