package vn.lequan.vivovideo.google.fcm;

import com.google.gson.annotations.SerializedName;
import com.smile.studio.libsmilestudio.utils.Debug;

/**
 * Created by admin on 24/07/2016.
 */
public class FCMNotificationOption {
    @SerializedName("action")
    private String action;
    @SerializedName("packagename")
    private String packagename;
    @SerializedName("message")
    private String message;
    @SerializedName("title")
    private String title;
    @SerializedName("id")
    private int ID;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void trace() {
        Debug.e("action: " + action + "\npackagename: " + packagename + "\nmessage: " + message + "\ntitle: " + title + "\nid: " + ID);
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
