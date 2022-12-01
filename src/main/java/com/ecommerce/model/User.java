package com.ecommerce.model;

public class User {

	private int userId;
	private String email;
	private String username;
	private boolean isLocked;
	
	
	public User(int userId,String email, String username, boolean isLocked) {
		super();
		this.userId=userId;
		this.email = email;
		this.username = username;
		this.isLocked = isLocked;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isLocked() {
		return isLocked;
	}
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
}
