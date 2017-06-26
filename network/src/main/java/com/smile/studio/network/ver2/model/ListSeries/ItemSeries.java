
package com.smile.studio.network.ver2.model.ListSeries;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemSeries {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("seo_title")
    @Expose
    private String seoTitle;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("seo_description")
    @Expose
    private String seoDescription;
    @SerializedName("trailer_url")
    @Expose
    private Object trailerUrl;
    @SerializedName("view_count")
    @Expose
    private Integer viewCount;
    @SerializedName("fake_view")
    @Expose
    private Integer fakeView;
    @SerializedName("fake_rating")
    @Expose
    private Integer fakeRating;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("seasons")
    @Expose
    private List<Season> seasons = null;

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

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public Object getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(Object trailerUrl) {
        this.trailerUrl = trailerUrl;
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

    public Integer getFakeRating() {
        return fakeRating;
    }

    public void setFakeRating(Integer fakeRating) {
        this.fakeRating = fakeRating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

}
