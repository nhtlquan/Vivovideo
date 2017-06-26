
package com.smile.studio.network.ver2.model.MovieItem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vote {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("avarage")
    @Expose
    private Object avarage;
    @SerializedName("user_vote")
    @Expose
    private Integer userVote;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Object getAvarage() {
        return avarage;
    }

    public void setAvarage(Object avarage) {
        this.avarage = avarage;
    }

    public Integer getUserVote() {
        return userVote;
    }

    public void setUserVote(Integer userVote) {
        this.userVote = userVote;
    }

}
