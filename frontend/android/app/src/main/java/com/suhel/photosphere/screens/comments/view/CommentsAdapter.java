package com.suhel.photosphere.screens.comments.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.suhel.photosphere.databinding.ItemCommentBinding;
import com.suhel.photosphere.model.response.Comment;
import com.suhel.photosphere.utils.common.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private List<Comment> comments;

    public void setComments(List<Comment> data) {
        this.comments = data;
        notifyDataSetChanged();
    }

    public void addComment(Comment comment) {
        if (this.comments == null)
            this.comments = new ArrayList<>();
        this.comments.add(0, comment);
        notifyItemInserted(this.comments.size() - 1);
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentsViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        holder.bind(getComment(position));
    }

    private Comment getComment(int position) {
        return comments != null ? comments.get(comments.size() - position - 1) : null;
    }

    @Override
    public int getItemCount() {
        return comments != null ? comments.size() : 0;
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder {

        ItemCommentBinding binding;

        public CommentsViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Comment data) {
            binding.tvOwnerName.setText(data.getUser().getName());
            binding.tvComment.setText(data.getComment());
            binding.tvDate.setText(DateUtils.formatSimple(data.getUpdatedAt()));
        }

    }

}
