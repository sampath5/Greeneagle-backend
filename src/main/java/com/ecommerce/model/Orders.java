package com.ecommerce.model;

import java.util.Date;
import java.util.List;

import com.ecommerce.entity.Address;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;

public class Orders {

	private int invoiceId;
	private float amount;
	private boolean paymentStatus;
	private Date orderedDate;
	private Address address;
	private boolean status;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	private List<Order> orderList;
	public int getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public boolean isPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Date getOrderedDate() {
		return orderedDate;
	}
	public void setOrderedDate(Date orderedDate) {
		this.orderedDate = orderedDate;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<Order> getProductList() {
		return orderList;
	}
	public void setProductList(List<Order> orderList) {
		this.orderList = orderList;
	}
	
	
}
