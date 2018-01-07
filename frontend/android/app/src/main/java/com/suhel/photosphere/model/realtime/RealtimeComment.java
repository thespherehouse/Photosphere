package com.suhel.photosphere.model.realtime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.suhel.photosphere.model.response.Comment;

public class RealtimeComment {

    @SerializedName("postId")
    @Expose
    private String postId;

    @SerializedName("comment")
    @Expose
    private Comment comment;

    public String getPostId() {
        return postId;
    }

    public Comment getComment() {
        return comment;
    }

}
