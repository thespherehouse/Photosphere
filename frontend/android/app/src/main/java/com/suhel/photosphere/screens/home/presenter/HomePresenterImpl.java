package com.suhel.photosphere.screens.home.presenter;

import android.support.annotation.NonNull;

import com.google.firebase.iid.FirebaseInstanceId;
import com.suhel.photosphere.base.model.ApiError;
import com.suhel.photosphere.model.realtime.RealtimeComment;
import com.suhel.photosphere.model.realtime.RealtimeLike;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.screens.home.contract.HomeContract;
import com.suhel.photosphere.service.realtime.WS;
import com.suhel.photosphere.service.realtime.WSCommentsListener;
import com.suhel.photosphere.service.realtime.WSLikesListener;
import com.suhel.photosphere.service.rest.ApiSubscriber;
import com.suhel.photosphere.service.rest.RestService;
import com.suhel.photosphere.service.storage.Store;

import java.util.List;

public class HomePresenterImpl extends HomePresenter {

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
            view.onRealtimeAddComment(data);
        }

        @Override
        public void onEditComment(RealtimeComment data) {

        }

        @Override
        public void onDeleteComment(RealtimeComment data) {
            view.onRealtimeDeleteComment(data);
        }

    };

    public HomePresenterImpl(@NonNull HomeContract.View view, RestService restService, Store store, WS ws) {
        super(view, restService, store, ws);
    }

    @Override
    public void addSocketListeners() {
        ws.addListener(likesListener);
        ws.addListener(commentsListener);
    }

    @Override
    public void removeSocketListeners() {
        ws.removeListener(likesListener);
        ws.removeListener(commentsListener);
    }

    @Override
    public void checkLoginStatus() {
        String token = store.get(Store.TOKEN);
        if (token == null || token.isEmpty()) {
            view.redirectToLogin();
            return;
        }
        restService.silentLogin(new ApiSubscriber.Callback<Void>() {

            @Override
            public void onSuccess(Void data) {
                getAllPosts();
            }

            @Override
            public void onError(ApiError apiError) {
                view.redirectToLogin();
            }

        });
    }

    @Override
    public void updateFirebaseToken() {
        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        if (fcmToken != null) {
            restService.fcm(fcmToken, new ApiSubscriber.Callback<Void>() {
            });
        }
    }

    @Override
    public void getAllPosts() {
        restService.getAllPosts(0, 50, new ApiSubscriber.Callback<List<Post>>() {

            @Override
            public void onSuccess(List<Post> data) {
                view.onNewData(data);
            }

        });
    }

    @Override
    public void likePost(Post post) {
        restService.likePost(post.getId(), new ApiSubscriber.Callback<Void>() {

            @Override
            public void onError(ApiError apiError) {
                view.onFailLikePost(post);
            }

        });
    }

    @Override
    public void unlikePost(Post post) {
        restService.unlikePost(post.getId(), new ApiSubscriber.Callback<Void>() {

            @Override
            public void onError(ApiError apiError) {
                view.onFailUnlikePost(post);
            }

        });
    }

}
