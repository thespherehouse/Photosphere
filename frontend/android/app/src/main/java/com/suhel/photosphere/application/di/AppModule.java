package com.suhel.photosphere.application.di;

import android.content.Context;

import com.suhel.photosphere.base.di.BaseModule;
import com.suhel.photosphere.service.realtime.WS;
import com.suhel.photosphere.service.rest.RestService;
import com.suhel.photosphere.service.storage.Store;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule extends BaseModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    Context providesApplicationContext() {
        return context;
    }

    @Singleton
    @Provides
    RestService providesRestService(Context context) {
        return new RestService(context);
    }

    @Singleton
    @Provides
    Store providesStore(Context context) {
        return new Store(context);
    }

    @Singleton
    @Provides
    WS providesWS(Store store) {
        return new WS(store);
    }

}
