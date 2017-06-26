
package com.smile.studio.network.ver2.model.UserBet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("prediction")
    @Expose
    private Prediction prediction;
    @SerializedName("profile")
    @Expose
    private Profile profile;

    public Prediction getPrediction() {
        return prediction;
    }

    public void setPrediction(Prediction prediction) {
        this.prediction = prediction;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

}
