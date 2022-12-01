package com.ecommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
//	@NotEmpty(message="product name is required")
	private String productName;
//	@NotEmpty(message="Category is required")
	private String Category;
//	@NotNull(message="quantity cannot be 0")
	private int quantity;
//	@NotNull(message="Price is required")
	private int price;
//	private image;
//	@NotEmpty(message="Brand name is required")
	private String brand;
//	@NotEmpty
	private String model;
//	@NotEmpty
	private String description;
	private boolean isActive;
//	@NotEmpty
	@Column(length = 16777215, columnDefinition = "LONGBLOB" ) 
    private byte[] primaryImage;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte[] getPrimaryImage() {
		return primaryImage;
	}
	public void setPrimaryImage(byte[] primaryImage) {
		this.primaryImage = primaryImage;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
