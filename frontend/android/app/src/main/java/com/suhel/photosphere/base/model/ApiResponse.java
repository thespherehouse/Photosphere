package com.suhel.photosphere.base.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 3961110455119632764L;

    @SerializedName("data")
    @Expose
    private T data;

    @SerializedName("error")
    @Expose
    private ApiError apiError;

    public T getData() {
        return data;
    }

    public ApiError getApiError() {
        return apiError;
    }

}
