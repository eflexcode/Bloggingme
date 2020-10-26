package com.eflexsoft.bloggingme.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eflexsoft.bloggingme.databinding.CommetItemBinding;
import com.eflexsoft.bloggingme.databinding.PostItemBinding;

public class ComentItemViewHolder extends RecyclerView.ViewHolder {

   public CommetItemBinding commetItemBinding;

    public ComentItemViewHolder(@NonNull CommetItemBinding CommetItemBinding) {
        super(CommetItemBinding.getRoot());
        this.commetItemBinding = CommetItemBinding;
    }
}
