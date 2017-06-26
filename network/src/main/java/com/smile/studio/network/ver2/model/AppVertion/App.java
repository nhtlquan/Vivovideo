
package com.smile.studio.network.ver2.model.AppVertion;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class App {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("app_name")
    @Expose
    private String appName;
    @SerializedName("package_name")
    @Expose
    private String packageName;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("publisher")
    @Expose
    private String publisher;
    @SerializedName("screen_shoots")
    @Expose
    private List<Object> screenShoots = null;
    @SerializedName("icon_url")
    @Expose
    private String iconUrl;
    @SerializedName("cover_url")
    @Expose
    private Object coverUrl;
    @SerializedName("intro_url")
    @Expose
    private Object introUrl;
    @SerializedName("version_name")
    @Expose
    private String versionName;
    @SerializedName("version_code")
    @Expose
    private Integer versionCode;
    @SerializedName("min_sdk")
    @Expose
    private Integer minSdk;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("md5")
    @Expose
    private String md5;
    @SerializedName("apk_url")
    @Expose
    private String apkUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<Object> getScreenShoots() {
        return screenShoots;
    }

    public void setScreenShoots(List<Object> screenShoots) {
        this.screenShoots = screenShoots;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Object getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(Object coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Object getIntroUrl() {
        return introUrl;
    }

    public void setIntroUrl(Object introUrl) {
        this.introUrl = introUrl;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public Integer getMinSdk() {
        return minSdk;
    }

    public void setMinSdk(Integer minSdk) {
        this.minSdk = minSdk;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

}
