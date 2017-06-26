
package com.smile.studio.network.ver2.model.ListSeries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Season {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("series_id")
    @Expose
    private Integer seriesId;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("total_episode")
    @Expose
    private Integer totalEpisode;
    @SerializedName("seo_title")
    @Expose
    private String seoTitle;
    @SerializedName("thumbnail_id")
    @Expose
    private Object thumbnailId;
    @SerializedName("seo_description")
    @Expose
    private String seoDescription;
    @SerializedName("view_count")
    @Expose
    private Integer viewCount;
    @SerializedName("fake_view")
    @Expose
    private Integer fakeView;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("fake_rating")
    @Expose
    private Integer fakeRating;
    @SerializedName("visible")
    @Expose
    private Integer visible;
    @SerializedName("visible_after")
    @Expose
    private Object visibleAfter;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("uploader_id")
    @Expose
    private Integer uploaderId;
    @SerializedName("published")
    @Expose
    private Integer published;
    @SerializedName("publisher_id")
    @Expose
    private Integer publisherId;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getTotalEpisode() {
        return totalEpisode;
    }

    public void setTotalEpisode(Integer totalEpisode) {
        this.totalEpisode = totalEpisode;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public Object getThumbnailId() {
        return thumbnailId;
    }

    public void setThumbnailId(Object thumbnailId) {
        this.thumbnailId = thumbnailId;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getFakeView() {
        return fakeView;
    }

    public void setFakeView(Integer fakeView) {
        this.fakeView = fakeView;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getFakeRating() {
        return fakeRating;
    }

    public void setFakeRating(Integer fakeRating) {
        this.fakeRating = fakeRating;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Object getVisibleAfter() {
        return visibleAfter;
    }

    public void setVisibleAfter(Object visibleAfter) {
        this.visibleAfter = visibleAfter;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Integer uploaderId) {
        this.uploaderId = uploaderId;
    }

    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer published) {
        this.published = published;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

}
