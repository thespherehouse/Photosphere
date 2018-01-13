package com.suhel.photosphere.screens.createPost.presenter;

import android.support.annotation.NonNull;

import com.suhel.photosphere.screens.createPost.contract.CreatePostContract;
import com.suhel.photosphere.service.realtime.WS;
import com.suhel.photosphere.service.rest.RestService;
import com.suhel.photosphere.service.storage.Store;

public abstract class CreatePostPresenter implements CreatePostContract.Presenter {

    protected CreatePostContract.View view;
    protected RestService restService;

    CreatePostPresenter(@NonNull CreatePostContract.View view, RestService restService) {
        this.view = view;
        this.restService = restService;
    }

}
