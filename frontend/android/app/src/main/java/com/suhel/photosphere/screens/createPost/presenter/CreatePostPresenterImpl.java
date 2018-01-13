package com.suhel.photosphere.screens.createPost.presenter;

import android.support.annotation.NonNull;

import com.suhel.photosphere.base.model.ApiError;
import com.suhel.photosphere.model.realtime.RealtimeComment;
import com.suhel.photosphere.model.response.Comment;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.screens.createPost.contract.CreatePostContract;
import com.suhel.photosphere.service.realtime.WS;
import com.suhel.photosphere.service.realtime.WSCommentsListener;
import com.suhel.photosphere.service.rest.ApiSubscriber;
import com.suhel.photosphere.service.rest.RestService;
import com.suhel.photosphere.service.storage.Store;

import java.io.File;
import java.util.List;

public class CreatePostPresenterImpl extends CreatePostPresenter {

    public CreatePostPresenterImpl(@NonNull CreatePostContract.View view, RestService restService) {
        super(view, restService);
    }

    @Override
    public void upload(String title, String description, File photo) {
        restService.createPost(title, description, photo,
                (transferred, total) -> view.onUploadProgressUpdate((int) (transferred * 100 / total)),
                new ApiSubscriber.Callback<Post>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        view.onShowBusy();
                    }

                    @Override
                    public void onSuccess(Post data) {
                        super.onSuccess(data);
                        view.onUploadSuccess();
                    }

                    @Override
                    public void onApiError(ApiError apiError) {
                        super.onApiError(apiError);
                        view.onUploadFailed();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        view.onUploadFailed();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        view.onHideBusy();
                    }

                });
    }

}
