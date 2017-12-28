package com.suhel.photosphere.screens.home.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SimpleItemAnimator;

import com.suhel.photosphere.R;
import com.suhel.photosphere.application.contract.AppContract;
import com.suhel.photosphere.base.view.BaseActivity;
import com.suhel.photosphere.databinding.ActivityHomeBinding;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.screens.comments.view.CommentsActivity;
import com.suhel.photosphere.screens.home.contract.HomeContract;
import com.suhel.photosphere.screens.home.di.HomeComponent;
import com.suhel.photosphere.screens.home.di.HomeModule;
import com.suhel.photosphere.screens.home.presenter.HomePresenter;
import com.suhel.photosphere.screens.login.view.LoginActivity;
import com.suhel.photosphere.utils.common.Constants;

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
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this, viewHolder.binding.image, getString(R.string.transition_name_photo));
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

}
