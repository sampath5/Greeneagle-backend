package com.ecommerce.model;

import org.springframework.http.HttpStatus;

public class LoginResponse {

	private String error;
	private HttpStatus statusCode;
	private String jwtToken;
	private String userName;
	private String role;
	
	public LoginResponse(String error, HttpStatus statusCode, String jwtToken, String userName, String role) {
		super();
		this.error = error;
		this.statusCode = statusCode;
		this.jwtToken = jwtToken;
		this.userName = userName;
		this.role = role;
	}

	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	
}
