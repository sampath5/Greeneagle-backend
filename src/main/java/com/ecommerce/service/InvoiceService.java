package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.InvoiceDao;
import com.ecommerce.entity.Invoice;
import com.ecommerce.entity.User;

@Service
public class InvoiceService {
	
	@Autowired
	InvoiceDao invoiceDao;
	
	public Invoice saveInvoice(Invoice invoice) {
		return invoiceDao.saveInvoice(invoice);
	}
	
	public List<Invoice> getInvoiceByUser(User user){
		return invoiceDao.getAllByUser(user);
	}
	
	public Invoice getInvoiceById(int invoiceId) {
		return invoiceDao.getInvoiceById(invoiceId);
	}
}
