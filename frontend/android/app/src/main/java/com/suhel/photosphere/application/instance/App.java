package com.suhel.photosphere.application.instance;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.suhel.photosphere.application.contract.AppContract;
import com.suhel.photosphere.application.di.AppComponent;
import com.suhel.photosphere.application.di.AppModule;
import com.suhel.photosphere.application.di.DaggerAppComponent;
import com.suhel.photosphere.screens.comments.di.CommentsComponent;
import com.suhel.photosphere.screens.home.di.HomeComponent;
import com.suhel.photosphere.screens.login.di.LoginComponent;

public class App extends Application implements AppContract {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @Override
    public LoginComponent.Builder getLoginComponent() {
        return appComponent.getLoginComponent();
    }

    @Override
    public HomeComponent.Builder getTimelineComponent() {
        return appComponent.getTimelineComponent();
    }

    @Override
    public CommentsComponent.Builder getCommentsComponent() {
        return appComponent.getCommentsComponent();
    }

}
