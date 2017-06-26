
package com.smile.studio.network.ver2.model.screen_menu;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("screen_menu")
    @Expose
    private List<ScreenMenuList> screenMenu = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScreenMenuList> getScreenMenu() {
        return screenMenu;
    }

    public void setScreenMenu(List<ScreenMenuList> screenMenu) {
        this.screenMenu = screenMenu;
    }

}
