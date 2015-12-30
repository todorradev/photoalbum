package com.toshko.photoalbum.dto;

import java.util.Collection;

public class ShowPicturesByFiltering {

	private Collection<Picture> filteredPictures;

	public Collection<Picture> getFilteredPictures() {
		return filteredPictures;
	}

	public void setFilteredPictures(Collection<Picture> filteredPictures) {
		this.filteredPictures = filteredPictures;
	}
}
