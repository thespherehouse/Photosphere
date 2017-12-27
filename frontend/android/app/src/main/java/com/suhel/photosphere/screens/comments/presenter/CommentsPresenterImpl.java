package com.suhel.photosphere.screens.comments.presenter;

import android.support.annotation.NonNull;

import com.suhel.photosphere.model.response.Comment;
import com.suhel.photosphere.screens.comments.contract.CommentsContract;
import com.suhel.photosphere.service.rest.ApiSubscriber;
import com.suhel.photosphere.service.rest.RestService;
import com.suhel.photosphere.service.storage.Store;

import java.util.List;

public class CommentsPresenterImpl extends CommentsPresenter {

    public CommentsPresenterImpl(@NonNull CommentsContract.View view, RestService restService, Store store) {
        super(view, restService, store);
    }

    @Override
    public void getAllComments(String postId) {
        restService.getAllComments(postId, 0, 50, new ApiSubscriber.Callback<List<Comment>>() {

            @Override
            public void onSuccess(List<Comment> data) {
                if (data == null || data.isEmpty())
                    view.onNoData();
                else
                    view.onShowComments(data);
            }

        });
    }

    @Override
    public void createComment(String postId, String comment) {
        restService.createComment(postId, comment, new ApiSubscriber.Callback<Comment>() {

            @Override
            public void onStart() {
                view.onBusy(true);
            }

            @Override
            public void onSuccess(Comment data) {
                if (data == null)
                    view.onCreateCommentFailure();
                else
                    view.onCreateCommentSuccess(data);
            }

            @Override
            public void onEnd() {
                view.onBusy(false);
            }

        });
    }

}
