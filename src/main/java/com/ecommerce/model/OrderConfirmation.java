package com.ecommerce.model;

public class OrderConfirmation {

	private String id;
	private String description;
	
	public OrderConfirmation(String id,String description) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.description=description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
		
}
