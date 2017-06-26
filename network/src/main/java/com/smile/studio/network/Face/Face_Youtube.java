package com.smile.studio.network.Face;


import com.smile.studio.network.Detail_Video.VideoDetail;
import com.smile.studio.network.DetailtChanel.ChanelDetail;
import com.smile.studio.network.ListVideoChanel.ListVideoChanel;
import com.smile.studio.network.Token;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_Youtube {
    @GET("search")
    Call<ListVideoChanel> getListVideoByChanel(@Query("key") String key, @Query("channelId") String channelId, @Query("part") String part, @Query("order") String order, @Query("maxResults") int maxResults);
    @GET("search")
    Call<ListVideoChanel> getListVideoByChanelToken(@Query("pageToken") String pageToken, @Query("key") String key, @Query("channelId") String channelId, @Query("part") String part, @Query("order") String order, @Query("maxResults") int maxResults);

    @GET("search")
    Call<Token> getPageToken(@Query("pageToken") String pageToken, @Query("key") String key, @Query("channelId") String channelId, @Query("part") String part, @Query("order") String order, @Query("maxResults") int maxResults);

    @GET("search")
    Call<ListVideoChanel> getListRelateVideo(@Query("key") String key, @Query("relatedToVideoId") String relatedToVideoId, @Query("type") String type, @Query("part") String part, @Query("order") String order, @Query("maxResults") int maxResults);

    @GET("videos")
    Call<VideoDetail> getDetailVideo(@Query("key") String key, @Query("id") String id, @Query("part") String part, @Query("order") String order, @Query("maxResults") int maxResults);

    @GET("channels")
    Call<ChanelDetail> getDetailChanel(@Query("key") String key, @Query("id") String id, @Query("part") String part, @Query("order") String order, @Query("maxResults") int maxResults);
}