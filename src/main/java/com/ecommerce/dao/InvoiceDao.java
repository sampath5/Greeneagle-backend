package com.ecommerce.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.entity.Invoice;
import com.ecommerce.entity.User;
import com.ecommerce.repository.InvoiceRepo;

@Component
public class InvoiceDao {

	@Autowired
	InvoiceRepo invoiceRepo;
	
	public List<Invoice> getAllByUser(User user){
		return invoiceRepo.getAllByUser(user);
	}
	
	public Invoice saveInvoice(Invoice invoice) {
		return invoiceRepo.save(invoice);
	}
	
	public List<Invoice> getInvoiceByUser(User user){
		return invoiceRepo.getAllByUserOrderByInvoiceIdDesc(user);
	}
	
	public Invoice getInvoiceById(int invoiceId) {
		return invoiceRepo.getById(invoiceId);
	}
}
