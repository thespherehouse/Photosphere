package com.suhel.photosphere.screens.home.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SimpleItemAnimator;

import com.suhel.photosphere.R;
import com.suhel.photosphere.application.contract.AppContract;
import com.suhel.photosphere.base.view.BaseActivity;
import com.suhel.photosphere.databinding.ActivityHomeBinding;
import com.suhel.photosphere.model.realtime.RealtimeComment;
import com.suhel.photosphere.model.realtime.RealtimeLike;
import com.suhel.photosphere.model.response.Comment;
import com.suhel.photosphere.model.response.Like;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.screens.comments.view.CommentsActivity;
import com.suhel.photosphere.screens.home.contract.HomeContract;
import com.suhel.photosphere.screens.home.di.HomeComponent;
import com.suhel.photosphere.screens.home.di.HomeModule;
import com.suhel.photosphere.screens.home.presenter.HomePresenter;
import com.suhel.photosphere.screens.login.view.LoginActivity;
import com.suhel.photosphere.utils.Constants;

import java.util.List;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomePresenter, HomeComponent> implements HomeContract.View {

    private PostsAdapter adapter = new PostsAdapter();

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected HomeComponent getComponent(AppContract contract) {
        return contract.getTimelineComponent().addModule(new HomeModule(this)).build();
    }

    @Override
    protected void inject(HomeComponent component) {
        component.inject(this);
    }

    @Override
    protected void onPreCreate() {
        presenter.checkLoginStatus();
        presenter.updateFirebaseToken();
    }

    @Override
    protected void onActivityCreated() {
        binding.timeline.list.setLayoutManager(new LinearLayoutManager(this));
        binding.timeline.list.setAdapter(adapter);
        ((SimpleItemAnimator) binding.timeline.list.getItemAnimator()).setSupportsChangeAnimations(false);
        adapter.setOnClickListener(new PostsAdapter.OnClickListener() {

            @Override
            public void onOwnerClick(PostsAdapter.PostViewHolder viewHolder, int position) {

            }

            @Override
            public void onLikeClick(PostsAdapter.PostViewHolder viewHolder, int position) {
                Post post = adapter.getItem(position);
                presenter.likePost(post);
                adapter.setOwnLike(post);
            }

            @Override
            public void onUnlikeClick(PostsAdapter.PostViewHolder viewHolder, int position) {
                Post post = adapter.getItem(position);
                presenter.unlikePost(post);
                adapter.removeOwnLike(post);
            }

            @Override
            public void onCommentClick(PostsAdapter.PostViewHolder viewHolder, int position) {
                Post post = adapter.getItem(position);
                Intent commentActivityIntent = new Intent(HomeActivity.this, CommentsActivity.class);
                commentActivityIntent.putExtra(Constants.Intent.Post, post);
                startActivity(commentActivityIntent);
            }

            @Override
            public void onPostClick(PostsAdapter.PostViewHolder viewHolder, int position) {

            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.addSocketListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.removeSocketListeners();
    }

    @Override
    public void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onNewData(List<Post> posts) {
        adapter.setPosts(posts);
    }

    @Override
    public void onFailLikePost(Post post) {
        adapter.removeOwnLike(post);
    }

    @Override
    public void onFailUnlikePost(Post post) {
        adapter.setOwnLike(post);
    }

    @Override
    public void onRealtimeLike(RealtimeLike like) {
        adapter.addLike(like);
    }

    @Override
    public void onRealtimeUnlike(RealtimeLike like) {
        adapter.removeLike(like);
    }

    @Override
    public void onRealtimeAddComment(RealtimeComment comment) {
        adapter.addComment(comment);
    }

    @Override
    public void onRealtimeDeleteComment(RealtimeComment comment) {
        adapter.removeComment(comment);
    }

}
