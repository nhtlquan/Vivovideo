package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.Search.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_Search {
    @GET("search?")
    Call<Search> getListSearch(@Query("q") String q, @Query("page") int header, @Query("pageSize") int pageSize, @Query("platform") String platform, @Header("Authorization") String Authorization);
}
