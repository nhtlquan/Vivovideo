
package com.smile.studio.network.ver2.model.MovieItem;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smile.studio.network.ver2.model.screen_menu.ScreenMenuItem;

public class Data {

    @SerializedName("movie")
    @Expose
    private Movie movie;
    @SerializedName("related_movies")
    @Expose
    private List<ScreenMenuItem> relatedMovies = null;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<ScreenMenuItem> getRelatedMovies() {
        return relatedMovies;
    }

    public void setRelatedMovies(List<ScreenMenuItem> relatedMovies) {
        this.relatedMovies = relatedMovies;
    }

}
