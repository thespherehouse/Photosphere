package com.suhel.photosphere.screens.editPost.contract;

import com.suhel.photosphere.model.response.Post;

public interface EditPostContract {

    interface View {

        void onUpdateUI(Post post);

        void onEditSuccess(Post post);

        void onEditFailed();

        void onShowBusy();

        void onHideBusy();

    }

    interface Presenter {

        void setPost(Post post);

        void edit(String title, String description);

    }

}
