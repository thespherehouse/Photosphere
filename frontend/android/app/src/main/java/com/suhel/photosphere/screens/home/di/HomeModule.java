package com.suhel.photosphere.screens.home.di;

import com.suhel.photosphere.base.di.BaseModule;
import com.suhel.photosphere.service.storage.Store;
import com.suhel.photosphere.screens.home.contract.HomeContract;
import com.suhel.photosphere.screens.home.presenter.HomePresenter;
import com.suhel.photosphere.screens.home.presenter.HomePresenterImpl;
import com.suhel.photosphere.service.rest.RestService;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule extends BaseModule {

    private HomeContract.View view;

    public HomeModule(HomeContract.View view) {
        this.view = view;
    }

    @Provides
    HomePresenter providesPresenter(RestService restService, Store store) {
        return new HomePresenterImpl(view, restService, store);
    }

}
