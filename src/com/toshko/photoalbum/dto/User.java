package com.toshko.photoalbum.dto;

import java.util.ArrayList;
import java.util.List;

public class User {
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private List<Category> categories = new ArrayList<Category>();
	
	public User() {
		
	}
	
	public User (String username, String firstName, String lastName, String email) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public User (String username, String password, String firstName, String lastName, String email) {
		this(-1, username, password, firstName, lastName, email);
	}
	
	public User (int id, String username, String password, String firstName, String lastName, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	//getters and setters for all property's without "persons".For "persons"  we have only setter.
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Category> getCategories(User user) {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}	
	
	@Override
	public String toString() {
		return String.format("id=%d, username=%s, password=%s, firstName=%s, lastName=%s, email=%s", id, username, password, firstName, lastName, email);
	}
}
