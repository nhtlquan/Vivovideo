
package com.smile.studio.network.ver2.model.CommentItem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeSuccess {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DataLike data;

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

    public DataLike getData() {
        return data;
    }

    public void setData(DataLike data) {
        this.data = data;
    }

}
