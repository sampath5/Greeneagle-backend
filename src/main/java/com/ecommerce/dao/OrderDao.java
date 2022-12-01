package com.ecommerce.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.entity.Invoice;
import com.ecommerce.entity.Order;
import com.ecommerce.repository.OrderRepo;

@Component
public class OrderDao {

	@Autowired
	OrderRepo orderRepo;
	
	public Order saveOrder(Order order) {
		return orderRepo.save(order);
	}
	
	public List<Order> getOrdersByInvoice(Invoice invoice) {
		return orderRepo.getAllByInvoiceid(invoice);
	}
//	pubilc void updateOrder() {
//		
//	}
}
