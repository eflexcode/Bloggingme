package com.eflexsoft.bloggingme.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eflexsoft.bloggingme.databinding.DraftItemBinding;
import com.eflexsoft.bloggingme.databinding.PostItemBinding;

public class DraftViewHolder extends RecyclerView.ViewHolder {

   public DraftItemBinding draftItemBinding;

    public DraftViewHolder(@NonNull DraftItemBinding draftItemBinding) {
        super(draftItemBinding.getRoot());
        this.draftItemBinding = draftItemBinding;
    }
}
