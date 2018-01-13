package com.suhel.photosphere.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Group implements Serializable {

    private static final long serialVersionUID = 5882271789097323563L;

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("isPrivate")
    @Expose
    private boolean isPrivate;

    @SerializedName("members")
    @Expose
    private String[] members;

    @SerializedName("admins")
    @Expose
    private String[] admins;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

    @SerializedName("amIAdmin")
    @Expose
    private boolean amIAdmin;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String[] getMembers() {
        return members;
    }

    public String[] getAdmins() {
        return admins;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public boolean isAmIAdmin() {
        return amIAdmin;
    }

}
