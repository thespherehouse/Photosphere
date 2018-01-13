package com.suhel.photosphere.screens.home.di.groups;

import com.suhel.photosphere.base.di.BaseModule;
import com.suhel.photosphere.screens.home.contract.ChatListContract;
import com.suhel.photosphere.screens.home.presenter.groups.ChatListPresenter;
import com.suhel.photosphere.screens.home.presenter.groups.ChatListPresenterImpl;
import com.suhel.photosphere.service.rest.RestService;

import dagger.Module;
import dagger.Provides;

@Module
public class ChatListModule extends BaseModule {

    private ChatListContract.View view;

    public ChatListModule(ChatListContract.View view) {
        this.view = view;
    }

    @Provides
    ChatListPresenter providesChatListPresenter(RestService restService) {
        return new ChatListPresenterImpl(view, restService);
    }

}
