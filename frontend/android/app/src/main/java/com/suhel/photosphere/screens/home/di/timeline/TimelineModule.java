package com.suhel.photosphere.screens.home.di.timeline;

import com.suhel.photosphere.base.di.BaseModule;
import com.suhel.photosphere.screens.home.contract.TimelineContract;
import com.suhel.photosphere.screens.home.presenter.timeline.TimelinePresenter;
import com.suhel.photosphere.screens.home.presenter.timeline.TimelinePresenterImpl;
import com.suhel.photosphere.service.realtime.WS;
import com.suhel.photosphere.service.rest.RestService;

import dagger.Module;
import dagger.Provides;

@Module
public class TimelineModule extends BaseModule {

    private TimelineContract.View view;

    public TimelineModule(TimelineContract.View view) {
        this.view = view;
    }

    @Provides
    TimelinePresenter providesGlobalChatPresenter(RestService restService, WS ws) {
        return new TimelinePresenterImpl(view, restService, ws);
    }

}
