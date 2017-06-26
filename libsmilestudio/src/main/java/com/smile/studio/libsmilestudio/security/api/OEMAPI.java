package com.smile.studio.libsmilestudio.security.api;

import com.smile.studio.libsmilestudio.security.Base;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by admin on 19/06/2016.
 */
public interface OEMAPI {

    @FormUrlEncoded
    @POST("blogradio/api/createapp.php")
    Call<Base> putCreateApp(@Field("debug") boolean debug, @Field("packagename") String packagename, @Field("version") String version, @Field("key_Client") String key_Client, @Field("key_Server") String key_Server, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("blogradio/api/createapp.php")
    Call<Base> putcheckApp(@Field("debug") boolean debug, @Field("packagename") String packagename, @Field("version") String version, @Field("key_Client") String key_Client);

}
