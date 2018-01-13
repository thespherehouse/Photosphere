package com.suhel.photosphere.service.rest;

import com.suhel.photosphere.base.model.ApiResponse;
import com.suhel.photosphere.model.request.CreateComment;
import com.suhel.photosphere.model.request.EditPost;
import com.suhel.photosphere.model.request.FCM;
import com.suhel.photosphere.model.request.RegisterSocial;
import com.suhel.photosphere.model.request.SendChatMessage;
import com.suhel.photosphere.model.response.Comment;
import com.suhel.photosphere.model.response.Group;
import com.suhel.photosphere.model.response.Message;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.model.response.User;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestInterface {

    //region Auth
    @PUT("auth/socialLogin")
    Single<ApiResponse<User>> socialLogin(@Body RegisterSocial registerSocial);

    @PUT("auth/silentLogin")
    Single<ApiResponse<Void>> silentLogin();

    @PUT("auth/fcm")
    Single<ApiResponse<Void>> fcm(@Body FCM fcm);
    //endregion

    //region Posts
    @Multipart
    @POST("posts")
    Single<ApiResponse<Post>> createPost(@Part MultipartBody.Part photo, @Part("title") RequestBody title, @Part("description") RequestBody description);

    @GET("posts")
    Single<ApiResponse<List<Post>>> getAllPosts(@Query("skip") int skip, @Query("limit") int limit);

    @GET("posts/by/me")
    Single<ApiResponse<List<Post>>> getMyPosts(@Query("skip") int skip, @Query("limit") int limit);

    @GET("posts/by/{userId}")
    Single<ApiResponse<List<Post>>> getUsersPosts(@Path("userId") String userId, @Query("skip") int skip, @Query("limit") int limit);

    @PUT("posts/{postId}")
    Single<ApiResponse<Post>> editPost(@Path("postId") String postId, @Body EditPost body);

    @DELETE("posts/{postId}")
    Single<ApiResponse<Void>> deletePost(@Path("postId") String postId);
    //endregion

    //region Comments
    @POST("posts/{postId}/comments")
    Single<ApiResponse<Comment>> createComment(@Path("postId") String postId, @Body CreateComment createComment);

    @GET("posts/{postId}/comments")
    Single<ApiResponse<List<Comment>>> getAllComments(@Path("postId") String postId, @Query("skip") int skip, @Query("limit") int limit);

    @PUT("posts/{postId}/comments/{commentId}")
    Single<ApiResponse<Void>> editComment(@Path("postId") String postId, @Path("commentId") String commentId, @Query("skip") int skip, @Query("limit") int limit);

    @DELETE("posts/{postId}/comments/{commentId}")
    Single<ApiResponse<Void>> deleteComment(@Path("postId") String postId, @Path("commentId") String commentId);
    //endregion

    //region Likes
    @POST("posts/{postId}/like")
    Single<ApiResponse<Void>> likePost(@Path("postId") String postId);

    @DELETE("posts/{postId}/unlike")
    Single<ApiResponse<Void>> unlikePost(@Path("postId") String postId);
    //endregion

    //region Chat
    @POST("chat/{groupId}")
    Single<ApiResponse<Void>> sendChatMessage(@Path("groupId") String groupId, @Body SendChatMessage message);

    @GET("chat")
    Single<ApiResponse<List<Group>>> getGroups(@Query("skip") int skip, @Query("limit") int limit);

    @GET("chat/{groupId}")
    Single<ApiResponse<List<Message>>> getChatMessages(@Path("groupId") String groupId, @Query("skip") int skip, @Query("limit") int limit);
    //endregion

}
