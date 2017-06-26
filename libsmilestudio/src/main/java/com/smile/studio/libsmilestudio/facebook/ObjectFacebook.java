package com.smile.studio.libsmilestudio.facebook;

public class ObjectFacebook {
	
	private String title, description, url, image;

	public ObjectFacebook() {
	}

	public ObjectFacebook(String title, String description, String url, String urlimage) {
		super();
		this.title = title;
		this.description = description;
		this.url = url;
		this.image = urlimage;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
