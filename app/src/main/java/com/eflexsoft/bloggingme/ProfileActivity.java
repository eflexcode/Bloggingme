package com.eflexsoft.bloggingme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eflexsoft.bloggingme.adapter.ProfileStoriesAdapter;
import com.eflexsoft.bloggingme.databinding.ActivityProfileAcitvityBinding;
import com.eflexsoft.bloggingme.fragment.DraftFragment;
import com.eflexsoft.bloggingme.fragment.MyStoriesFragment;
import com.eflexsoft.bloggingme.model.User;
import com.eflexsoft.bloggingme.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    ProfileStoriesAdapter adapter;
    ProfileViewModel viewModel;
    ActivityProfileAcitvityBinding activityProfileAcitvityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile_acitvity);

        activityProfileAcitvityBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile_acitvity);

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.getUserDetails();

        List<String> strings = new ArrayList<>();
        strings.add("Stories");
        strings.add("Drafts");

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MyStoriesFragment());
        fragments.add(new DraftFragment());

        adapter = new ProfileStoriesAdapter(getSupportFragmentManager(), strings, fragments);


        activityProfileAcitvityBinding.viewPager.setAdapter(adapter);
        activityProfileAcitvityBinding.tabLayout.setupWithViewPager(activityProfileAcitvityBinding.viewPager);

        viewModel.getUserMutableLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                activityProfileAcitvityBinding.net.scrollTo(0,0);
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.color.brown);
                requestOptions.error(R.drawable.no_p);

                activityProfileAcitvityBinding.uName.setText(user.getFirstName() + " " + user.getLastName());
                Glide.with(ProfileActivity.this).load(user.getProPicUrl()).apply(requestOptions).into(activityProfileAcitvityBinding.proProPic);

                if (user.getBio() == null) {
                    activityProfileAcitvityBinding.uBio.setText("bio unavailable");
                } else {
                    activityProfileAcitvityBinding.uBio.setText(user.getBio());
                }

                if (user.getLocation() == null) {
                    activityProfileAcitvityBinding.location.setText("location unavailable");
                } else {
                    activityProfileAcitvityBinding.location.setText(user.getLocation());
                }

                if (user.getDate() == null) {
                    activityProfileAcitvityBinding.time.setText("dob unavailable");
                } else {
                    activityProfileAcitvityBinding.time.setText(user.getDate());
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        activityProfileAcitvityBinding.net.scrollTo(0,0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        activityProfileAcitvityBinding.net.scrollTo(0,0);
    }
}