package com.suhel.photosphere.screens.comments.di;

import com.suhel.photosphere.base.di.BaseModule;
import com.suhel.photosphere.screens.comments.contract.CommentsContract;
import com.suhel.photosphere.screens.comments.presenter.CommentsPresenter;
import com.suhel.photosphere.screens.comments.presenter.CommentsPresenterImpl;
import com.suhel.photosphere.service.rest.RestService;
import com.suhel.photosphere.service.storage.Store;

import dagger.Module;
import dagger.Provides;

@Module
public class CommentsModule extends BaseModule {

    private CommentsContract.View view;

    public CommentsModule(CommentsContract.View view) {
        this.view = view;
    }

    @Provides
    CommentsPresenter providesPresenter(RestService restService, Store store) {
        return new CommentsPresenterImpl(view, restService, store);
    }

}
