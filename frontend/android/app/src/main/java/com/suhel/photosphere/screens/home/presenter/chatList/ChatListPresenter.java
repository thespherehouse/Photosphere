package com.suhel.photosphere.screens.home.presenter.chatList;

import com.suhel.photosphere.screens.home.contract.ChatListContract;
import com.suhel.photosphere.service.rest.RestService;
import com.suhel.photosphere.utils.UserStore;

public abstract class ChatListPresenter implements ChatListContract.Presenter {

    protected ChatListContract.View view;
    protected RestService restService;

    public ChatListPresenter(ChatListContract.View view, RestService restService) {
        this.view = view;
        this.restService = restService;
    }

}
