package com.suhel.photosphere.screens.home.presenter;

import android.support.annotation.NonNull;

import com.suhel.photosphere.screens.home.contract.HomeContract;
import com.suhel.photosphere.service.realtime.WS;
import com.suhel.photosphere.service.rest.RestService;
import com.suhel.photosphere.service.storage.Store;
import com.suhel.photosphere.utils.UserStore;

public abstract class HomePresenter implements HomeContract.Presenter {

    protected HomeContract.View view;
    protected RestService restService;
    protected Store store;

    HomePresenter(@NonNull HomeContract.View view, RestService restService, Store store) {
        this.view = view;
        this.restService = restService;
        this.store = store;
    }

}
