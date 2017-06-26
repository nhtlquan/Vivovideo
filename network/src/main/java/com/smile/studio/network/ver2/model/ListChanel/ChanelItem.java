
package com.smile.studio.network.ver2.model.ListChanel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChanelItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("visible_after")
    @Expose
    private Object visibleAfter;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private Image image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getVisibleAfter() {
        return visibleAfter;
    }

    public void setVisibleAfter(Object visibleAfter) {
        this.visibleAfter = visibleAfter;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
