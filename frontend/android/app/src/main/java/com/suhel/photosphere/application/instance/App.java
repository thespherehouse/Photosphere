package com.suhel.photosphere.application.instance;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.suhel.photosphere.application.contract.AppContract;
import com.suhel.photosphere.application.di.AppComponent;
import com.suhel.photosphere.application.di.AppModule;
import com.suhel.photosphere.application.di.DaggerAppComponent;
import com.suhel.photosphere.custom.app.LifecycleManager;
import com.suhel.photosphere.screens.comments.di.CommentsComponent;
import com.suhel.photosphere.screens.home.di.HomeComponent;
import com.suhel.photosphere.screens.login.di.LoginComponent;
import com.suhel.photosphere.service.rest.RestService;

public class App extends Application implements AppContract {

    private AppComponent appComponent;
    private LifecycleManager lifecycleManager = new LifecycleManager();

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        registerActivityLifecycleCallbacks(lifecycleManager);
    }

    @Override
    public RestService getRestService() {
        return appComponent.getRestService();
    }

    @Override
    public LifecycleManager getLifecycleManager() {
        return lifecycleManager;
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
