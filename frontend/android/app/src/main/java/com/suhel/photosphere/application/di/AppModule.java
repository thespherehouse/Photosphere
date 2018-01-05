package com.suhel.photosphere.application.di;

import android.content.Context;

import com.suhel.photosphere.base.di.BaseModule;
import com.suhel.photosphere.service.realtime.SocketIO;
import com.suhel.photosphere.service.rest.RestService;
import com.suhel.photosphere.service.storage.Store;

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

    @Provides
    RestService providesRestService(Context context) {
        return new RestService(context);
    }

    @Provides
    Store providesStore(Context context) {
        return new Store(context);
    }

    @Provides
    SocketIO providesSocketIO(Store store) {
        return new SocketIO(store);
    }

}
