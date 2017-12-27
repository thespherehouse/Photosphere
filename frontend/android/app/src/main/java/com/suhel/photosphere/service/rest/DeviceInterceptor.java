package com.suhel.photosphere.service.rest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DeviceInterceptor implements Interceptor {

    private Context context;

    DeviceInterceptor(Context context) {
        this.context = context;
    }

    @SuppressLint("HardwareIds")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();

        String deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        requestBuilder.addHeader(Field.DEVICE_ID, deviceId);

        return chain.proceed(requestBuilder.build());
    }

    private interface Field {
        String DEVICE_ID = "Device-ID";
    }

}
