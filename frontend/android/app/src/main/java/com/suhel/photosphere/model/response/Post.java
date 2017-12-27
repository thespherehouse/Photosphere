package com.suhel.photosphere.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {

    private final static long serialVersionUID = -7602696854015856948L;

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("owner")
    @Expose
    private String owner;

    @SerializedName("ownerName")
    @Expose
    private String ownerName;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("aspectRatio")
    @Expose
    private float aspectRatio;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("commentsCount")
    @Expose
    private int commentsCount;

    @SerializedName("likesCount")
    @Expose
    private int likesCount;

    @SerializedName("updatedAt")
    @Expose
    private Date updatedAt;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

    @SerializedName("isLikedByMe")
    @Expose
    private boolean isLikedByMe;

    @SerializedName("isCommentedByMe")
    @Expose
    private boolean isCommentedByMe;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isLikedByMe() {
        return isLikedByMe;
    }

    public void setLikedByMe(boolean likedByMe) {
        isLikedByMe = likedByMe;
    }

    public boolean isCommentedByMe() {
        return isCommentedByMe;
    }

    public void setCommentedByMe(boolean commentedByMe) {
        isCommentedByMe = commentedByMe;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Post && ((Post) obj).id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
