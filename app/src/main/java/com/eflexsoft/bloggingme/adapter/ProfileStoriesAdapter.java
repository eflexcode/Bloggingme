package com.eflexsoft.bloggingme.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ProfileStoriesAdapter extends FragmentPagerAdapter {
    List<String> stringList;
    List<Fragment> fragmentList;

    public ProfileStoriesAdapter(@NonNull FragmentManager fm, List<String> stringList, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }
}
