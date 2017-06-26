
package com.smile.studio.network.ver2.model.NavMenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActionClick {

    @SerializedName("clickable")
    @Expose
    private Integer clickable;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("isHasChild")
    @Expose
    private Integer isHasChild;

    public ActionClick(Integer clickable, String type, Integer categoryId, Integer isHasChild) {
        this.clickable = clickable;
        this.type = type;
        this.categoryId = categoryId;
        this.isHasChild = isHasChild;
    }

    public Integer getClickable() {
        return clickable;
    }

    public void setClickable(Integer clickable) {
        this.clickable = clickable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getIsHasChild() {
        return isHasChild;
    }

    public void setIsHasChild(Integer isHasChild) {
        this.isHasChild = isHasChild;
    }

}
