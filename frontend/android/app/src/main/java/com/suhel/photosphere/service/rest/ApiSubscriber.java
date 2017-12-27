package com.suhel.photosphere.service.rest;

import com.suhel.photosphere.base.model.ApiError;
import com.suhel.photosphere.base.model.ApiResponse;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class ApiSubscriber<T> implements SingleObserver<ApiResponse<T>> {

    private Callback<T> callback;

    public ApiSubscriber(Callback<T> callback) {
        this.callback = callback;
    }

    @Override
    public final void onSubscribe(Disposable d) {
        if (callback != null)
            callback.onStart();
    }

    @Override
    public final void onSuccess(ApiResponse<T> response) {
        if (callback == null)
            return;

        if (response.getApiError() != null && response.getApiError().getCode() == 0)
            callback.onSuccess(response.getData());
        else
            callback.onError(response.getApiError());
        callback.onEnd();
    }

    @Override
    public final void onError(Throwable t) {
        t.printStackTrace();
        if (callback == null)
            return;

        callback.onError(new ApiError(1, "Internal error"));
        callback.onEnd();
    }

    public static abstract class Callback<U> {

        public void onStart() {
        }

        public void onSuccess(U data) {
        }

        public void onError(ApiError apiError) {
        }

        public void onEnd() {
        }

    }

}
