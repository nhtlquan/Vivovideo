
package com.smile.studio.network.ver2.model.CommentItem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemComment {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DataComment data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataComment getData() {
        return data;
    }

    public void setData(DataComment data) {
        this.data = data;
    }

}
