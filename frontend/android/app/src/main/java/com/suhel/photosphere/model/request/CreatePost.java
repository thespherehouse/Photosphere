package com.suhel.photosphere.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreatePost implements Serializable {

    private static final long serialVersionUID = -3595610332483880708L;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    public CreatePost(String title, String description) {
        this.title = title;
        this.description = description;
    }

}
