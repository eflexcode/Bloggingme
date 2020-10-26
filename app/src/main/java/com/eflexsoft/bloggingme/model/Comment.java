package com.eflexsoft.bloggingme.model;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eflexsoft.bloggingme.R;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Comment {

//    HashMap<String, Object> map = new HashMap<>();
//                        map.put("commenterId", firebaseAuth.getUid());
//                        map.put("commentId", System.currentTimeMillis());
//                        map.put("date", date);
//                        map.put("commentsBody", commentText);
//                        map.put("CommentImage", downloadUri);

    private String commenterId;
    private long commentId;
    private String date;
    private String commentsBody;
    private String commentImage;

    public Comment() {
    }

    public Comment(String commenterId, long commentId, String date, String commentsBody, String commentImage) {
        this.commenterId = commenterId;
        this.commentId = commentId;
        this.date = date;
        this.commentsBody = commentsBody;
        this.commentImage = commentImage;
    }

    public String getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(String commenterId) {
        this.commenterId = commenterId;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCommentsBody() {
        return commentsBody;
    }

    public void setCommentsBody(String commentsBody) {
        this.commentsBody = commentsBody;
    }

    public String getCommentImage() {
        return commentImage;
    }

    public void setCommentImage(String commentImage) {
       this.commentImage = commentImage;
    }

    @BindingAdapter("android:loadCommentImage")
    public static void loadCommentImage(ImageView imageView,String url){

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.color.brown);

        Glide.with(imageView).load(url).apply(requestOptions).into(imageView);

    }

    @BindingAdapter("android:loadProPicImage")
    public static void loadProPicImage(CircleImageView imageView, String url){

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.no_p);

        Glide.with(imageView).load(url).apply(requestOptions).into(imageView);

    }

    @BindingAdapter("android:setTimeText")
    public static void setTimeText(TextView textView,String time){

        textView.setText(UtilsClass.formatDate(time));

    }

    @BindingAdapter("android:setName")
    public static void setName(TextView textView,String firstName,String secondName){

//        textView.setText(UtilsClass.formatDate(time));

    }

}
