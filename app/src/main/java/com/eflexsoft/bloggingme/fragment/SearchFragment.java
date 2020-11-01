package com.eflexsoft.bloggingme.fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eflexsoft.bloggingme.CommentsActivity;
import com.eflexsoft.bloggingme.PostDetailsActivity;
import com.eflexsoft.bloggingme.R;
import com.eflexsoft.bloggingme.databinding.FragmentSearchBinding;
import com.eflexsoft.bloggingme.databinding.PostItemBinding;
import com.eflexsoft.bloggingme.model.Post;
import com.eflexsoft.bloggingme.model.User;
import com.eflexsoft.bloggingme.viewholder.PostItemViewHolder;
import com.eflexsoft.bloggingme.viewmodel.SearchViewModel;
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


public class SearchFragment extends Fragment {

    FragmentSearchBinding fragmentSearchBinding;
    SearchViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewModel = new ViewModelProvider(getActivity()).get(SearchViewModel.class);

        fragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);

        View view = fragmentSearchBinding.getRoot();

        fragmentSearchBinding.swipeRef.setColorSchemeResources(R.color.colorPrimary);

        fragmentSearchBinding.swipeRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String text = fragmentSearchBinding.searchForPostWithTitle.getText().toString();

                if (!text.trim().isEmpty()) {
                    doSearchWithFirebaseUi(fragmentSearchBinding.searchForPostWithTitle.getText().toString());
                } else {
                    fragmentSearchBinding.swipeRef.setRefreshing(false);
                }

            }
        });

        fragmentSearchBinding.searchForPostWithTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!s.toString().trim().isEmpty()) {
                            doSearchWithFirebaseUi(s.toString());
                            fragmentSearchBinding.swipeRef.setRefreshing(true);
                        }
                    }
                }, 2000);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    public void doSearchWithFirebaseUi(String keyword) {

//        Query query = FirebaseFirestore.getInstance()
//                .collection("Posts")
//                .orderBy("PostId", Query.Direction.DESCENDING);
////                .startAt(keyword)
////                .endAt(keyword + "\uf8ff");
//
//        PagedList.Config config = new PagedList.Config.Builder()
//                .setEnablePlaceholders(false)
//                .setPageSize(20)
//                .setPrefetchDistance(10)
//                .setInitialLoadSizeHint(10)
//                .build();
//
//        FirestorePagingOptions<Post> pagingOptions = new FirestorePagingOptions.Builder<Post>()
//                .setLifecycleOwner(this)
//                .setQuery(query, config, Post.class)
//                .build();

        PagedList.Config builder = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPrefetchDistance(10)
                .setPageSize(20)
                .setEnablePlaceholders(false)
                .build();

        Query query = FirebaseFirestore.getInstance().collection("Posts").orderBy("StoryTitle")
                .startAt(keyword)
                .endAt(keyword + "\uf8ff");

//        Query query = FirebaseFirestore.getInstance().collection("Posts").orderBy("StoryTitle")
//                .whereArrayContains("StoryTitle",keyword);

        FirestorePagingOptions<Post> pagingOptions = new FirestorePagingOptions.Builder<Post>()
                .setLifecycleOwner(this)
                .setQuery(query, builder, Post.class)
                .build();

        FirestorePagingAdapter<Post, PostItemViewHolder> firestorePagingAdapter = new FirestorePagingAdapter<Post, PostItemViewHolder>(pagingOptions) {
            @Override
            protected void onBindViewHolder(@NonNull PostItemViewHolder holder, int position, @NonNull Post model) {

                holder.postItemBinding.setPost(model);

                MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.like3);
                mediaPlayer.setVolume(1, 1);

                if (model.getPostImage().equals("none")) {
                    holder.postItemBinding.imagePost.setVisibility(View.GONE);
                } else {
                    holder.postItemBinding.imagePost.setVisibility(View.VISIBLE);
                }

                String id = model.getPosterId();

                if (holder.postItemBinding.likeSwitcher.getCurrentView() == null) {

                    holder.postItemBinding.likeSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                        @Override
                        public View makeView() {

                            TextView textView = new TextView(getContext());
                            textView.setGravity(Gravity.CENTER_VERTICAL);
                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            textView.setTextSize(15);

                            return textView;
                        }
                    });
                }

                if (holder.postItemBinding.likeSwitcher2.getCurrentView() == null) {

                    holder.postItemBinding.likeSwitcher2.setFactory(new ViewSwitcher.ViewFactory() {
                        @Override
                        public View makeView() {

                            TextView textView = new TextView(getContext());
                            textView.setGravity(Gravity.CENTER_VERTICAL);
                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            textView.setTextSize(15);

                            return textView;
                        }
                    });
                }

                long[] count = {model.getLikes()};
                boolean[] isLiked = {false};

                holder.postItemBinding.likeSwitcher.setText(String.valueOf(model.getLikes()));

                Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
                Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_lift);

                holder.postItemBinding.likeSwitcher.setInAnimation(in);
                holder.postItemBinding.likeSwitcher.setOutAnimation(out);

                holder.postItemBinding.likeSwitcher2.setText(String.valueOf(model.getComments()));

                holder.postItemBinding.likeSwitcher2.setInAnimation(in);
                holder.postItemBinding.likeSwitcher2.setOutAnimation(out);

                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

                DocumentReference documentReference = firebaseFirestore.collection("Posts").document(model.getPostId())
                        .collection("likesId").document(FirebaseAuth.getInstance().getUid());

                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        assert value != null;
                        if (value.exists()) {
                            holder.postItemBinding.starButton.setImageResource(R.drawable.ic_heart_liked);
                            isLiked[0] = true;
                        } else {
                            holder.postItemBinding.starButton.setImageResource(R.drawable.ic_heart_not_liked);
                            isLiked[0] = false;
                        }
                    }
                });

                holder.postItemBinding.starButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (isLiked[0]) {
                            holder.postItemBinding.starButton.setImageResource(R.drawable.ic_heart_not_liked);
                            isLiked[0] = false;
                            count[0] = count[0] - 1;
                            viewModel.removeLike(model.getPostId());

                        } else {
                            holder.postItemBinding.starButton.setImageResource(R.drawable.ic_heart_liked);
                            isLiked[0] = true;
                            count[0] = count[0] + 1;
                            viewModel.addLike(model.getPostId());
                            mediaPlayer.start();
                        }

                        holder.postItemBinding.likeSwitcher.setText(String.valueOf(count[0]));

                    }
                });

                DocumentReference documentReference2 = FirebaseFirestore.getInstance().collection("Users").document(id);
                documentReference2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        User user = value.toObject(User.class);

                        assert user != null;
                        holder.postItemBinding.postUserName.setText(user.getFirstName() + " " + user.getLastName());

                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.placeholder(R.color.brown);
                        requestOptions.error(R.drawable.no_p);

                        Glide.with(getActivity()).load(user.getProPicUrl()).apply(requestOptions).into(holder.postItemBinding.postProPic);

                    }
                });



                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getContext(), PostDetailsActivity.class);
                        intent.putExtra("id", model.getPosterId());
                        intent.putExtra("title", model.getStoryTitle());
                        intent.putExtra("body", model.getStoryBody());
                        intent.putExtra("postId", model.getPostId());
                        intent.putExtra("postImage", model.getPostImage());
                        intent.putExtra("date", model.getDate());
                        intent.putExtra("likes", model.getLikes());
                        intent.putExtra("comments", model.getComments());
                        startActivity(intent);
                    }
                });

                holder.postItemBinding.messageComments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), CommentsActivity.class);
                        intent.putExtra("id", model.getPosterId());
                        intent.putExtra("title", model.getStoryTitle());
                        intent.putExtra("body", model.getStoryBody());
                        intent.putExtra("postId", model.getPostId());
                        intent.putExtra("postImage", model.getPostImage());
                        intent.putExtra("date", model.getDate());
                        intent.putExtra("likes", model.getLikes());
                        intent.putExtra("comments", model.getComments());
                        startActivity(intent);
                    }
                });

                holder.postItemBinding.shareBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plan");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Blogging me share");
                        String text = model.getStoryTitle() + "\n\n want to read more? get the app on google play store it's called blogging me";
                        intent.putExtra(Intent.EXTRA_TEXT, text);
                        startActivity(Intent.createChooser(intent, "share with :"));
                    }
                });

            }

            @NonNull
            @Override
            public PostItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

                PostItemBinding postItemBinding = PostItemBinding.inflate(layoutInflater, parent, false);

                return new PostItemViewHolder(postItemBinding);
            }

            @Override
            protected void onError(@NonNull Exception e) {
                super.onError(e);
                fragmentSearchBinding.swipeRef.setRefreshing(false);
            }

            @Override
            protected void onLoadingStateChanged(@NonNull LoadingState state) {
                super.onLoadingStateChanged(state);

                switch (state) {
                    case ERROR:

                        fragmentSearchBinding.swipeRef.setRefreshing(false);
                        break;
                    case FINISHED:

                        fragmentSearchBinding.swipeRef.setRefreshing(false);
                        break;
                    case LOADING_MORE:
                        fragmentSearchBinding.swipeRef.setRefreshing(true);
                        break;
                    case LOADING_INITIAL:

                        fragmentSearchBinding.swipeRef.setRefreshing(true);
                        break;
                    case LOADED:

                        fragmentSearchBinding.swipeRef.setRefreshing(false);
                        break;
                }

            }

        };

        fragmentSearchBinding.search2Recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentSearchBinding.search2Recycler.setAdapter(firestorePagingAdapter);
    }

}