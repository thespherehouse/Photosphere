package com.suhel.photosphere.screens.home.di;


import com.suhel.photosphere.base.di.BaseSubcomponent;
import com.suhel.photosphere.screens.home.di.chatList.ChatListComponent;
import com.suhel.photosphere.screens.home.di.timeline.TimelineComponent;
import com.suhel.photosphere.screens.home.view.HomeActivity;

import dagger.Subcomponent;

@HomeScope
@Subcomponent(modules = HomeModule.class)
public interface HomeComponent extends BaseSubcomponent {

    void inject(HomeActivity activity);

    TimelineComponent.Builder timelineComponentBuilder();

    ChatListComponent.Builder chatListComponentBuilder();

    @Subcomponent.Builder
    interface Builder extends BaseSubcomponent.Builder<HomeComponent, HomeModule> {

    }

}
