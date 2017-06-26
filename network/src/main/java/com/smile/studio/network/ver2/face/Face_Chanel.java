package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.ChanelItem.ItemChanel;
import com.smile.studio.network.ver2.model.ListChanel.ListChanel;
import com.squareup.okhttp.ResponseBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_Chanel {
    @GET("live-channel/{ID}")
    Call<ItemChanel> getDetailChanel(@Path("ID") int ID, @Query("platform") String platform, @Header("Authorization") String authorization);

    @GET("live-channel")
    Call<ListChanel> getListChanel(@Query("page") int page, @Query("pageSize") int pageSize, @Query("platform") String platform, @Header("Authorization") String authorization);

    @GET("live-channel")
    Call<ListChanel> getListChanelCategoryID(@Query("category_ids[]") int category_ids, @Query("page") int page, @Query("pageSize") int pageSize, @Query("platform") String platform, @Header("Authorization") String authorization);

    @GET("/get_video_info?&video_id=YmDCt4RTesQ")
    Call<String> getLink();
}
