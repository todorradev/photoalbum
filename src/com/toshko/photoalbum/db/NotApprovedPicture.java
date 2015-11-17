package com.toshko.photoalbum.db;


public class NotApprovedPicture{
	private int userId;
	private int categoryId;
	private int pictureId;
	
	public NotApprovedPicture(int userId, int categoryId, int pictureId) {
		this.userId = userId;
		this.categoryId = categoryId;
		this.pictureId = pictureId;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getPictureId() {
		return pictureId;
	}

	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}
}
