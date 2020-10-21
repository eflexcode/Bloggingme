package com.eflexsoft.bloggingme.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eflexsoft.bloggingme.databinding.PostItemBinding;

public class PostItemViewHolder extends RecyclerView.ViewHolder {

   public PostItemBinding postItemBinding;

    public PostItemViewHolder(@NonNull PostItemBinding postItemBinding) {
        super(postItemBinding.getRoot());
        this.postItemBinding = postItemBinding;
    }
}
