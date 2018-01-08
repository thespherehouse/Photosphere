package com.suhel.photosphere.screens.home.view.timeline;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suhel.photosphere.R;
import com.suhel.photosphere.custom.view.SimpleProgressBarDrawable;
import com.suhel.photosphere.databinding.ItemPostBinding;
import com.suhel.photosphere.model.realtime.RealtimeComment;
import com.suhel.photosphere.model.realtime.RealtimeLike;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.utils.Constants;
import com.suhel.photosphere.utils.DateUtils;
import com.suhel.photosphere.utils.LanguageUtils;
import com.suhel.photosphere.utils.S3Utils;

import java.util.ArrayList;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {

    private List<Post> posts;
    private OnClickListener onClickListener;

    @Override
    public TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TimelineViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(TimelineViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public Post getItem(int position) {
        return posts.get(position);
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public void addPosts(@NonNull List<Post> posts) {
        if (this.posts == null)
            this.posts = new ArrayList<>();
        int initialSize = this.posts.size();
        this.posts.addAll(posts);
        notifyItemRangeInserted(initialSize, posts.size());
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void addRealtimeLike(RealtimeLike like) {
        int position = -1;
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId().equals(like.getPostId()))
                position = i;
        }
        if (position != -1) {
            posts.get(position).setLikesCount(posts.get(position).getLikesCount() + 1);
            notifyItemChanged(position);
        }
    }

    public void removeRealtimeLike(RealtimeLike like) {
        int position = -1;
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId().equals(like.getPostId()))
                position = i;
        }
        if (position != -1) {
            posts.get(position).setLikesCount(posts.get(position).getLikesCount() - 1);
            notifyItemChanged(position);
        }
    }

    public void addRealtimeComment(RealtimeComment comment) {
        int position = -1;
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId().equals(comment.getPostId()))
                position = i;
        }
        if (position != -1) {
            posts.get(position).setCommentsCount(posts.get(position).getCommentsCount() + 1);
            notifyItemChanged(position);
        }
    }

    public void removeRealtimeComment(RealtimeComment comment) {
        int position = -1;
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId().equals(comment.getPostId()))
                position = i;
        }
        if (position != -1) {
            posts.get(position).setCommentsCount(posts.get(position).getCommentsCount() - 1);
            notifyItemChanged(position);
        }
    }

    public void setOwnLike(Post post) {
        int index = posts.indexOf(post);
        if (index != -1 && !posts.get(index).isLikedByMe()) {
            posts.get(index).setLikedByMe(true);
//            posts.get(index).setLikesCount(posts.get(index).getLikesCount() + 1);
            notifyItemChanged(index);
        }
    }

    public void removeOwnLike(Post post) {
        int index = posts.indexOf(post);
        if (index != -1 && posts.get(index).isLikedByMe()) {
            posts.get(index).setLikedByMe(false);
//            posts.get(index).setLikesCount(posts.get(index).getLikesCount() - 1);
            notifyItemChanged(index);
        }
    }

    public void setOwnComment(Post post) {
        int index = posts.indexOf(post);
        if (index != -1 && !posts.get(index).isCommentedByMe()) {
            posts.get(index).setCommentedByMe(true);
            posts.get(index).setCommentsCount(posts.get(index).getCommentsCount() - 1);
            notifyItemChanged(index);
        }
    }

    public void removeOwnComment(Post post) {
        int index = posts.indexOf(post);
        if (index != -1 && posts.get(index).isCommentedByMe()) {
            posts.get(index).setCommentedByMe(false);
            posts.get(index).setCommentsCount(posts.get(index).getCommentsCount() - 1);
            notifyItemChanged(index);
        }
    }

    @Override
    public int getItemCount() {
        return (posts != null ? posts.size() : 0);
    }

    public interface OnClickListener {

        void onOwnerClick(TimelineViewHolder viewHolder, int position);

        void onLikeClick(TimelineViewHolder viewHolder, int position);

        void onUnlikeClick(TimelineViewHolder viewHolder, int position);

        void onCommentClick(TimelineViewHolder viewHolder, int position);

        void onPostClick(TimelineViewHolder viewHolder, int position);

    }

    class TimelineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemPostBinding binding;

        TimelineViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
            binding.tvOwnerName.setOnClickListener(this);
            binding.btnLike.setOnClickListener(this);
            binding.btnComment.setOnClickListener(this);
            binding.photo.getHierarchy().setProgressBarImage(new SimpleProgressBarDrawable(Color.BLACK, 40, 3));
        }

        void bind(Post post) {
            binding.photo.setImageURI(S3Utils.getImageUrl(post.getUrl(), S3Utils.ImageType.Thumbnail));
            float aspectRatio = post.getAspectRatio();
            if (aspectRatio > Constants.UI.maximumAspectRatio) {
                // Too wide

                aspectRatio = Constants.UI.maximumAspectRatio;
            } else if (aspectRatio < Constants.UI.minimumAspectRatio) {
                // Too tall

                aspectRatio = Constants.UI.minimumAspectRatio;
            }

            binding.photo.setAspectRatio(aspectRatio);
            binding.tvTitle.setText(post.getTitle());
            binding.tvDateTime.setText(DateUtils.formatSimple(post.getCreatedAt()));
            binding.tvDescription.setText(post.getDescription());
            binding.tvOwnerName.setText(post.getOwnerName());

            binding.tvCommentsCount.setText(String.valueOf(post.getCommentsCount()));
            binding.tvCommentsLabel.setText(LanguageUtils.plural("comment", post.getCommentsCount()));
            binding.btnComment.setColorFilterResource(post.isCommentedByMe() ? R.color.colorBlue : R.color.colorMedium);

            binding.tvLikesCount.setText(String.valueOf(post.getLikesCount()));
            binding.tvLikesLabel.setText(LanguageUtils.plural("like", post.getLikesCount()));
            binding.btnLike.setColorFilterResource(post.isLikedByMe() ? R.color.colorRed : R.color.colorMedium);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.tvOwnerName:

                    if (onClickListener != null)
                        onClickListener.onOwnerClick(this, getAdapterPosition());
                    break;

                case R.id.btnLike:

                    if (onClickListener != null) {
                        if (posts.get(getAdapterPosition()).isLikedByMe())
                            onClickListener.onUnlikeClick(this, getAdapterPosition());
                        else
                            onClickListener.onLikeClick(this, getAdapterPosition());
                    }
                    break;

                case R.id.btnComment:

                    if (onClickListener != null)
                        onClickListener.onCommentClick(this, getAdapterPosition());
                    break;

                default:

                    if (onClickListener != null)
                        onClickListener.onPostClick(this, getAdapterPosition());
                    break;

            }
        }

    }

}
