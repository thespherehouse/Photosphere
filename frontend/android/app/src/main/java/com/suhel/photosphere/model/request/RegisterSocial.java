package com.suhel.photosphere.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterSocial implements Serializable {

    private static final long serialVersionUID = 6289712109862839375L;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("socialId")
    @Expose
    private String socialId;

    @SerializedName("loginType")
    @Expose
    private int loginType;

    public RegisterSocial(String name, String email, String socialId, int loginType) {
        this.name = name;
        this.email = email;
        this.socialId = socialId;
        this.loginType = loginType;
    }

}
