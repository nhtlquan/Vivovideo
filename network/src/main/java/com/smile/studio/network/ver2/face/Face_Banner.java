package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.Banner.Banner;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_Banner {
    @GET("banner")
    Call<Banner> getBanner(@Query("platform") String platform, @Header("Authorization") String header);
}
