
package com.smile.studio.network.ver2.model.ListBookMark;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemListBookMark {

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
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("categories")
    @Expose
    private List<Object> categories = null;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("bookmark")
    @Expose
    private Integer bookmark;
    @SerializedName("watch_log")
    @Expose
    private Integer watchLog;
    @SerializedName("persons")
    @Expose
    private List<Person> persons = null;
    @SerializedName("view_count")
    @Expose
    private Integer viewCount;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("season_info")
    @Expose
    private SeasonInfo seasonInfo;
    @SerializedName("series_info")
    @Expose
    private SeriesInfo seriesInfo;

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Object> getCategories() {
        return categories;
    }

    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getBookmark() {
        return bookmark;
    }

    public void setBookmark(Integer bookmark) {
        this.bookmark = bookmark;
    }

    public Integer getWatchLog() {
        return watchLog;
    }

    public void setWatchLog(Integer watchLog) {
        this.watchLog = watchLog;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
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

    public SeasonInfo getSeasonInfo() {
        return seasonInfo;
    }

    public void setSeasonInfo(SeasonInfo seasonInfo) {
        this.seasonInfo = seasonInfo;
    }

    public SeriesInfo getSeriesInfo() {
        return seriesInfo;
    }

    public void setSeriesInfo(SeriesInfo seriesInfo) {
        this.seriesInfo = seriesInfo;
    }

}
