package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer>{

	public Transaction save(Transaction transaction);

	public Transaction getByInvoice_InvoiceId(int transactionId);
}
