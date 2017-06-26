package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.NavMenu.NavMenu;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_NavMenu {

    @GET("nav-menu")
    Call<NavMenu> getListNavMenu(@Query("platform") String platform, @Header("Authorization") String header);
}
