package com.ecommerce.model;

import java.util.Date;
import java.util.List;

import com.ecommerce.entity.Order;

public class UserTransactions {
	
	int invoiceId;
	double amount;
	boolean paymentStatus;
	Date transactionDate;
	List<Order> orders;
	
	public UserTransactions() {
		super();
	}

	public UserTransactions(int invoiceId, double amount, boolean paymentStatus, Date transactionDate,
			List<Order> orders) {
		super();
		this.invoiceId = invoiceId;
		this.amount = amount;
		this.paymentStatus = paymentStatus;
		this.transactionDate = transactionDate;
		this.orders = orders;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
}
