package com.suhel.photosphere.screens.home.di.timeline;

import com.suhel.photosphere.base.di.BaseSubcomponent;
import com.suhel.photosphere.screens.home.view.timeline.TimelineFragment;

import dagger.Subcomponent;

@TimelineScope
@Subcomponent(modules = TimelineModule.class)
public interface TimelineComponent extends BaseSubcomponent {

    void inject(TimelineFragment fragment);

    @Subcomponent.Builder
    interface Builder extends BaseSubcomponent.Builder<TimelineComponent, TimelineModule> {
    }

}
