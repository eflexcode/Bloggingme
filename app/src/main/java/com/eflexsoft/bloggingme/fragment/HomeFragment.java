package com.eflexsoft.bloggingme.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eflexsoft.bloggingme.PostDetailsActivity;
import com.eflexsoft.bloggingme.R;
import com.eflexsoft.bloggingme.databinding.FragmentHomeBinding;
import com.eflexsoft.bloggingme.databinding.PostItemBinding;
import com.eflexsoft.bloggingme.model.Post;
import com.eflexsoft.bloggingme.model.User;
import com.eflexsoft.bloggingme.viewholder.PostItemViewHolder;
import com.eflexsoft.bloggingme.viewmodel.FragmentHomeViewModel;
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;


public class HomeFragment extends Fragment {

    FragmentHomeBinding fragmentHomeBinding;

    FragmentHomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);

        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        View view = fragmentHomeBinding.getRoot();

        fragmentHomeBinding.swipeRef.setColorSchemeResources(R.color.colorPrimary);

        initRecycler();

        viewModel = new ViewModelProvider(getActivity()).get(FragmentHomeViewModel.class);
        viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.no_p);
                    requestOptions.error(R.drawable.no_p);

                    Glide.with(getActivity()).load(user.getProPicUrl()).apply(requestOptions).into(fragmentHomeBinding.homeProPic);


                }
            }
        });

        fragmentHomeBinding.swipeRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecycler();
            }
        });

        fragmentHomeBinding.netRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHomeBinding.swipeRef.setRefreshing(true);
                initRecycler();
            }
        });

        return view;


    }

    public void initRecycler() {
        PagedList.Config builder = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPrefetchDistance(10)
                .setPageSize(20)
                .setEnablePlaceholders(false)
                .build();

        Query query = FirebaseFirestore.getInstance().collection("Posts").orderBy("postId", Query.Direction.DESCENDING);

        FirestorePagingOptions<Post> pagingOptions = new FirestorePagingOptions.Builder<Post>()
                .setLifecycleOwner(this)
                .setQuery(query, builder, Post.class)
                .build();

        FirestorePagingAdapter<Post, PostItemViewHolder> firestorePagingAdapter = new FirestorePagingAdapter<Post, PostItemViewHolder>(pagingOptions) {
            @Override
            protected void onBindViewHolder(@NonNull PostItemViewHolder holder, int position, @NonNull Post model) {

                holder.postItemBinding.setPost(model);
                if (model.getPostImage().equals("none")) {
                    holder.postItemBinding.imagePost.setVisibility(View.GONE);
                } else {
                    holder.postItemBinding.imagePost.setVisibility(View.VISIBLE);
                }

                String id = model.getPosterId();

                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(id);
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        User user = value.toObject(User.class);

                        assert user != null;
                        holder.postItemBinding.postUserName.setText(user.getFirstName() + " " + user.getLastName());


                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.placeholder(R.drawable.no_p);
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
                        intent.putExtra("postId",model.getPostId());
                        intent.putExtra("postImage",model.getPostImage());
                        startActivity(intent);
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
                fragmentHomeBinding.proBar.setVisibility(View.GONE);
                fragmentHomeBinding.linEr.setVisibility(View.VISIBLE);
                fragmentHomeBinding.swipeRef.setRefreshing(false);
            }

            @Override
            protected void onLoadingStateChanged(@NonNull LoadingState state) {
                super.onLoadingStateChanged(state);

                switch (state) {
                    case ERROR:

                        fragmentHomeBinding.proBar.setVisibility(View.GONE);
                        fragmentHomeBinding.linEr.setVisibility(View.VISIBLE);
                        fragmentHomeBinding.swipeRef.setRefreshing(false);
                        break;
                    case FINISHED:
//                        fragmentHomeBinding.proBar.setVisibility(View.GONE);
//                        fragmentHomeBinding.linEr.setVisibility(View.VISIBLE);
//                        fragmentHomeBinding.swipeRef.setRefreshing(false);
                        break;
                    case LOADING_MORE:
                        fragmentHomeBinding.linEr.setVisibility(View.GONE);
                        fragmentHomeBinding.swipeRef.setRefreshing(false);
                        break;
                    case LOADING_INITIAL:
                        fragmentHomeBinding.proBar.setVisibility(View.VISIBLE);
                        fragmentHomeBinding.linEr.setVisibility(View.GONE);
                        fragmentHomeBinding.swipeRef.setRefreshing(false);
                        break;
                    case LOADED:
                        fragmentHomeBinding.proBar.setVisibility(View.GONE);
                        fragmentHomeBinding.linEr.setVisibility(View.GONE);
                        fragmentHomeBinding.swipeRef.setRefreshing(false);
                        break;
                }


            }


        };


        fragmentHomeBinding.homeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentHomeBinding.homeRecycler.setAdapter(firestorePagingAdapter);


    }

}