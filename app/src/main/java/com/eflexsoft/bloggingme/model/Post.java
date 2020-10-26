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
    private long likes;
    private long comments;

    public Post() {
    }

    public Post(String storyTitle, String storyBody, String posterId, String postId, String postImage,String date,long likes,long comments) {
        StoryTitle = storyTitle;
        StoryBody = storyBody;
        PosterId = posterId;
        this.postId = postId;
        this.postImage = postImage;
        this.date = date;
        this.likes = likes;
        this.comments = comments;
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

    public long getLikes() {
        return likes;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }
//    @BindingAdapter("android:use_pretty_date")
//    public static void setTime(TextView textView,String time){
//
//    }

    @BindingAdapter("android:load_image")
    public static void setImg(ImageView circleImageView, String utl){

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.color.brown);
        requestOptions.error(R.color.brown);

        Glide.with(circleImageView).load(utl).apply(requestOptions).into(circleImageView);

    }

    @BindingAdapter("android:set_Pretty_Time")
    public static void setTime(TextView textView, String time){

        textView.setText(UtilsClass.formatDate(time));

    }
}
