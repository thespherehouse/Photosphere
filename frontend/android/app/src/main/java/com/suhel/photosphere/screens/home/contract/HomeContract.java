package com.suhel.photosphere.screens.home.contract;

import com.suhel.photosphere.model.response.Post;

import java.util.List;

public interface HomeContract {

    interface View {

        void redirectToLogin();

        void onNewData(List<Post> posts);

        void onFailLikePost(Post post);

        void onFailUnlikePost(Post post);

    }

    interface Presenter {

        void checkLoginStatus();

        void getAllPosts();

        void likePost(Post post);

        void unlikePost(Post post);

    }

}
