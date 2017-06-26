
package com.smile.studio.network.ver2.model.VideoItem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelatedVideo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("view_count")
    @Expose
    private Integer viewCount;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("free")
    @Expose
    private Integer free;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("bookmark")
    @Expose
    private Object bookmark;
    @SerializedName("watch_log")
    @Expose
    private Object watchLog;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Object getBookmark() {
        return bookmark;
    }

    public void setBookmark(Object bookmark) {
        this.bookmark = bookmark;
    }

    public Object getWatchLog() {
        return watchLog;
    }

    public void setWatchLog(Object watchLog) {
        this.watchLog = watchLog;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}
