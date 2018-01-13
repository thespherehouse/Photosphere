package com.suhel.photosphere.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EditPost implements Serializable {

    private static final long serialVersionUID = 2411338610898181357L;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    public EditPost(String title, String description) {
        this.title = title;
        this.description = description;
    }

}
