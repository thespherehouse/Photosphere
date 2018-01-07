package com.suhel.photosphere.model.realtime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.suhel.photosphere.model.response.Like;

public class RealtimeLike {

    @SerializedName("postId")
    @Expose
    private String postId;

    @SerializedName("like")
    @Expose
    private Like like;

    public String getPostId() {
        return postId;
    }

    public Like getLike() {
        return like;
    }

}
