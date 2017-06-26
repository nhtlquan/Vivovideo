
package com.smile.studio.network.ver2.model.VideoItem;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smile.studio.network.ver2.model.screen_menu.ScreenMenuItem;

public class Data {

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
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("view_count")
    @Expose
    private Integer viewCount;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("file_id")
    @Expose
    private Integer fileId;
    @SerializedName("watch_log")
    @Expose
    private int watchLog;
    @SerializedName("persons")
    @Expose
    private List<Person> persons = null;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("bookmark")
    @Expose
    private int bookmark;
    @SerializedName("seo_info")
    @Expose
    private SeoInfo seoInfo;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("related_video")
    @Expose
    private List<ScreenMenuItem> relatedVideo = null;
    @SerializedName("link_stream")
    @Expose
    private String linkStream;

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

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public int getWatchLog() {
        return watchLog;
    }

    public void setWatchLog(int watchLog) {
        this.watchLog = watchLog;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public int getBookmark() {
        return bookmark;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }

    public SeoInfo getSeoInfo() {
        return seoInfo;
    }

    public void setSeoInfo(SeoInfo seoInfo) {
        this.seoInfo = seoInfo;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<ScreenMenuItem> getRelatedVideo() {
        return relatedVideo;
    }

    public void setRelatedVideo(List<ScreenMenuItem> relatedVideo) {
        this.relatedVideo = relatedVideo;
    }

    public String getLinkStream() {
        return linkStream;
    }

    public void setLinkStream(String linkStream) {
        this.linkStream = linkStream;
    }

}
