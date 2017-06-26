package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.Banner.Banner;
import com.smile.studio.network.ver2.model.DetailEpisode.DetailEpisode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_Episode {
    @GET("series/{ID_series}/season/{ID_season}/episode/{ID_episode}")
    Call<DetailEpisode> getDetailEpisode(@Path("ID_series") int ID_series, @Path("ID_season") int ID_season, @Path("ID_episode") int ID_episode, @Header("Authorization") String header, @Query("platform") String platform);
}
