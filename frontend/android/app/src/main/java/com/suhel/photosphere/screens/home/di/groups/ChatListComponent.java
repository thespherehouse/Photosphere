package com.suhel.photosphere.screens.home.di.groups;

import com.suhel.photosphere.base.di.BaseSubcomponent;
import com.suhel.photosphere.screens.home.view.groups.ChatListFragment;

import dagger.Subcomponent;

@ChatListScope
@Subcomponent(modules = ChatListModule.class)
public interface ChatListComponent extends BaseSubcomponent {

    void inject(ChatListFragment fragment);

    @Subcomponent.Builder
    interface Builder extends BaseSubcomponent.Builder<ChatListComponent, ChatListModule> {
    }

}
