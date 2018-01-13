package com.suhel.photosphere.screens.createPost.di;

import com.suhel.photosphere.base.di.BaseModule;
import com.suhel.photosphere.screens.createPost.contract.CreatePostContract;
import com.suhel.photosphere.screens.createPost.presenter.CreatePostPresenter;
import com.suhel.photosphere.screens.createPost.presenter.CreatePostPresenterImpl;
import com.suhel.photosphere.service.realtime.WS;
import com.suhel.photosphere.service.rest.RestService;
import com.suhel.photosphere.service.storage.Store;

import dagger.Module;
import dagger.Provides;

@Module
public class CreatePostModule extends BaseModule {

    private CreatePostContract.View view;

    public CreatePostModule(CreatePostContract.View view) {
        this.view = view;
    }

    @Provides
    CreatePostPresenter providesPresenter(RestService restService) {
        return new CreatePostPresenterImpl(view, restService);
    }

}
