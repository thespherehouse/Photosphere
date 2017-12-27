package com.suhel.photosphere.service.rest;

import android.content.Context;

import com.suhel.photosphere.base.model.ProgressRequestBody;
import com.suhel.photosphere.model.request.CreateComment;
import com.suhel.photosphere.model.request.CreatePost;
import com.suhel.photosphere.model.request.RegisterSocial;
import com.suhel.photosphere.model.response.Comment;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.service.rx.RxHelper;
import com.suhel.photosphere.utils.common.Constants;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestService {

    private RestInterface restInterface;

    //region Constructor
    public RestService(Context context) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new DeviceInterceptor(context))
                .addInterceptor(new TokenInterceptor(context))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        restInterface = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.Network.REST_URL)
                .build()
                .create(RestInterface.class);

    }
    //endregion

    //region Auth
    public void registerSocial(String name, String email, String socialId, int loginType,
                               ApiSubscriber.Callback<Void> callback) {
        restInterface.socialLogin(new RegisterSocial(name, email, socialId, loginType))
                .compose(RxHelper.applySchedulers())
                .subscribeWith(new ApiSubscriber<>(callback));
    }

    public void silentLogin(ApiSubscriber.Callback<Void> callback) {
        restInterface.silentLogin()
                .compose(RxHelper.applySchedulers())
                .subscribeWith(new ApiSubscriber<>(callback));
    }
    //endregion

    //region Posts
    public void createPost(String title, String description, File photo,
                           ProgressRequestBody.OnProgressUpdateListener progressUpdateListener,
                           ApiSubscriber.Callback<Void> callback) {
        restInterface.createPost(new ProgressRequestBody(photo, progressUpdateListener),
                new CreatePost(title, description))
                .compose(RxHelper.applySchedulers())
                .subscribeWith(new ApiSubscriber<>(callback));
    }

    public void getAllPosts(int skip, int limit, ApiSubscriber.Callback<List<Post>> callback) {
        restInterface.getAllPosts(skip, limit)
                .compose(RxHelper.applySchedulers())
                .subscribeWith(new ApiSubscriber<>(callback));
    }

    public void likePost(String postId, ApiSubscriber.Callback<Void> callback) {
        restInterface.likePost(postId)
                .compose(RxHelper.applySchedulers())
                .subscribeWith(new ApiSubscriber<>(callback));
    }

    public void unlikePost(String postId, ApiSubscriber.Callback<Void> callback) {
        restInterface.unlikePost(postId)
                .compose(RxHelper.applySchedulers())
                .subscribeWith(new ApiSubscriber<>(callback));
    }
    //endregion

    //region Comments
    public void createComment(String postId, String comment, ApiSubscriber.Callback<Comment> callback) {
        restInterface.createComment(postId, new CreateComment(comment))
                .compose(RxHelper.applySchedulers())
                .subscribeWith(new ApiSubscriber<>(callback));
    }

    public void getAllComments(String postId, int skip, int limit, ApiSubscriber.Callback<List<Comment>> callback) {
        restInterface.getAllComments(postId, skip, limit)
                .compose(RxHelper.applySchedulers())
                .subscribeWith(new ApiSubscriber<>(callback));
    }
    //endregion


}
