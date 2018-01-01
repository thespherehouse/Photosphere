package com.suhel.photosphere.application.di;

import com.suhel.photosphere.application.instance.App;
import com.suhel.photosphere.base.di.BaseComponent;
import com.suhel.photosphere.screens.comments.di.CommentsComponent;
import com.suhel.photosphere.screens.login.di.LoginComponent;
import com.suhel.photosphere.screens.home.di.HomeComponent;
import com.suhel.photosphere.service.rest.RestService;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent extends BaseComponent {

    RestService getRestService();

    LoginComponent.Builder getLoginComponent();

    HomeComponent.Builder getTimelineComponent();

    CommentsComponent.Builder getCommentsComponent();

}
