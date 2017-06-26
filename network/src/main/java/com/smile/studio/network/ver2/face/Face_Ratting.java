package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.Ratting.Ratting;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_Ratting {

    @FormUrlEncoded
    @POST("vote/{type}/{ID}/create")
    Call<Ratting> setRatting(@Path("type") String type, @Path("ID") int ID, @Field("rating") int rating, @Header("Authorization") String authorization);
}
