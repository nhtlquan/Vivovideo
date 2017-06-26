package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.BookMark.BookMark;
import com.smile.studio.network.ver2.model.BookMark.BookMarkRequest;
import com.smile.studio.network.ver2.model.ListBookMark.ModelListBookMark;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_BookMark {
    @POST("bookmark/{type}/{ID}/create")
    Call<BookMark> creatBookmark(@Path("type") String type, @Path("ID") int ID, @Body BookMarkRequest bookMarkRequest, @Header("Authorization") String header);

    @GET("bookmark")
    Call<ModelListBookMark> getListBookmark(@Query("page") int page, @Query("pageSize") int pageSize, @Query("platform") String platform, @Header("Authorization") String header);
}
