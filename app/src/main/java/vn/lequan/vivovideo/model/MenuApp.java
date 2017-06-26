package vn.lequan.vivovideo.model;

/**
 * Created by admin on 18/08/2016.
 */
public class MenuApp {

    private int id;
    private int type;
    private String image;
    private int imageID;
    private String title;
    private int titleID;

    public MenuApp(int id, int type, int imageID, int titleID) {
        this.id = id;
        this.type = type;
        this.imageID = imageID;
        this.titleID = titleID;
    }

    public MenuApp(int id, int type, String image, String title) {
        this.id = id;
        this.type = type;
        this.image = image;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTitleID() {
        return titleID;
    }

    public void setTitleID(int titleID) {
        this.titleID = titleID;
    }
}
