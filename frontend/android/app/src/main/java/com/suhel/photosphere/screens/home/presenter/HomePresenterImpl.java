package com.suhel.photosphere.screens.home.presenter;

import android.support.annotation.NonNull;

import com.suhel.photosphere.base.model.ApiError;
import com.suhel.photosphere.screens.home.contract.HomeContract;
import com.suhel.photosphere.service.realtime.WS;
import com.suhel.photosphere.service.rest.ApiSubscriber;
import com.suhel.photosphere.service.rest.RestService;
import com.suhel.photosphere.service.storage.Store;
import com.suhel.photosphere.utils.UserStore;

public class HomePresenterImpl extends HomePresenter {

    public HomePresenterImpl(@NonNull HomeContract.View view, RestService restService, Store store) {
        super(view, restService, store);
    }

    @Override
    public void checkLoginStatus() {
        String token = store.get(Store.TOKEN);
        if (token == null || token.isEmpty()) {
            view.redirectToLogin();
            return;
        }
        restService.silentLogin(new ApiSubscriber.Callback<Void>() {

            @Override
            public void onSuccess(Void data) {
            }

            @Override
            public void onApiError(ApiError apiError) {
                view.redirectToLogin();
            }

        });
    }


}
