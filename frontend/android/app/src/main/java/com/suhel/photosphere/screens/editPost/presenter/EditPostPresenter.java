package com.suhel.photosphere.screens.editPost.presenter;

import android.support.annotation.NonNull;

import com.suhel.photosphere.screens.editPost.contract.EditPostContract;
import com.suhel.photosphere.service.rest.RestService;

public abstract class EditPostPresenter implements EditPostContract.Presenter {

    protected EditPostContract.View view;
    protected RestService restService;

    EditPostPresenter(@NonNull EditPostContract.View view, RestService restService) {
        this.view = view;
        this.restService = restService;
    }

}
