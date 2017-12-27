package com.suhel.photosphere.service.rest;

import android.content.Context;

import com.suhel.photosphere.service.storage.Store;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    private Store store;

    TokenInterceptor(Context context) {
        this.store = new Store(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();

        String token = store.get(Store.TOKEN);

        if (token != null)
            requestBuilder.addHeader(Field.TOKEN, token);

        Response response = chain.proceed(requestBuilder.build());

        token = response.header(Field.TOKEN, null);

        if (token != null)
            store.put(Store.TOKEN, token);

        return response;
    }

    private interface Field {
        String TOKEN = "token";
    }

}
