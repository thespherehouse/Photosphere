package com.suhel.photosphere.screens.home.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.suhel.photosphere.base.model.ApiError;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.screens.home.contract.HomeContract;
import com.suhel.photosphere.service.realtime.SocketIO;
import com.suhel.photosphere.service.realtime.SocketIOClient;
import com.suhel.photosphere.service.rest.ApiSubscriber;
import com.suhel.photosphere.service.rest.RestService;
import com.suhel.photosphere.service.storage.Store;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HomePresenterImpl extends HomePresenter {

    public HomePresenterImpl(@NonNull HomeContract.View view, RestService restService, Store store, SocketIO socketIO) {
        super(view, restService, store, socketIO);
    }

    @Override
    public void connectToSocket() {
        socketIO.connect();
        socketIO.add(new SocketIOClient() {

            @Override
            public void onConnect() {
                Log.e("Likes", "Likes connected");
            }

            @Override
            public void onDisconnect() {

            }

            @Override
            public void onReceive(Domain domain, Event event, JSONObject data) {
                Log.e("Socket.IO Domain", domain.toString());
                Log.e("Socket.IO Event", event.toString());
                try {
                    Log.e("Socket.IO Data", data.toString(2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
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
