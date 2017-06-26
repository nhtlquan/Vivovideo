package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.BookMark.BookMark;
import com.smile.studio.network.ver2.model.WatchLog.WatchLog;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_WatchLog {
    @FormUrlEncoded
    @POST("watch-log/{type}/{ID}/create")
    Call<WatchLog> creatWatchLog(@Path("type") String type, @Path("ID") int ID, @Field("time") int time, @Header("Authorization") String header);

}
