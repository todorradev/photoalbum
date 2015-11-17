package com.toshko.photoalbum.dto;

import java.sql.Date;


public class Picture {
	private int id;
	private String name;
	private String description;
	private Date date ;
	private int size;
	private byte[] imageContent;
	
	public Picture(String name, String description) {
		this.name = name;
		this.description = description;
	}
	public Picture(int id, String name, String description, Date date, int size, byte[] cnt) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.date = date;
		this.size = size;
		this.imageContent = cnt;
	}
	
	public Picture(String name, String description, java.sql.Date date, byte[] content) {
		this.name = name;
		this.description = description;
		this.date = date;
		this.size = content.length;
		this.imageContent = content;
	}

	/*
	 * getters and setters for name and description.
	 * for the rest of the property's we have only getters.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String descriptionOfPicture) {
		this.description = descriptionOfPicture;
	}
	
	public java.sql.Date getDate() {
		return this.date;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public byte[] getContent() {
		return imageContent;
	}

	public void setImageContent(byte[] cnt) {
		this.imageContent = cnt;
	}
	
	@Override
	public String toString() {
		return String.format("id=%d, name=%s, description=%s, date=%s, size=%d", id, name, description, date, size);
	}
	
}
