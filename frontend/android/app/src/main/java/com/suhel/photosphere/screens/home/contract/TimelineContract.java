package com.suhel.photosphere.screens.home.contract;

import android.support.annotation.NonNull;

import com.suhel.photosphere.model.realtime.RealtimeComment;
import com.suhel.photosphere.model.realtime.RealtimeLike;
import com.suhel.photosphere.model.response.Post;

import java.util.List;

public interface TimelineContract {

    interface View {

        void onGetInitialPosts(List<Post> posts);

        void onGetFurtherPosts(List<Post> posts);

        void onLikeFailure(@NonNull Post post);

        void onUnlikeFailure(@NonNull Post post);

        void onDeleteSuccess(@NonNull Post post);

        void onDeleteFailure(@NonNull Post post);

        void onRealtimeLike(RealtimeLike realtimeLike);

        void onRealtimeUnlike(RealtimeLike realtimeLike);

        void onRealtimeCreateComment(RealtimeComment realtimeComment);

        void onRealtimeEditComment(RealtimeComment realtimeComment);

        void onRealtimeDeleteComment(RealtimeComment realtimeComment);

        void onShowBusy();

        void onHideBusy();

    }

    interface Presenter {

        void attachListeners();

        void detachListeners();

        void getPosts();

        void getPosts(int skip, int limit);

        void like(@NonNull Post post);

        void unlike(@NonNull Post post);

        void delete(@NonNull Post post);

    }

}
