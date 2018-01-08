package com.suhel.photosphere.screens.home.di.chatList;

import com.suhel.photosphere.base.di.BaseSubcomponent;
import com.suhel.photosphere.screens.home.view.chatList.ChatListFragment;

import dagger.Subcomponent;

@ChatListScope
@Subcomponent(modules = ChatListModule.class)
public interface ChatListComponent extends BaseSubcomponent {

    void inject(ChatListFragment fragment);

    @Subcomponent.Builder
    interface Builder extends BaseSubcomponent.Builder<ChatListComponent, ChatListModule> {
    }

}
