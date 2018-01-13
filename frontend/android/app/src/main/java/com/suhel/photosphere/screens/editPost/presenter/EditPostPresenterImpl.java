package com.suhel.photosphere.screens.editPost.presenter;

import android.support.annotation.NonNull;

import com.suhel.photosphere.base.model.ApiError;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.screens.editPost.contract.EditPostContract;
import com.suhel.photosphere.service.rest.ApiSubscriber;
import com.suhel.photosphere.service.rest.RestService;

public class EditPostPresenterImpl extends EditPostPresenter {

    private Post post;

    public EditPostPresenterImpl(@NonNull EditPostContract.View view, RestService restService) {
        super(view, restService);
    }

    @Override
    public void setPost(Post post) {
        this.post = post;
        view.onUpdateUI(post);
    }

    @Override
    public void edit(String title, String description) {
        restService.editPost(post.getId(), title, description, new ApiSubscriber.Callback<Post>() {

            @Override
            public void onStart() {
                super.onStart();
                view.onShowBusy();
            }

            @Override
            public void onSuccess(Post data) {
                super.onSuccess(data);
                post.setTitle(data.getTitle());
                post.setDescription(data.getDescription());
                view.onEditSuccess(post);
            }

            @Override
            public void onApiError(ApiError apiError) {
                super.onApiError(apiError);
                view.onEditFailed();
            }

            @Override
            public void onEnd() {
                super.onEnd();
                view.onHideBusy();
            }

        });
    }

}
