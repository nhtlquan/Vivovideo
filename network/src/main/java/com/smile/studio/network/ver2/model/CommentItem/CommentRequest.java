package com.smile.studio.network.ver2.model.CommentItem;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 15/08/2016.
 */
public class CommentRequest {
    @SerializedName("content")
    private String content;

    @SerializedName("parent_id")
    private String parent_id;

    public CommentRequest(String content, String parent_id) {
        this.content = content;
        this.parent_id = parent_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
