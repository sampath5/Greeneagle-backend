package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.TransactionDao;
import com.ecommerce.entity.Transaction;

@Service
public class TransactionService {

	@Autowired
	TransactionDao transDao;
	
	public Transaction saveTransaction(Transaction trans) {
		return transDao.saveTransaction(trans);
	}
	
	public Transaction getTransaction(int id) {
		return transDao.findById(id);
	}
}
