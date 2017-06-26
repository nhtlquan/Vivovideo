package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.VideoByCategory.VideoByCategory;
import com.smile.studio.network.ver2.model.VideoItem.ItemVideo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_Video {
    @GET("vod/getVideoById/{ID}")
    Call<ItemVideo> getDetailVideo(@Path("ID") int ID, @Query("platform") String platform, @Header("Authorization") String authorization);

    @GET("vod")
    Call<VideoByCategory> getVideoByCategory(@Query("category_ids[]") int category_ids, @Query("page") int page, @Query("pageSize") int pageSize, @Query("platform") String platform, @Header("Authorization") String authorization);
}
