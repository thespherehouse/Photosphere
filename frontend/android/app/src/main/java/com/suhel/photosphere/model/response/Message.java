package com.suhel.photosphere.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

    private static final long serialVersionUID = 7282896483586977828L;

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("user")
    @Expose
    private String userId;

    @SerializedName("userName")
    @Expose
    private String userName;

    public String getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

}
