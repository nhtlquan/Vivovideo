package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.MovieCategory.MovieCategory;
import com.smile.studio.network.ver2.model.MovieItem.Movie_Key;
import com.smile.studio.network.ver2.model.VideoItem.ItemVideo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_Movie {
    @GET("movie/{ID}")
    Call<Movie_Key> getDetailMovie(@Path("ID") int ID, @Query("platform") String platform, @Header("Authorization") String authorization);

    @GET("movie")
    Call<MovieCategory> getMovieCategory(@Query("category_ids[]") int category_ids, @Query("page") int page, @Query("pageSize") int pageSize, @Query("platform") String platform, @Header("Authorization") String authorization);
}
