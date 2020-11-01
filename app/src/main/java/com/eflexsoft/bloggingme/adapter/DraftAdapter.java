package com.eflexsoft.bloggingme.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.eflexsoft.bloggingme.databinding.DraftItemBinding;
import com.eflexsoft.bloggingme.databinding.PostItemBinding;
import com.eflexsoft.bloggingme.room.DraftEntity;
import com.eflexsoft.bloggingme.viewholder.DraftViewHolder;
import com.eflexsoft.bloggingme.viewholder.PostItemViewHolder;

public class DraftAdapter extends ListAdapter<DraftEntity, DraftViewHolder> {

    public DraftAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public DraftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        DraftItemBinding draftItemBinding = DraftItemBinding.inflate(layoutInflater, parent, false);

        return new DraftViewHolder(draftItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DraftViewHolder holder, int position) {

        DraftEntity draftEntity = getItem(position);

        if (draftEntity !=null){
            holder.draftItemBinding.setDraft(draftEntity);
        }

}

    static DiffUtil.ItemCallback<DraftEntity> diffCallback = new DiffUtil.ItemCallback<DraftEntity>() {

        @Override
        public boolean areItemsTheSame(@NonNull DraftEntity oldItem, @NonNull DraftEntity newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull DraftEntity oldItem, @NonNull DraftEntity newItem) {
            return oldItem.getId() == newItem.getId() && oldItem.getDraftBody().equals(newItem) && oldItem.getDraftBody().equals(newItem);
        }

    };

}
