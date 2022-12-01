package com.ecommerce.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int invoiceId;
	@ManyToOne(cascade = {CascadeType.ALL})
	private User user;
	private double amount;
	private boolean cancellationStatus;
	private java.util.Date cancellationDate;
	private String cancellationReason;
	private java.util.Date refundDate;
	private String refundStatus;
	public int getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public boolean isCancellationStatus() {
		return cancellationStatus;
	}
	public void setCancellationStatus(boolean cancellationStatus) {
		this.cancellationStatus = cancellationStatus;
	}
	public java.util.Date getCancellationDate() {
		return cancellationDate;
	}
	public void setCancellationDate(java.util.Date date) {
		this.cancellationDate = date;
	}
	public String getCancellationReason() {
		return cancellationReason;
	}
	public void setCancellationReason(String cancellationReason) {
		this.cancellationReason = cancellationReason;
	}
	public java.util.Date getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(java.util.Date refundDate) {
		this.refundDate = refundDate;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	
	
}
