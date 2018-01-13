package com.suhel.photosphere.screens.home.presenter.upload;

import com.suhel.photosphere.screens.home.contract.TimelineContract;
import com.suhel.photosphere.screens.home.contract.UploadContract;
import com.suhel.photosphere.service.realtime.WS;
import com.suhel.photosphere.service.rest.RestService;

public abstract class UploadPresenter implements UploadContract.Presenter {

    protected UploadContract.View view;
    protected RestService restService;

    public UploadPresenter(UploadContract.View view, RestService restService) {
        this.view = view;
        this.restService = restService;
    }

}
