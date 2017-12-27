package com.suhel.photosphere.screens.comments.contract;

import com.suhel.photosphere.model.response.Comment;

import java.util.List;

public interface CommentsContract {

    interface View {

        void onNoData();

        void onShowComments(List<Comment> data);

        void onCreateCommentSuccess(Comment comment);

        void onCreateCommentFailure();

        void onBusy(boolean isBusy);

    }

    interface Presenter {

        void getAllComments(String postId);

        void createComment(String postId, String comment);

    }

}
