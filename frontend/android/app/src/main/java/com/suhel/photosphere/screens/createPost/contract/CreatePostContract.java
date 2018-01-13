package com.suhel.photosphere.screens.createPost.contract;

import java.io.File;

public interface CreatePostContract {

    interface View {

        void onUploadProgressUpdate(int percent);

        void onUploadSuccess();

        void onUploadFailed();

        void onShowBusy();

        void onHideBusy();

    }

    interface Presenter {

        void upload(String title, String description, File photo);

    }

}
