package com.suhel.photosphere.service.rest;

import com.suhel.photosphere.base.model.ApiResponse;

import io.reactivex.Single;

public abstract class ApiCall<T> extends Single<ApiResponse<T>> {
}
