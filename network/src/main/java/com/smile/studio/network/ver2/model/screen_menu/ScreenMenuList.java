
package com.smile.studio.network.ver2.model.screen_menu;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScreenMenuList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("screen_menu_items")
    @Expose
    private List<ScreenMenuItem> screenMenuItems = null;
    @SerializedName("isHasChild")
    @Expose
    private Integer isHasChild;
    @SerializedName("icon_file")
    @Expose
    private String iconFile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScreenMenuItem> getScreenMenuItems() {
        return screenMenuItems;
    }

    public void setScreenMenuItems(List<ScreenMenuItem> screenMenuItems) {
        this.screenMenuItems = screenMenuItems;
    }

    public Integer getIsHasChild() {
        return isHasChild;
    }

    public void setIsHasChild(Integer isHasChild) {
        this.isHasChild = isHasChild;
    }

    public String getIconFile() {
        return iconFile;
    }

    public void setIconFile(String iconFile) {
        this.iconFile = iconFile;
    }

}
