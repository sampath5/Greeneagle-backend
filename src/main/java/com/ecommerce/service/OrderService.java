package com.ecommerce.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.OrderDao;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Invoice;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Transaction;
import com.ecommerce.entity.User;
import com.ecommerce.enums.PaymentStatus;
import com.ecommerce.enums.PaymentType;
import com.ecommerce.model.OrderConfirmation;

@Service
public class OrderService {

	@Autowired
	OrderDao orderDao;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	TransactionService transService;
	
	public Order saveOrder(Order order) {
		return orderDao.saveOrder(order);
	}
	
	public List<Order> getOrders(Invoice invoice){
		return orderDao.getOrdersByInvoice(invoice); 
	}
	
	public void orderConfirmation(OrderConfirmation orderConfirmation,String total) {
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user= userService.getUserByEmail(email);
		
		String cartIds=orderConfirmation.getDescription();
//		String[] ids=cartIds.replace("[", "").replace("]", "").split(",");
		
		List<Cart> cartList=cartService.getAllCartByUser(user);
		
		Invoice invoice=new Invoice();
		invoice.setAmount(Double.parseDouble(total));
		invoice.setUser(user);
		Invoice savedInv=invoiceService.saveInvoice(invoice);
		for(Cart cart:cartList) {
			
			
			Product prod=cart.getProd();
			int quan=prod.getQuantity()-cart.getQuantity();
			prod.setQuantity(quan);
			
			Order order=new Order();
			order.setInvoice(savedInv);
			order.setProduct(prod);
			order.setQuantity(cart.getQuantity());
			orderDao.saveOrder(order);
			
			Transaction trans=new Transaction();
			trans.setAmount(Double.parseDouble(total));
			trans.setInvoice(savedInv);
			trans.setTransactionDate(new Date());
			trans.setPaymentStatus(PaymentStatus.PAID);
			trans.setPaymentType(PaymentType.DEBIT_CARD);
			transService.saveTransaction(trans);
			
			cart.setVisible(false);
			cart.setQuantity(0);
			cart.setProd(prod);
			cartService.updateCart(cart);
			
		}
	}
}
