package com.eflexsoft.bloggingme.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eflexsoft.bloggingme.R;
import com.eflexsoft.bloggingme.adapter.DraftAdapter;
import com.eflexsoft.bloggingme.databinding.FragmentDraftBinding;
import com.eflexsoft.bloggingme.room.DraftEntity;
import com.eflexsoft.bloggingme.room.DraftViewModel;

import java.util.List;

public class DraftFragment extends Fragment {

    DraftViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentDraftBinding fragmentDraftBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_draft, container, false);

        viewModel = new ViewModelProvider(getActivity()).get(DraftViewModel.class);

        DraftAdapter adapter = new DraftAdapter();

        View view = fragmentDraftBinding.getRoot();
        
        fragmentDraftBinding.dRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentDraftBinding.dRecycler.setAdapter(adapter);

        viewModel.getAll().observe(getViewLifecycleOwner(), new Observer<List<DraftEntity>>() {
            @Override
            public void onChanged(List<DraftEntity> draftEntities) {
                adapter.submitList(draftEntities);
            }
        });

        return view;
    }
}