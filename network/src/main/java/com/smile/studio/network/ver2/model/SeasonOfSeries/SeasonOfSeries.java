
package com.smile.studio.network.ver2.model.SeasonOfSeries;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeasonOfSeries {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ItemSeasonOfSeries> data = null;

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

    public List<ItemSeasonOfSeries> getData() {
        return data;
    }

    public void setData(List<ItemSeasonOfSeries> data) {
        this.data = data;
    }

}
