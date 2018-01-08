package com.suhel.photosphere.screens.home.presenter.timeline;

import com.suhel.photosphere.screens.home.contract.TimelineContract;
import com.suhel.photosphere.service.realtime.WS;
import com.suhel.photosphere.service.rest.RestService;

public abstract class TimelinePresenter implements TimelineContract.Presenter {

    protected TimelineContract.View view;
    protected RestService restService;
    protected WS ws;

    public TimelinePresenter(TimelineContract.View view, RestService restService, WS ws) {
        this.view = view;
        this.restService = restService;
        this.ws = ws;
    }

}
