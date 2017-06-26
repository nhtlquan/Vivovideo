package com.smile.studio.network.ver2.model.Login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.smile.studio.network.utils.Debug;

/**
 * Created by admin on 15/08/2016.
 */
public class LoginRequest {
    @SerializedName("phone")
    private String phone;
    @SerializedName("password")
    private String password;
    @SerializedName("client_id")
    private String client_id;
    @SerializedName("client_secret")
    private String client_secret;

    public LoginRequest(String phone, String password, String client_id, String client_secret) {
        this.phone = phone;
        this.password = password;
        this.client_id = client_id;
        this.client_secret = client_secret;
    }

    public String getusername() {
        return phone;
    }

    public void setusername(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }
}
