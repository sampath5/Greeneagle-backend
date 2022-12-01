package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Invoice;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer>{

	public Order save(Order order);
	
	public List<Order> getAllByInvoiceid(Invoice invoice);
	 
}
