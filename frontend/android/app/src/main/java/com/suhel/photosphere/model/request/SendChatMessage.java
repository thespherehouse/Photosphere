package com.suhel.photosphere.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SendChatMessage implements Serializable {

    private static final long serialVersionUID = 2756198015393069034L;

    @SerializedName("message")
    @Expose
    private String message;

    public SendChatMessage(String message) {
        this.message = message;
    }

}
