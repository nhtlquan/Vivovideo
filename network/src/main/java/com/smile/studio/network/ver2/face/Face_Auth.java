package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.Login.Login;
import com.smile.studio.network.ver2.model.Login.LoginRequest;
import com.smile.studio.network.ver2.model.Login.ResponeLogin;
import com.smile.studio.network.ver2.model.Login.ResponeUpdatePhone;
import com.smile.studio.network.ver2.model.LoginFacebook.LoginFacebookToken;
import com.smile.studio.network.ver2.model.Register.Register;
import com.smile.studio.network.ver2.model.UserProfile.UserProfile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_Auth {
    @POST("user/login")
    Call<Login> actionLogin(@Body LoginRequest loginRequest);

    @GET("user/profile")
    Call<UserProfile> getUserProfile(@Header("Authorization") String header, @Query("forceUpdate") String forceUpdate);

    @FormUrlEncoded
    @POST("user/profile")
    Call<UserProfile> setUserProfile(@Field("gender_id") String gender_id, @Field("last_name") String last_name, @Field("email") String email, @Field("address") String address, @Header("Authorization") String header);

    @FormUrlEncoded
    @POST("user/verify")
    Call<UserProfile> reQuestCode(@Field("phone") String phone, @Field("access_token") String token, @Field("client_id") String client_id, @Field("client_secret") String client_secret, @Header("Authorization") String header);

    @FormUrlEncoded
    @POST("user/verify/check")
    Call<UserProfile> vetifyCode(@Field("code") String code, @Field("token") String token, @Field("client_id") String client_id, @Field("client_secret") String client_secret, @Header("Authorization") String header);

    @FormUrlEncoded
    @POST("user/recover/check")
    Call<UserProfile> vetifyRecover(@Field("code") String code, @Field("recover_id") String recover_id, @Field("client_id") String client_id, @Field("client_secret") String client_secret);

    @FormUrlEncoded
    @POST("user/update-phone")
    Call<ResponeUpdatePhone> updatePhone(@Field("token") String token, @Field("client_id") String client_id, @Field("client_secret") String client_secret, @Field("password") String password, @Field("name") String name);

    @FormUrlEncoded
    @POST("user/update-phone")
    Call<ResponeUpdatePhone> updatePass(@Field("token") String token, @Field("client_id") String client_id, @Field("client_secret") String client_secret, @Field("old_password") String old_password, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/recover")
    Call<ResponeLogin> recoverPassword(@Field("phone") String phone, @Field("client_id") String client_id, @Field("client_secret") String client_secret);

    @FormUrlEncoded
    @POST("user/register")
    Call<Register> userRegister(@Field("phone") String phone, @Field("password") String password, @Field("name") String name, @Field("client_id") String client_id, @Field("client_secret") String client_secret);

    @FormUrlEncoded
    @POST("user/social/auth")
    Call<LoginFacebookToken> loginFacebook(@Query("provider") String provider, @Field("token") String token, @Field("client_id") String client_id, @Field("client_secret") String client_secret);

    @FormUrlEncoded
    @POST("user/social/auth")
    Call<LoginFacebookToken> loginFacebookUserToken(@Query("provider") String provider, @Field("user_token") String user_token, @Field("token") String token, @Field("client_id") String client_id, @Field("client_secret") String client_secret);


}
