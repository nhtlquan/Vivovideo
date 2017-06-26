package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.ListSeries.ListSeries;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_Series {
    @GET("series")
    Call<ListSeries> getListSeries(@Query("page") int page, @Query("pageSize") int pageSize, @Query("category_ids[]") int category_ids, @Query("platform") String platform, @Header("Authorization") String authorization);
}
