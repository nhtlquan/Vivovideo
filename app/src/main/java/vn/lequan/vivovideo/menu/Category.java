package vn.lequan.vivovideo.menu;

public class Category {

	private String title = null;
	private int imageRes;
	private int titleRes;
	public int isOpenSub;
	
	public Category() {

	}

	public Category(String title, int imageRes) {
		super();
		this.title = title;
		this.imageRes = imageRes;
	}

	public Category(int titleRes, int imageRes) {
		super();
		this.titleRes = titleRes;
		this.imageRes = imageRes;
	}

	public String getTitle() {
		return title;
	}

	public int getImageRes() {
		return imageRes;
	}

	public int getTitleRes() {
		return titleRes;
	}

}
