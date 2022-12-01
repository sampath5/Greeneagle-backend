package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Invoice;
import com.ecommerce.entity.User;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Integer>{

	List<Invoice> getAllByUser(User user);
	
	@SuppressWarnings("unchecked")
	Invoice save(Invoice invoice);
	
	List<Invoice> getAllByUserOrderByInvoiceIdDesc(User user);
}
