
package com.smile.studio.network.ver2.model.MovieCategory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smile.studio.network.ver2.model.screen_menu.ScreenMenuItem;

public class MovieCategory {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ScreenMenuItem> data = null;

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

    public List<ScreenMenuItem> getData() {
        return data;
    }

    public void setData(List<ScreenMenuItem> data) {
        this.data = data;
    }

}
