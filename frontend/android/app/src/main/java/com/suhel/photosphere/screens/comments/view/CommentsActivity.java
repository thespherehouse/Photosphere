package com.suhel.photosphere.screens.comments.view;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import com.suhel.photosphere.R;
import com.suhel.photosphere.application.contract.AppContract;
import com.suhel.photosphere.base.view.BaseActivity;
import com.suhel.photosphere.databinding.ActivityCommentsBinding;
import com.suhel.photosphere.model.realtime.RealtimeComment;
import com.suhel.photosphere.model.response.Comment;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.screens.comments.contract.CommentsContract;
import com.suhel.photosphere.screens.comments.di.CommentsComponent;
import com.suhel.photosphere.screens.comments.di.CommentsModule;
import com.suhel.photosphere.screens.comments.presenter.CommentsPresenter;
import com.suhel.photosphere.utils.Constants;

import java.util.List;

public class CommentsActivity extends BaseActivity<ActivityCommentsBinding, CommentsPresenter, CommentsComponent> implements CommentsContract.View {

    private CommentsAdapter adapter = new CommentsAdapter();
    private Post post;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_comments;
    }

    @Override
    protected CommentsComponent createComponent(AppContract contract) {
        return contract.getCommentsComponent().addModule(new CommentsModule(this)).build();
    }

    @Override
    protected void inject(CommentsComponent component) {
        component.inject(this);
    }

    @Override
    protected void onActivityCreated() {
        if (getIntent() != null && getIntent().hasExtra(Constants.IntentKey.Post))
            post = (Post) getIntent().getSerializableExtra(Constants.IntentKey.Post);
        else
            finish();

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.lstComments.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator) binding.lstComments.getItemAnimator()).setSupportsChangeAnimations(false);
        binding.lstComments.setAdapter(adapter);
        binding.txtComment.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        updateButton();
        binding.btnSend.setOnClickListener(view -> presenter.createComment(post.getId(), binding.txtComment.getText().toString().trim()));
        presenter.getAllComments(post.getId());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.addSocketListeners();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.removeSocketListeners();
    }

    private void updateButton() {
        if (binding.txtComment.getText().toString().trim().isEmpty()) {
            binding.btnSend.setEnabled(false);
            binding.btnSend.setColorFilter(ContextCompat.getColor(CommentsActivity.this, R.color.colorMedium));
        } else {
            binding.btnSend.setEnabled(true);
            binding.btnSend.setColorFilter(ContextCompat.getColor(CommentsActivity.this, R.color.colorAccent));
        }
    }

    @Override
    public void onNoData() {
        binding.placeholder.setVisibility(View.VISIBLE);
        adapter.setComments(null);
    }

    @Override
    public void onShowComments(List<Comment> data) {
        binding.placeholder.setVisibility(View.GONE);
        adapter.setComments(data);
        binding.lstComments.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void onCreateCommentSuccess(Comment comment) {
        binding.txtComment.setText("");
//        adapter.addComment(comment);
        updateButton();
    }

    @Override
    public void onCreateCommentFailure() {
        updateButton();
    }

    @Override
    public void onBusy(boolean isBusy) {
        if (isBusy) {
            binding.btnSend.setVisibility(View.GONE);
            binding.spinner.setVisibility(View.VISIBLE);
            binding.txtComment.setEnabled(false);
        } else {
            binding.btnSend.setVisibility(View.VISIBLE);
            binding.spinner.setVisibility(View.GONE);
            binding.txtComment.setEnabled(true);
        }
    }

    @Override
    public void onRealtimeAddComment(RealtimeComment realtimeComment) {
        if (realtimeComment.getPostId().equals(post.getId())) {
            adapter.addRealtimeComment(realtimeComment);
            binding.lstComments.smoothScrollToPosition(adapter.getItemCount() - 1);
        }
    }

    @Override
    public void onRealtimeEditComment(RealtimeComment realtimeComment) {
        if (realtimeComment.getPostId().equals(post.getId()))
            adapter.editRealtimeComment(realtimeComment);
    }

    @Override
    public void onRealtimeDeleteComment(RealtimeComment realtimeComment) {
        if (realtimeComment.getPostId().equals(post.getId()))
            adapter.deleteRealtimeComment(realtimeComment);
    }

}
