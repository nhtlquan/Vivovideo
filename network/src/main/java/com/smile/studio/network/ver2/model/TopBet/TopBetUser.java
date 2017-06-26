
package com.smile.studio.network.ver2.model.TopBet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopBetUser {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("totalWin")
    @Expose
    private Integer totalWin;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotalWin() {
        return totalWin;
    }

    public void setTotalWin(Integer totalWin) {
        this.totalWin = totalWin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
