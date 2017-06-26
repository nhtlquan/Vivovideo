package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.Banner.Banner;
import com.smile.studio.network.ver2.model.ScheduleByDay.ScheduleByDay;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_ScheduleByDay {
    @GET("live-channel/schedule-by-day")
    Call<ScheduleByDay> getEpg(@Query("day") String day, @Query("liveChannelId") int liveChannelId, @Header("Authorization") String header, @Query("platform") String platform);
}
