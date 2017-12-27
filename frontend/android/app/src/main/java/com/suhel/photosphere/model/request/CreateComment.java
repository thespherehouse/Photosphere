package com.suhel.photosphere.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreateComment implements Serializable {

    private static final long serialVersionUID = -1339065441331788591L;

    @SerializedName("comment")
    @Expose
    private String comment;

    public CreateComment(String comment) {
        this.comment = comment;
    }

}
