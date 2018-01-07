package com.suhel.photosphere.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Like implements Serializable {

    private static final long serialVersionUID = 567927249991317099L;

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

    @SerializedName("user")
    @Expose
    protected User user;

    public String getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

}
