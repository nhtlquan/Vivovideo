
package com.smile.studio.network.ver2.model.NavMenu;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("icon_url")
    @Expose
    private String iconUrl;
    @SerializedName("action_click")
    @Expose
    private ActionClick actionClick;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
    @SerializedName("fixed_menu")
    @Expose
    private Object fixedMenu;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("visible")
    @Expose
    private Integer visible;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("children")
    @Expose
    private List<MenuItem> children = null;

    public Integer getId() {
        return id;
    }

    public MenuItem() {
    }

    public MenuItem(Integer id, String name, String iconUrl, ActionClick actionClick, Integer parentId, Object fixedMenu, Integer order, Integer visible, String createdAt, Object updatedAt, List<MenuItem> children) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
        this.actionClick = actionClick;
        this.parentId = parentId;
        this.fixedMenu = fixedMenu;
        this.order = order;
        this.visible = visible;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.children = children;
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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public ActionClick getActionClick() {
        return actionClick;
    }

    public void setActionClick(ActionClick actionClick) {
        this.actionClick = actionClick;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Object getFixedMenu() {
        return fixedMenu;
    }

    public void setFixedMenu(Object fixedMenu) {
        this.fixedMenu = fixedMenu;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<MenuItem> getChildren() {
        return children;
    }

    public void setChildren(List<MenuItem> children) {
        this.children = children;
    }

}
