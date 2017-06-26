
package com.smile.studio.network.ver2.model.SeriesCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeriesCategory {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private SeriesCategoryItem data;

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

    public SeriesCategoryItem getData() {
        return data;
    }

    public void setData(SeriesCategoryItem data) {
        this.data = data;
    }

}
