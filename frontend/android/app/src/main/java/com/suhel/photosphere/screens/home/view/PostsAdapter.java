package com.suhel.photosphere.screens.home.view;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suhel.photosphere.R;
import com.suhel.photosphere.custom.view.SimpleProgressBarDrawable;
import com.suhel.photosphere.databinding.ItemPostBinding;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.utils.common.Constants;
import com.suhel.photosphere.utils.common.DateUtils;
import com.suhel.photosphere.utils.common.LanguageUtils;
import com.suhel.photosphere.utils.common.S3Utils;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private List<Post> posts;
    private OnClickListener onClickListener;

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public Post getItem(int position) {
        return posts.get(position);
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOwnLike(Post post) {
        int index = posts.indexOf(post);
        if (index != -1 && !posts.get(index).isLikedByMe()) {
            posts.get(index).setLikedByMe(true);
            posts.get(index).setLikesCount(posts.get(index).getLikesCount() + 1);
            notifyItemChanged(index);
        }
    }

    public void removeOwnLike(Post post) {
        int index = posts.indexOf(post);
        if (index != -1 && posts.get(index).isLikedByMe()) {
            posts.get(index).setLikedByMe(false);
            posts.get(index).setLikesCount(posts.get(index).getLikesCount() - 1);
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

        void onOwnerClick(PostViewHolder viewHolder, int position);

        void onLikeClick(PostViewHolder viewHolder, int position);

        void onUnlikeClick(PostViewHolder viewHolder, int position);

        void onCommentClick(PostViewHolder viewHolder, int position);

        void onPostClick(PostViewHolder viewHolder, int position);

    }

    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemPostBinding binding;

        PostViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
            binding.btnOwner.setOnClickListener(this);
            binding.btnLike.setOnClickListener(this);
            binding.btnComment.setOnClickListener(this);
            binding.image.getHierarchy().setProgressBarImage(new SimpleProgressBarDrawable(Color.BLACK, 40, 3));
        }

        void bind(Post post) {
            binding.image.setImageURI(S3Utils.getImageUrl(post.getUrl(), S3Utils.ImageType.Thumbnail));
            float aspectRatio = post.getAspectRatio();
            if (aspectRatio > Constants.UI.maximumAspectRatio) {
                // Too wide

                aspectRatio = Constants.UI.maximumAspectRatio;
            } else if (aspectRatio < Constants.UI.minimumAspectRatio) {
                // Too tall

                aspectRatio = Constants.UI.minimumAspectRatio;
            }

            binding.image.setAspectRatio(aspectRatio);
            binding.tvTitle.setText(post.getTitle());
            binding.tvTime.setText(DateUtils.formatSimple(post.getCreatedAt()));
            binding.tvDescription.setText(post.getDescription());
            binding.btnOwner.setText(post.getOwnerName());

            binding.tvCommentsCount.setText(String.valueOf(post.getCommentsCount()));
            binding.tvCommentLabel.setText(LanguageUtils.plural("comment", post.getCommentsCount()));
            binding.icIsCommented.setVisibility(post.isCommentedByMe() ? View.VISIBLE : View.GONE);

            binding.tvLikesCount.setText(String.valueOf(post.getLikesCount()));
            binding.tvLikeLabel.setText(LanguageUtils.plural("like", post.getLikesCount()));
            binding.icIsLiked.setVisibility(post.isLikedByMe() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.btnOwner:

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
