package com.suhel.photosphere.screens.home.presenter.groups;

import com.suhel.photosphere.screens.home.contract.ChatListContract;
import com.suhel.photosphere.service.rest.RestService;

public abstract class ChatListPresenter implements ChatListContract.Presenter {

    protected ChatListContract.View view;
    protected RestService restService;

    public ChatListPresenter(ChatListContract.View view, RestService restService) {
        this.view = view;
        this.restService = restService;
    }

}
