package com.ecommerce.model;

import javax.validation.constraints.NotBlank;

public class LoginModel {

	@NotBlank(message="Email is missing")
	private String email;
	@NotBlank(message="Password is missing")
	private String password;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
