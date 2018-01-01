package com.suhel.photosphere.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FCM implements Serializable {

    @SerializedName("fcm")
    @Expose
    private String fcm;

    public FCM(String fcm) {
        this.fcm = fcm;
    }

}
