package com.smile.studio.network.ver2.model.BookMark;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 15/08/2016.
 */ 
public class BookMarkRequest {
    @SerializedName("status")
    private int status;

    public BookMarkRequest(int status) {
        this.status = status;
    }

    public int getstatus() {
        return status;
    }

    public void setstatus(int status) {
        this.status = status;
    }
}
