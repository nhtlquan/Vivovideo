
package com.smile.studio.network.ver2.model.ListSeries;

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
    private String bigPosterUrl;

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

    public String getBigPosterUrl() {
        return bigPosterUrl;
    }

    public void setBigPosterUrl(String bigPosterUrl) {
        this.bigPosterUrl = bigPosterUrl;
    }

}
