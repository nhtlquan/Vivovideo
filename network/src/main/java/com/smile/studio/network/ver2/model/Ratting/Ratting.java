
package com.smile.studio.network.ver2.model.Ratting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smile.studio.network.ver2.model.ChanelItem.LiveChannel;

import java.util.List;

public class Ratting {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;

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

}
