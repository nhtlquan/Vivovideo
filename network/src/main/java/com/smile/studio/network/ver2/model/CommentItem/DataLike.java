
package com.smile.studio.network.ver2.model.CommentItem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataLike {

    @SerializedName("isLike")
    @Expose
    private Integer isLike;

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

}
