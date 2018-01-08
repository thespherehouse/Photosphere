package com.suhel.photosphere.screens.home.presenter.timeline;

import android.support.annotation.NonNull;

import com.suhel.photosphere.base.model.ApiError;
import com.suhel.photosphere.model.realtime.RealtimeComment;
import com.suhel.photosphere.model.realtime.RealtimeLike;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.screens.home.contract.TimelineContract;
import com.suhel.photosphere.service.realtime.WS;
import com.suhel.photosphere.service.realtime.WSCommentsListener;
import com.suhel.photosphere.service.realtime.WSLikesListener;
import com.suhel.photosphere.service.rest.ApiSubscriber;
import com.suhel.photosphere.service.rest.RestService;

import java.util.List;

public class TimelinePresenterImpl extends TimelinePresenter {

    private WSLikesListener likesListener = new WSLikesListener() {

        @Override
        public void onLike(RealtimeLike data) {
            view.onRealtimeLike(data);
        }

        @Override
        public void onUnlike(RealtimeLike data) {
            view.onRealtimeUnlike(data);
        }

    };

    private WSCommentsListener commentsListener = new WSCommentsListener() {

        @Override
        public void onCreateComment(RealtimeComment data) {
            view.onRealtimeCreateComment(data);
        }

        @Override
        public void onEditComment(RealtimeComment data) {
            view.onRealtimeEditComment(data);
        }

        @Override
        public void onDeleteComment(RealtimeComment data) {
            view.onRealtimeDeleteComment(data);
        }

    };

    public TimelinePresenterImpl(TimelineContract.View view, RestService restService, WS ws) {
        super(view, restService, ws);
    }

    @Override
    public void attachListeners() {
        ws.addListener(likesListener);
        ws.addListener(commentsListener);
    }

    @Override
    public void detachListeners() {
        ws.removeListener(likesListener);
        ws.removeListener(commentsListener);
    }

    @Override
    public void getPosts() {
        restService.getAllPosts(0, 10, new ApiSubscriber.Callback<List<Post>>() {

            @Override
            public void onSuccess(List<Post> data) {
                view.onGetInitialPosts(data);
            }

        });
    }

    @Override
    public void getPosts(int skip, int limit) {
        restService.getAllPosts(skip, limit, new ApiSubscriber.Callback<List<Post>>() {

            @Override
            public void onSuccess(List<Post> data) {
                view.onGetFurtherPosts(data);
            }

        });
    }

    @Override
    public void like(@NonNull Post post) {
        final Post strongPost = post;
        restService.likePost(post.getId(), new ApiSubscriber.Callback<Void>() {

            @Override
            public void onApiError(ApiError apiError) {
                view.onLikeFailure(strongPost);
            }

            @Override
            public void onError(Throwable t) {
                view.onLikeFailure(strongPost);
            }

        });
    }

    @Override
    public void unlike(@NonNull Post post) {
        final Post strongPost = post;
        restService.unlikePost(post.getId(), new ApiSubscriber.Callback<Void>() {

            @Override
            public void onApiError(ApiError apiError) {
                view.onUnlikeFailure(strongPost);
            }

            @Override
            public void onError(Throwable t) {
                view.onUnlikeFailure(strongPost);
            }

        });
    }

}
