
package com.smile.studio.network.ver2.model.VideoByCategory;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smile.studio.network.ver2.model.screen_menu.ScreenMenuItem;

public class Data {

    @SerializedName("platform")
    @Expose
    private String platform;
    @SerializedName("items")
    @Expose
    private List<ScreenMenuItem> items = null;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public List<ScreenMenuItem> getItems() {
        return items;
    }

    public void setItems(List<ScreenMenuItem> items) {
        this.items = items;
    }

}
