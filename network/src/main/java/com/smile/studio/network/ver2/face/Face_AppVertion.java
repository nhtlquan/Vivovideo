package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.AppVertion.AppVertion;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_AppVertion {
    @GET("app/{packageName}")
    Call<AppVertion> getAppVertion(@Path("packageName") String packageName, @Query("platform") String platform);
}