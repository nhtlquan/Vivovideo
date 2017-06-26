package com.smile.studio.libsmilestudio.security;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smile.studio.libsmilestudio.utils.Debug;

/**
 * Created by admin on 19/06/2016.
 */
public class Base {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("message64")
    @Expose
    private String message64;

    /**
     * @return The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return The message64
     */
    public String getMessage64() {
        return message64;
    }

    /**
     * @param message64 The message64
     */
    public void setMessage64(String message64) {
        this.message64 = message64;
    }

    public void trace() {
        Debug.e("status: " + status +
                "\nmessage: " + message +
                "\ndata: " + data +
                "\nmessage64: " + message64);
    }
}
