package com.ecommerce.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.entity.Transaction;
import com.ecommerce.repository.TransactionRepo;

@Component
public class TransactionDao {

	@Autowired
	TransactionRepo transRepo;
	
	public Transaction saveTransaction(Transaction trans) {
		return transRepo.save(trans);
	}

	public Transaction findById(int transactionId) {
		return transRepo.getByInvoice_InvoiceId(transactionId);
	}
}
