package com.suhel.photosphere.screens.home.di.upload;

import com.suhel.photosphere.base.di.BaseModule;
import com.suhel.photosphere.screens.home.contract.UploadContract;
import com.suhel.photosphere.screens.home.presenter.upload.UploadPresenter;
import com.suhel.photosphere.screens.home.presenter.upload.UploadPresenterImpl;
import com.suhel.photosphere.service.rest.RestService;

import dagger.Module;
import dagger.Provides;

@Module
public class UploadModule extends BaseModule {

    private UploadContract.View view;

    public UploadModule(UploadContract.View view) {
        this.view = view;
    }

    @Provides
    UploadPresenter providesUploadPresenter(RestService restService) {
        return new UploadPresenterImpl(view, restService);
    }

}
