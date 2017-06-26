
package com.smile.studio.network.ver2.model.UserProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("has_info")
    @Expose
    private int has_info;
    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("verified")
    @Expose
    private Integer verified;

    public int getHas_info() {
        return has_info;
    }

    public void setHas_info(int has_info) {
        this.has_info = has_info;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }

}
