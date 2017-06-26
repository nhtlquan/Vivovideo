package vn.lequan.vivovideo.model;


/**
 * Created by Administrator on 25/10/2016.
 */

public class ImageWellcome {
    private String title;
    private int imageView;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public ImageWellcome(String title, int imageView) {
        this.title = title;
        this.imageView = imageView;
    }
}
