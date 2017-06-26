
package com.smile.studio.network.ver2.model.ScheduleByDay;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smile.studio.network.ver2.model.ChanelItem.LiveChannelSchedule;

public class ScheduleByDay {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<LiveChannelSchedule> data = null;

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

    public List<LiveChannelSchedule> getData() {
        return data;
    }

    public void setData(List<LiveChannelSchedule> data) {
        this.data = data;
    }

}
