package com.smile.studio.network.ver2.face;

import com.smile.studio.network.ver2.model.screen_menu.ScreenMenu;
import com.smile.studio.network.ver2.model.screen_menu.ScreenMenuList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by admin on 15/08/2016.
 */
public interface Face_ScreenMenu {

    @GET("screen-menu?platform=Phone")
    Call<ScreenMenu> getListScreen();
}
