package com.ecommerce.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserSignupModel {

	@NotEmpty(message = "FirstName must not be null")
	@Pattern(regexp="^[A-Za-z]+$",message = "FirstName must not be null")
	private String firstName;
	@NotEmpty(message = "Lastname must not be null")
	@Pattern(regexp="^[A-Za-z]+$",message = "Lastname must not be null")
	private String lastName;
	@Email()
	@NotEmpty(message="Email must not be empty")
	private String email;
	@NotEmpty(message="password must not be empty")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
    message = "Password must contain atleast 1 uppercase, 1 lowercase, 1 special character and 1 number ")
	private String password;
	@NotEmpty(message = "Phone number is missing")
	@Pattern(regexp="^(0|[1-9][0-9]*)$",message = "Phone number is missing")
	private String phone;
//	@NotEmpty
	private String aptNo;
	@NotEmpty(message="Street is missing")
	private String street;
	@NotEmpty(message="City must not be empty")
	private String city;
	@NotEmpty(message="State must not be empty")
	private String state;
	@NotNull(message="zipcode must not be empty")
	@Pattern(regexp="^(0|[1-9][0-9]*){5}$",message="zipcode must not be empty")
	private String zipcode;
	
	
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
	public String getAptNo() {
		return aptNo;
	}
	public void setAptNo(String aptNo) {
		this.aptNo = aptNo;
	}
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
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	
}
