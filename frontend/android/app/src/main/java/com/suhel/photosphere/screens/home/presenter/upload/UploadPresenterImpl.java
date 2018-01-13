package com.suhel.photosphere.screens.home.presenter.upload;

import com.suhel.photosphere.base.model.ApiError;
import com.suhel.photosphere.model.response.Post;
import com.suhel.photosphere.screens.home.contract.UploadContract;
import com.suhel.photosphere.service.rest.ApiSubscriber;
import com.suhel.photosphere.service.rest.RestService;

import java.io.File;

public class UploadPresenterImpl extends UploadPresenter {

    public UploadPresenterImpl(UploadContract.View view, RestService restService) {
        super(view, restService);
    }

}
