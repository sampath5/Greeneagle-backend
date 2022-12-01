package com.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.entity.Invoice;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Transaction;
import com.ecommerce.entity.User;
import com.ecommerce.model.LoginResponse;
import com.ecommerce.model.Orders;
import com.ecommerce.model.ResponseModel;
import com.ecommerce.repository.ProductRepo;
import com.ecommerce.service.InvoiceService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.TransactionService;
import com.ecommerce.service.UserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
	
	@Autowired
	ProductService prodService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	TransactionService transactionService;
	
	@PostMapping("/addProduct")
	public ResponseEntity<ResponseModel> addProduct(@Valid @RequestBody Product prod,Errors error ){
		if(error.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(
					error.getFieldError().getDefaultMessage(),""));
		}
		Product product=prodService.addProduct(prod);		
		if(product==null)
			return  new ResponseEntity<ResponseModel>(new ResponseModel("Error adding the Product","Error adding the product"),HttpStatus.BAD_REQUEST);
		else {
			return  new ResponseEntity<ResponseModel>(new ResponseModel("Product added successfully",null),HttpStatus.ACCEPTED);
		}
	}
	
	@PostMapping("/inActivateProduct/{id}")
	public ResponseEntity<Product> inActivateProduct(@PathVariable int id){
		Product product=prodService.getProductById(id);
		if(product!=null) {
			product.setActive(false);
			product=prodService.addProduct(product);
			if(product!=null) {
				return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity<Product>(product,HttpStatus.BAD_REQUEST);		
	}
	
	@PostMapping("/activateProduct/{id}")
	public ResponseEntity<Product> activateProduct(@PathVariable int id){
		Product product=prodService.getProductById(id);
		if(product!=null) {
			product.setActive(true);
			product=prodService.addProduct(product);
			if(product!=null) {
				return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity<Product>(product,HttpStatus.BAD_REQUEST);		
	}
	
//	@PostMapping("/editproduct")
//	public ResponseEntity<Product> editProduct(Product editProd){
//		Product product=prodService.getProductById(editProd.getProductId());
//		if(product!=null) {
//			product=prodService.addProduct(editProd);
//			if(product!=null) {
//				return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
//			}
//		}
//		return new ResponseEntity<Product>(product,HttpStatus.BAD_REQUEST);		
//	}
//	
	@GetMapping("/getproducts")
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product> productsList= prodService.getAllProducts();
			return new ResponseEntity<List<Product>>(productsList,HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/getproduct/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable int id){
		
		Optional<Product> product= prodService.findById(id);
		if(product.isPresent())
			return new ResponseEntity(product,HttpStatus.ACCEPTED);
		else {
			return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
		}		
	}
	
	@GetMapping("/getOrders/{id}")
	public ResponseEntity<List<Orders>> getOrdersByUserId(@PathVariable int id){
		User user=userService.getUserById(id);
		List<Invoice> invoiceList=invoiceService.getInvoiceByUser(user);
		List<Orders> op=new ArrayList<>();
		for(Invoice inv:invoiceList) {
			
			List<Order> orderList=orderService.getOrders(inv);
			orderList.forEach(x->x.setInvoice(null));
			Transaction trans=transactionService.getTransaction(inv.getInvoiceId());
			
			Orders orders=new Orders();
			if(inv.isCancellationStatus()) {
				orders.setStatus(false);
			}else {
				orders.setStatus(true);
			}
			orders.setOrderedDate(trans.getTransactionDate());
			orders.setAmount((float)inv.getAmount());
			orders.setInvoiceId(inv.getInvoiceId());
//			orders.setPaymentStatus(false);
			orders.setProductList(orderList);
			op.add(orders);
			orderList.forEach(o->System.out.println(o.getOrderId()));
			
			
		}
		return new ResponseEntity<List<Orders>>(op,HttpStatus.ACCEPTED);
	}
}
