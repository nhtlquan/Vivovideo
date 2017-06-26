
package com.smile.studio.network.ver2.model.ListBookMark;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("poster_url")
    @Expose
    private Object posterUrl;

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Object getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(Object posterUrl) {
        this.posterUrl = posterUrl;
    }

}
