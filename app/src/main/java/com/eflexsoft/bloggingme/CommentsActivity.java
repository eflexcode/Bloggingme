package com.eflexsoft.bloggingme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eflexsoft.bloggingme.databinding.ActivityCommentsBinding;
import com.eflexsoft.bloggingme.databinding.CommetItemBinding;
import com.eflexsoft.bloggingme.databinding.PostItemBinding;
import com.eflexsoft.bloggingme.model.Comment;
import com.eflexsoft.bloggingme.model.Post;
import com.eflexsoft.bloggingme.model.User;
import com.eflexsoft.bloggingme.viewholder.ComentItemViewHolder;
import com.eflexsoft.bloggingme.viewholder.PostItemViewHolder;
import com.eflexsoft.bloggingme.viewmodel.CommentsViewModel;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class CommentsActivity extends AppCompatActivity {

    ActivityCommentsBinding activityCommentsBinding;
    String postId;
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
        postId = intent.getStringExtra("postId");
        String postImage = intent.getStringExtra("postImage");
        String date = intent.getStringExtra("date");
        long likes = intent.getLongExtra("likes", 0);
        long comments = intent.getLongExtra("comments", 0);

        Post post = new Post(title, body, id, postId, postImage, date, likes, comments);

        activityCommentsBinding.setPost(post);
        activityCommentsBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        commentsViewModel.getUserMutableLiveData(id).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.error(R.drawable.no_p);
                requestOptions.placeholder(R.color.brown);

                Glide.with(CommentsActivity.this).load(user.getProPicUrl()).apply(requestOptions).into(activityCommentsBinding.detailedProPic);

            }
        });

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

                    commentsViewModel.sendComment(commentText, postId, imageUri);
                    activityCommentsBinding.commentText.setText("");
                    activityCommentsBinding.commentImg.setImageURI(null);
                    activityCommentsBinding.commentImg.setVisibility(View.GONE);
                    Toast.makeText(CommentsActivity.this, "swipe down to see comment", Toast.LENGTH_SHORT).show();

                }
            }
        });

        activityCommentsBinding.swipeRef.setColorSchemeResources(R.color.colorPrimary);
        activityCommentsBinding.swipeRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecycler();
            }
        });

        activityCommentsBinding.swipeRef.setRefreshing(true);
        initRecycler();

    }

    public void initRecycler() {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        PagedList.Config builder = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPrefetchDistance(10)
                .setPageSize(20)
                .setEnablePlaceholders(false)
                .build();



        Query query = firebaseFirestore.collection("Posts").document(postId)
                .collection("comments").orderBy("commentId", Query.Direction.DESCENDING);

//        Query query = FirebaseFirestore.getInstance().collection("Posts").orderBy("postId", Query.Direction.DESCENDING);

        FirestorePagingOptions<Comment> pagingOptions = new FirestorePagingOptions.Builder<Comment>()
                .setLifecycleOwner(this)
                .setQuery(query, builder, Comment.class)
                .build();

        FirestorePagingAdapter<Comment, ComentItemViewHolder> firestorePagingAdapter = new FirestorePagingAdapter<Comment, ComentItemViewHolder>(pagingOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ComentItemViewHolder holder, int position, @NonNull Comment model) {

                holder.commetItemBinding.setComment(model);

                DocumentReference documentReference2 = FirebaseFirestore.getInstance().collection("Users").document(model.getCommenterId());
                documentReference2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        User user = value.toObject(User.class);
                        holder.commetItemBinding.setUser(user);

                        holder.commetItemBinding.commentUserName.setText(user.getFirstName() + " " + user.getLastName());

                    }
                });

                if (model.getCommentImage().equals("none")){
                    holder.commetItemBinding.imageComment.setVisibility(View.GONE);
                }else {
                    holder.commetItemBinding.imageComment.setVisibility(View.VISIBLE);
                }


            }

            @NonNull
            @Override
            public ComentItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

                CommetItemBinding commetItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.commet_item, parent, false);

                return new ComentItemViewHolder(commetItemBinding);
            }

            @Override
            protected void onLoadingStateChanged(@NonNull LoadingState state) {
                super.onLoadingStateChanged(state);

                switch (state) {
                    case ERROR:

                        activityCommentsBinding.swipeRef.setRefreshing(false);
                        break;
                    case FINISHED:
                        activityCommentsBinding.swipeRef.setRefreshing(false);
                        break;
                    case LOADING_MORE:
                        activityCommentsBinding.swipeRef.setRefreshing(true);
                        break;
                    case LOADING_INITIAL:
                        activityCommentsBinding.swipeRef.setRefreshing(true);
                        break;
                    case LOADED:
                        activityCommentsBinding.swipeRef.setRefreshing(false);
                        break;
                }


            }

        };

        activityCommentsBinding.cRecycler.setLayoutManager(new LinearLayoutManager(this));
        activityCommentsBinding.cRecycler.setAdapter(firestorePagingAdapter);

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