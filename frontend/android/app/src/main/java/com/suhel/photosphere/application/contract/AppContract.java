package com.suhel.photosphere.application.contract;

import com.suhel.photosphere.custom.app.LifecycleManager;
import com.suhel.photosphere.screens.comments.di.CommentsComponent;
import com.suhel.photosphere.screens.home.di.HomeComponent;
import com.suhel.photosphere.screens.login.di.LoginComponent;
import com.suhel.photosphere.service.rest.RestService;

public interface AppContract {

    LifecycleManager getLifecycleManager();

    RestService getRestService();

    LoginComponent.Builder getLoginComponent();

    HomeComponent.Builder getTimelineComponent();

    CommentsComponent.Builder getCommentsComponent();

}
