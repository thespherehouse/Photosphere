package com.suhel.photosphere.screens.home.presenter.groups;

import com.suhel.photosphere.base.model.ApiError;
import com.suhel.photosphere.model.response.Group;
import com.suhel.photosphere.screens.home.contract.ChatListContract;
import com.suhel.photosphere.service.rest.ApiSubscriber;
import com.suhel.photosphere.service.rest.RestService;

import java.util.List;

public class ChatListPresenterImpl extends ChatListPresenter {

    public ChatListPresenterImpl(ChatListContract.View view, RestService restService) {
        super(view, restService);
    }

    @Override
    public void getNewGroups() {
        restService.getGroups(0, 30, new ApiSubscriber.Callback<List<Group>>() {

            @Override
            public void onStart() {
                super.onStart();
                view.onShowBusy();
            }

            @Override
            public void onSuccess(List<Group> data) {
                super.onSuccess(data);
                view.onNewGroups(data);
            }

            @Override
            public void onApiError(ApiError apiError) {
                super.onApiError(apiError);
                view.onLoadError();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                view.onLoadError();
            }

            @Override
            public void onEnd() {
                super.onEnd();
                view.onHideBusy();
            }

        });
    }

    @Override
    public void getMoreGroups(int skip, int limit) {
        restService.getGroups(skip, limit, new ApiSubscriber.Callback<List<Group>>() {

            @Override
            public void onStart() {
                super.onStart();
                view.onShowBusy();
            }

            @Override
            public void onSuccess(List<Group> data) {
                super.onSuccess(data);
                view.onMoreGroups(data);
            }

            @Override
            public void onApiError(ApiError apiError) {
                super.onApiError(apiError);
                view.onLoadError();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                view.onLoadError();
            }

            @Override
            public void onEnd() {
                super.onEnd();
                view.onHideBusy();
            }

        });
    }

}
