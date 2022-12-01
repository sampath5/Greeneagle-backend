package com.ecommerce.model;

public class UpdateUserDetailsResponse {

	private UpdateUserDetails userDetails;
	private String error;
	
	public UpdateUserDetailsResponse(UpdateUserDetails userDetails, String error) {
		super();
		this.userDetails = userDetails;
		this.error = error;
	}
	public UpdateUserDetails getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UpdateUserDetails userDetails) {
		this.userDetails = userDetails;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
