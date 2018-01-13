package com.suhel.photosphere.service.rest;

import android.content.Context;

import com.suhel.photosphere.base.model.ProgressRequestBody;
import com.suhel.photosphere.model.request.CreateComment;
import com.suhel.photosphere.model.request.EditPost;
import com.suhel.photosphere.model.request.FCM;
import com.suhel.photosphere.model.request.RegisterSocial;
import com.suhel.photosphere.model.response.Comment;
import com.suhel.photosphere.model.response.Group;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.model.response.User;
import com.suhel.photosphere.service.rx.RxHelper;
import com.suhel.photosphere.utils.Constants;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
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
                               ApiSubscriber.Callback<User> callback) {
        restInterface.socialLogin(new RegisterSocial(name, email, socialId, loginType))
                .compose(RxHelper.applySchedulers())
                .subscribeWith(new ApiSubscriber<>(callback));
    }

    public void silentLogin(ApiSubscriber.Callback<Void> callback) {
        restInterface.silentLogin()
                .compose(RxHelper.applySchedulers())
                .subscribeWith(new ApiSubscriber<>(callback));
    }

    public void fcm(String fcm, ApiSubscriber.Callback<Void> callback) {
        restInterface.fcm(new FCM(fcm))
                .compose(RxHelper.applySchedulers())
                .subscribeWith(new ApiSubscriber<>(callback));
    }
    //endregion

    //region Posts
    public void createPost(String title, String description, File photo,
                           ProgressRequestBody.OnProgressUpdateListener progressUpdateListener,
                           ApiSubscriber.Callback<Post> callback) {
        restInterface.createPost(MultipartBody.Part.createFormData("photo", "photo", new ProgressRequestBody(photo, progressUpdateListener)),
                RequestBody.create(MediaType.parse("text/plain"), title),
                RequestBody.create(MediaType.parse("text/plain"), description))
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

    public void editPost(String postId, String title, String description, ApiSubscriber.Callback<Post> callback) {
        restInterface.editPost(postId, new EditPost(title, description))
                .compose(RxHelper.applySchedulers())
                .subscribeWith(new ApiSubscriber<>(callback));
    }

    public void deletePost(String postId, ApiSubscriber.Callback<Void> callback) {
        restInterface.deletePost(postId)
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

    //region Chat
//    public void sendGlobalChatMessage(String message, ApiSubscriber.Callback<Void> callback) {
//        restInterface.sendChatMessage(new SendChatMessage(message))
//                .compose(RxHelper.applySchedulers())
//                .subscribeWith(new ApiSubscriber<>(callback));
//    }
//
//    public void getGlobalChatMessages(ApiSubscriber.Callback<List<Message>> callback) {
//        restInterface.getGlobalChatMessages()
//                .compose(RxHelper.applySchedulers())
//                .subscribeWith(new ApiSubscriber<>(callback));
//    }

    public void getGroups(int skip, int limit, ApiSubscriber.Callback<List<Group>> callback) {
        restInterface.getGroups(skip, limit)
                .compose(RxHelper.applySchedulers())
                .subscribeWith(new ApiSubscriber<>(callback));
    }
    //endregion

}
