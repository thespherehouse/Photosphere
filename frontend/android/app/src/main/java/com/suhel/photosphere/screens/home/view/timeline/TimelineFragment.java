package com.suhel.photosphere.screens.home.view.timeline;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SimpleItemAnimator;

import com.suhel.photosphere.R;
import com.suhel.photosphere.base.view.BaseFragment;
import com.suhel.photosphere.databinding.FragmentTimelineBinding;
import com.suhel.photosphere.model.realtime.RealtimeComment;
import com.suhel.photosphere.model.realtime.RealtimeLike;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.screens.comments.view.CommentsActivity;
import com.suhel.photosphere.screens.editPost.view.EditPostActivity;
import com.suhel.photosphere.screens.home.contract.TimelineContract;
import com.suhel.photosphere.screens.home.di.timeline.TimelineComponent;
import com.suhel.photosphere.screens.home.di.timeline.TimelineModule;
import com.suhel.photosphere.screens.home.presenter.timeline.TimelinePresenter;
import com.suhel.photosphere.screens.home.view.HomeActivity;
import com.suhel.photosphere.screens.home.view.upload.DeletePostDialog;
import com.suhel.photosphere.utils.Constants;

import java.util.List;

public class TimelineFragment extends BaseFragment<HomeActivity, FragmentTimelineBinding, TimelinePresenter, TimelineComponent> implements TimelineContract.View {

    private DeletePostDialog deletePostDialog = new DeletePostDialog();
    private TimelineAdapter timelineAdapter = new TimelineAdapter();

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_timeline;
    }

    @Override
    protected TimelineComponent createComponent(HomeActivity activity) {
        return activity.getComponent().timelineComponentBuilder().addModule(new TimelineModule(this)).build();
    }

    @Override
    protected void inject(TimelineComponent component) {
        component.inject(this);
    }

    @Override
    protected void onCreated() {
        presenter.attachListeners();
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        ((SimpleItemAnimator) binding.list.getItemAnimator()).setSupportsChangeAnimations(false);
        binding.list.setAdapter(timelineAdapter);
        timelineAdapter.setOnClickListener(new TimelineAdapter.OnClickListener() {

            @Override
            public void onEditClick(TimelineAdapter.TimelineViewHolder viewHolder, int position) {
                Intent intent = new Intent(getContext(), EditPostActivity.class);
                intent.putExtra(Constants.IntentKey.Post, timelineAdapter.getItem(viewHolder.getAdapterPosition()));
                startActivity(intent);
            }

            @Override
            public void onShareClick(TimelineAdapter.TimelineViewHolder viewHolder, int position) {

            }

            @Override
            public void onDeleteClick(TimelineAdapter.TimelineViewHolder viewHolder, int position) {
                deletePostDialog.setListener(() -> presenter.delete(timelineAdapter.getItem(viewHolder.getAdapterPosition())));
                deletePostDialog.show(getChildFragmentManager(), "delete");
            }

            @Override
            public void onOwnerClick(TimelineAdapter.TimelineViewHolder viewHolder, int position) {

            }

            @Override
            public void onLikeClick(TimelineAdapter.TimelineViewHolder viewHolder, int position) {
                Post post = timelineAdapter.getItem(viewHolder.getAdapterPosition());
                timelineAdapter.setOwnLike(post);
                presenter.like(post);
            }

            @Override
            public void onUnlikeClick(TimelineAdapter.TimelineViewHolder viewHolder, int position) {
                Post post = timelineAdapter.getItem(viewHolder.getAdapterPosition());
                timelineAdapter.removeOwnLike(post);
                presenter.unlike(post);
            }

            @Override
            public void onCommentClick(TimelineAdapter.TimelineViewHolder viewHolder, int position) {
                Intent intent = new Intent(getContext(), CommentsActivity.class);
                intent.putExtra(Constants.IntentKey.Post, timelineAdapter.getItem(viewHolder.getAdapterPosition()));
                startActivity(intent);
            }

            @Override
            public void onPostClick(TimelineAdapter.TimelineViewHolder viewHolder, int position) {

            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getPosts();
    }

    @Override
    public void onGetInitialPosts(List<Post> posts) {
        timelineAdapter.setPosts(posts);
    }

    @Override
    public void onGetFurtherPosts(List<Post> posts) {
        timelineAdapter.addPosts(posts);
    }

    @Override
    public void onLikeFailure(@NonNull Post post) {
        timelineAdapter.removeOwnLike(post);
    }

    @Override
    public void onUnlikeFailure(@NonNull Post post) {
        timelineAdapter.setOwnLike(post);
    }

    @Override
    public void onDeleteSuccess(@NonNull Post post) {
        timelineAdapter.remove(post);
    }

    @Override
    public void onDeleteFailure(@NonNull Post post) {

    }

    @Override
    public void onShowBusy() {

    }

    @Override
    public void onHideBusy() {

    }

    @Override
    public void onRealtimeLike(RealtimeLike realtimeLike) {
        timelineAdapter.addRealtimeLike(realtimeLike);
    }

    @Override
    public void onRealtimeUnlike(RealtimeLike realtimeLike) {
        timelineAdapter.removeRealtimeLike(realtimeLike);
    }

    @Override
    public void onRealtimeCreateComment(RealtimeComment realtimeComment) {
        timelineAdapter.addRealtimeComment(realtimeComment);
    }

    @Override
    public void onRealtimeEditComment(RealtimeComment realtimeComment) {

    }

    @Override
    public void onRealtimeDeleteComment(RealtimeComment realtimeComment) {
        timelineAdapter.removeRealtimeComment(realtimeComment);
    }

}
