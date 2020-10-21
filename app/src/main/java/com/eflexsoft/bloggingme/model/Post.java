package com.eflexsoft.bloggingme.model;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eflexsoft.bloggingme.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Post {

//      map.put("StoryTitle", title);
//            map.put("StoryBody", body);
//            map.put("PosterId", firebaseAuth.getUid());
//            map.put("postId", postId);
//            map.put("postImage", "none");

    private String StoryTitle;
    private String StoryBody;
    private String PosterId;
    private String postId;
    private String postImage;
    private String date;

    public Post() {
    }

    public Post(String storyTitle, String storyBody, String posterId, String postId, String postImage,String date) {
        StoryTitle = storyTitle;
        StoryBody = storyBody;
        PosterId = posterId;
        this.postId = postId;
        this.postImage = postImage;
        this.date = date;
    }

    public String getStoryTitle() {
        return StoryTitle;
    }

    public void setStoryTitle(String storyTitle) {
        StoryTitle = storyTitle;
    }

    public String getStoryBody() {
        return StoryBody;
    }

    public void setStoryBody(String storyBody) {
        StoryBody = storyBody;
    }

    public String getPosterId() {
        return PosterId;
    }

    public void setPosterId(String posterId) {
        PosterId = posterId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @BindingAdapter("android:use_pretty_date")
    public static void setTime(TextView textView,String time){

    }

    @BindingAdapter("android:load_image")
    public static void setImg(ImageView circleImageView, String utl){

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.ic_broken_image);

        Glide.with(circleImageView).load(utl).apply(requestOptions).into(circleImageView);

    }

}
