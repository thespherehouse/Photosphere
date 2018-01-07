package com.suhel.photosphere.screens.home.contract;

import com.suhel.photosphere.model.realtime.RealtimeComment;
import com.suhel.photosphere.model.realtime.RealtimeLike;
import com.suhel.photosphere.model.response.Post;

import java.util.List;

public interface HomeContract {

    interface View {

        void redirectToLogin();

        void onNewData(List<Post> posts);

        void onFailLikePost(Post post);

        void onFailUnlikePost(Post post);

        void onRealtimeLike(RealtimeLike like);

        void onRealtimeUnlike(RealtimeLike like);

        void onRealtimeAddComment(RealtimeComment comment);

        void onRealtimeDeleteComment(RealtimeComment comment);

    }

    interface Presenter {

        void addSocketListeners();

        void removeSocketListeners();

        void checkLoginStatus();

        void getAllPosts();

        void updateFirebaseToken();

        void likePost(Post post);

        void unlikePost(Post post);

    }

}
