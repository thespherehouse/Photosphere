package com.suhel.photosphere.application.contract;

import com.suhel.photosphere.screens.comments.di.CommentsComponent;
import com.suhel.photosphere.screens.home.di.HomeComponent;
import com.suhel.photosphere.screens.login.di.LoginComponent;

public interface AppContract {

    LoginComponent.Builder getLoginComponent();

    HomeComponent.Builder getTimelineComponent();

    CommentsComponent.Builder getCommentsComponent();

}
