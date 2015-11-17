package com.toshko.photoalbum.dto;

import java.io.Serializable;



public class Category implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String description;
	
	public Category() {
		
	}
	
	public Category(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Category(int id,String name, String description) {
		this.name = name;
		this.description = description;
		this.id = id;
	}
	//getters and setters for the all property's
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return String.format("id=%d, name=%s, description=%s", id, name, description);
	}
}
