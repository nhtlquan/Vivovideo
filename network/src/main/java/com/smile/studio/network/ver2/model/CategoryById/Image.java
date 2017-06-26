
package com.smile.studio.network.ver2.model.CategoryById;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("poster_url")
    @Expose
    private String posterUrl;
    @SerializedName("logo_url")
    @Expose
    private String logoUrl;
    @SerializedName("bigPoster_url")
    @Expose
    private Object bigPosterUrl;

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Object getBigPosterUrl() {
        return bigPosterUrl;
    }

    public void setBigPosterUrl(Object bigPosterUrl) {
        this.bigPosterUrl = bigPosterUrl;
    }

}
