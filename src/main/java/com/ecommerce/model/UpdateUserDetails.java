package com.ecommerce.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UpdateUserDetails {
	
	@NotEmpty(message = "FirstName must not be null")
	@Pattern(regexp="^[A-Za-z ]+$",message = "FirstName must not be null")
	private String firstName;
	
	@NotEmpty(message = "Lastname must not be null")
	@Pattern(regexp="^[A-Za-z]+$",message = "Lastname must not be null")
	private String lastName;
	
	@NotEmpty(message = "Phone number is missing")
	@Pattern(regexp="^(0|[1-9][0-9]*)$",message = "Phone number is missing")
	private String phone;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
