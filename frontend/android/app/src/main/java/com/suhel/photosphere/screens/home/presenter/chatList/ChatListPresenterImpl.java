package com.suhel.photosphere.screens.home.presenter.chatList;

import com.suhel.photosphere.screens.home.contract.ChatListContract;
import com.suhel.photosphere.service.rest.RestService;
import com.suhel.photosphere.utils.UserStore;

public class ChatListPresenterImpl extends ChatListPresenter {

    public ChatListPresenterImpl(ChatListContract.View view, RestService restService) {
        super(view, restService);
    }
}
