package com.eflexsoft.bloggingme.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eflexsoft.bloggingme.R;
import com.eflexsoft.bloggingme.databinding.FragmentSearchBinding;


public class SearchFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentSearchBinding fragmentSearchBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false);

        View view = fragmentSearchBinding.getRoot();

        return view;
    }
}