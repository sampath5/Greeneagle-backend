package com.ecommerce.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.Address;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Invoice;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Transaction;
import com.ecommerce.entity.User;
import com.ecommerce.entity.WishList;
import com.ecommerce.enums.Roles;
import com.ecommerce.exception.OrderOutOfStockException;
import com.ecommerce.exception.UsernameNotFoundException;
import com.ecommerce.jwt.JwtUtils;
import com.ecommerce.model.CancelOrder;
import com.ecommerce.model.LoginModel;
import com.ecommerce.model.LoginResponse;
import com.ecommerce.model.OrderConfirmation;
import com.ecommerce.model.Orders;
import com.ecommerce.model.PaypalLink;
import com.ecommerce.model.ResponseModel;
import com.ecommerce.model.UpdateUserDetails;
import com.ecommerce.model.UpdateUserDetailsResponse;
import com.ecommerce.model.UserSignupModel;
import com.ecommerce.security.UserDetailsImpl;
import com.ecommerce.security.UserDetailsServiceImpl;
import com.ecommerce.service.CartService;
import com.ecommerce.service.InvoiceService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.PaypalService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.TransactionService;
import com.ecommerce.service.UserService;
import com.ecommerce.service.WishlistService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	ProductService prodService;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	PasswordEncoder pwdEncoder;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	WishlistService wlService;
	
	@Autowired
	PaypalService paypalService;
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	OrderService orderService;
	
	Roles roles;
	
	public static final String SUCCESS_URL = "paypal/success";
	public static final String CANCEL_URL = "pay/cancel";
	

	@PostMapping("/signup")
	public ResponseEntity<ResponseModel> signup(@Valid @RequestBody UserSignupModel userModel, Errors result) {
		ResponseModel resp = new ResponseModel();
		if (result.hasErrors()) {
			resp.setError("Bad request");
			resp.setMessage(result.getFieldError().getDefaultMessage());
			return new ResponseEntity<ResponseModel>(resp, HttpStatus.BAD_REQUEST);
		}
		User user = new User();
		if (userService.isExistsByEmail(userModel.getEmail())) {
			resp.setError("User Email already existing!");
			return new ResponseEntity<ResponseModel>(resp, HttpStatus.BAD_REQUEST);
		}

		user.setEmail(userModel.getEmail());
		user.setFirstName(userModel.getFirstName());
		user.setLastName(userModel.getLastName());
		user.setPassword(pwdEncoder.encode(userModel.getPassword()));
		user.setPhoneno(userModel.getPhone());
		user.setRole(roles.USER);

		user = userService.createUser(user);

		Address address = new Address();
		address.setAptNo(userModel.getAptNo());
		address.setCity(userModel.getCity());
		address.setState(userModel.getState());
		address.setZip(userModel.getZipcode());
		address.setStreet(userModel.getStreet());
		address.setUser(user);
		userService.saveAddress(address);
		resp.setMessage("User successfully registered!");

		return new ResponseEntity<ResponseModel>(resp, HttpStatus.ACCEPTED);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginModel loginDetails, Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LoginResponse(
					error.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null, null, null));
		}
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDetails.getEmail(), loginDetails.getPassword()));
		} catch (UsernameNotFoundException e) {
			System.out.println("line 113");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new LoginResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null, null, null));
		} catch (BadCredentialsException e) {
			System.out.println("line 117 " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new LoginResponse("Invalid credentials", HttpStatus.BAD_REQUEST, null, null, null));
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		String role = roles.get(0);

		return ResponseEntity.ok(new LoginResponse(null, HttpStatus.ACCEPTED, jwt, userDetails.getUsername(), role));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String errorFieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(errorFieldName, errorMessage);
		});
		return errors;
	}

	@PutMapping("/updatedetails")
	public ResponseEntity<UpdateUserDetailsResponse> updateUserDetails(
			@Valid @RequestBody UpdateUserDetails userDetails, Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new UpdateUserDetailsResponse(null, error.getFieldError().getDefaultMessage()));
		}

		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user = userService.getUserByEmail(email);

		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setPhoneno(userDetails.getPhone());
		userService.save(user);
		return ResponseEntity.ok(new UpdateUserDetailsResponse(userDetails, null));
	}

	@GetMapping("/getuserdetails")
	public ResponseEntity<UpdateUserDetails> getUserDetails() {
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user = userService.getUserByEmail(email);
		UpdateUserDetails userDetails = new UpdateUserDetails();
		userDetails.setFirstName(user.getFirstName());
		userDetails.setLastName(user.getLastName());
		userDetails.setPhone(user.getPhoneno());

		return ResponseEntity.ok(userDetails);
	}

	@GetMapping("/admin/getusers")
	public ResponseEntity<List<com.ecommerce.model.User>> getAllUsers() {
		List<User> usersList = userService.getAllUsers();
		List<com.ecommerce.model.User> adminSideUsersList = new LinkedList<com.ecommerce.model.User>();
		for (User u : usersList) {
			if (u.getRole() == roles.USER) {
				com.ecommerce.model.User user = new com.ecommerce.model.User(u.getUserId(), u.getEmail(),
						u.getFirstName() + " " + u.getLastName(), false);
				adminSideUsersList.add(user);
			}
		}
		return ResponseEntity.ok(adminSideUsersList);
	}

	@GetMapping("/")
	public ResponseEntity<List<Product>> getListofProducts() {
		List<Product> productsList = prodService.getActiveProducts();
		return new ResponseEntity(productsList,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProductByIdAndActive(@PathVariable int id){
		Product product=prodService.getProductByIdAndIsActive(id, true);
		System.out.println(product);
		if(product==null) {
			return new ResponseEntity<Product>(product,HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
		}
	}
	@PostMapping("/addToCart/{id}")
	public ResponseEntity<ResponseModel> addToCart(@PathVariable int id){
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user=userService.getUserByEmail(email);
		Product product=prodService.getProductByIdAndIsActive(id, true);
		Optional<Cart> optionalCart=cartService.getCartByUserIdAndProductId(user,product);
		Cart cart;
		if(optionalCart.get().getQuantity()>0) {
			cart=optionalCart.get();
			cart.setQuantity(optionalCart.get().getQuantity()+1);
		}else{
			cart=new Cart();
			cart.setProd(product);
			cart.setUser(user);
			cart.setQuantity(1);
			cart.setVisible(true);
			System.out.println(cart.getUser().getEmail());			
		}
		Cart c=cartService.addToCart(cart);
		if(c!=null)
			return new ResponseEntity<ResponseModel>(new ResponseModel("Added to the cart",""),HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<ResponseModel>(new ResponseModel("","Unable to Add to the cart"),HttpStatus.BAD_REQUEST);
			
	}
	
	@PostMapping("/cart/getAll")
	public ResponseEntity<List<Cart>> getCartByUser(){
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user=userService.getUserByEmail(email);
		List<Cart> cartList=cartService.getAllCartByUser(user);
		cartList.forEach(c->c.setUser(null));
//		cartList.forEach(c->c.getProd().getProductId())
		return new ResponseEntity<List<Cart>>(cartList,HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/cart/inc/{id}")
	public ResponseEntity increaseQuantityInCart(@PathVariable int id){
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user=userService.getUserByEmail(email);
		Cart cart;
		Product product=prodService.getProductByIdAndIsActive(id, true);
		List<Cart> listCart=cartService.getAllCartByUser(user);
		if(listCart.size()<=0) {
			cart=new Cart();
			cart.setProd(product);
			cart.setUser(user);
			cart.setQuantity(1);
			cart.setVisible(true);
		}else {
			listCart=cartService.getByUserAndProduct(user, product);
			if(listCart.size()<=0) {
				cart=new Cart();
				cart.setProd(product);
				cart.setUser(user);
				cart.setQuantity(1);
				cart.setVisible(true);
			}else {
				Optional<Cart> optionalCart= cartService.getCartByUserIdAndProductId(user,product);
				cart=optionalCart.get();
				cart.setQuantity(cart.getQuantity()+1);
			}
		}
		
		Cart c=cartService.addToCart(cart);
		if(c!=null)
			return new ResponseEntity<ResponseModel>(new ResponseModel("Added to the cart",""),HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<ResponseModel>(new ResponseModel("","Unable to Add to the cart"),HttpStatus.BAD_REQUEST);
//	
//		List<Cart> carts=cartService.getAllCartByUser(user);
//		Cart cart;
//		if(carts.size()>0) {
//			Optional<Cart> optionalCart= cartService.getCartByUserIdAndProductId(user,product);
//			
//			if(optionalCart.isPresent()) {
//				cart=optionalCart.get();
//				cart.setQuantity(cart.getQuantity()+1);
//				cartService.updateCart(cart);
//			}else {
//				cart=new Cart();
//				cart.setProd(product);
//				cart.setUser(user);
//				cart.setQuantity(1);
//				cart.setVisible(true);
//				System.out.println(cart.getUser().getEmail());
//				
//			}
//			Cart c=cartService.addToCart(cart);
//				if(c!=null)
//					return new ResponseEntity<ResponseModel>(new ResponseModel("Added to the cart",""),HttpStatus.ACCEPTED);
//				else
//					return new ResponseEntity<ResponseModel>(new ResponseModel("","Unable to Add to the cart"),HttpStatus.BAD_REQUEST);
//			
//		}else {
//			cart=new Cart();
//			cart.setProd(product);
//			cart.setUser(user);
//			cart.setQuantity(1);
//			cart.setVisible(true);
//			System.out.println(cart.getUser().getEmail());
//			Cart c=cartService.addToCart(cart);
//			if(c!=null)
//				return new ResponseEntity<ResponseModel>(new ResponseModel("Added to the cart",""),HttpStatus.ACCEPTED);
//			else
//				return new ResponseEntity<ResponseModel>(new ResponseModel("","Unable to Add to the cart"),HttpStatus.BAD_REQUEST);
//		
//		}
//		
	}
	
	@PostMapping("/cart/dec/{id}")
	public ResponseEntity decreaseQuantityInCart(@PathVariable int id){
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user=userService.getUserByEmail(email);
		Optional<Cart> optionalCart= cartService.getCartById(id);
		Cart cart;
		if(optionalCart.isPresent()) {
			cart=optionalCart.get();
			cart.setQuantity(cart.getQuantity()-1);
			cartService.updateCart(cart);
		}		
		
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/cart/remove/{id}")
	public ResponseEntity removeProductInCart(@PathVariable int id) {
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user=userService.getUserByEmail(email);
		Optional<Cart> optionalCart= cartService.getCartById(id);
		Cart cart;
		if(optionalCart.isPresent()) {
			if(optionalCart.get().getQuantity()>0) {
				cart=optionalCart.get();
				cartService.removeProductFromCart(cart);
			}			
		}		
		else {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/addTowishlist/{id}")
	public ResponseEntity addToWishlist(@PathVariable int id){
		WishList w=null;
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user=userService.getUserByEmail(email);
		Product product=prodService.getProductByIdAndIsActive(id, true);
		List<WishList> wishList=wlService.getByUser_UserIdAndProduct_ProductId(user, product);
		if(wishList.size()<=0) {
			WishList wl=new WishList();
			wl.setProduct(product);
			wl.setUser(user);
			wl.setVisible(true);
			
			w=wlService.addToWishList(wl);
		}
		
		if(w!=null)
			return new ResponseEntity<ResponseModel>(new ResponseModel("Added to the wishList",""),HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<ResponseModel>(new ResponseModel("","Unable to Add to the list"),HttpStatus.BAD_REQUEST);
			
	}
	
	@PostMapping("/wishlist/getAll")
	public ResponseEntity<List<WishList>> getWLByUser(){
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user=userService.getUserByEmail(email);
		List<WishList> wList=wlService.getWishlistByUser(user);
		wList.forEach(c->c.setUser(null));
//		cartList.forEach(c->c.getProd().getProductId())
		return new ResponseEntity<List<WishList>>(wList,HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/wishlist/remove/{id}")
	public ResponseEntity removeProductInWishlist(@PathVariable int id) {
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user=userService.getUserByEmail(email);
		WishList wl=wlService.getWishlistById(id);
		
		if(wl!=null) {
			wl.setVisible(false);
			wlService.removeFromWishlist(wl);
		}		
		else {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/pay/{id}")
	public  ResponseEntity<PaypalLink> payment(@PathVariable int id)
	{
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user=userService.getUserByEmail(email);
		List<Cart> cartList=cartService.getAllCartByUser(user);
//		List<Order> orderList=new ArrayList();
		PaypalLink plink=new PaypalLink();
		double sum=0;
		if(cartList!=null) {
			System.out.println(cartList.size());
			Iterator<Cart> cartIterator=cartList.iterator();
			while(cartIterator.hasNext()) {
				
				Cart c=cartIterator.next();
				if(c.getProd().getQuantity() < c.getQuantity()) {
					throw new OrderOutOfStockException(c.getProd().getProductName()+" is out of stock");
				}
			
				sum+=c.getProd().getPrice()*c.getQuantity();
			}
			List<Integer> cartids=cartList.stream().map(x->x.getCartId()).collect(Collectors.toList());
			System.out.println("sum="+sum);
//			orderList.forEach(x->x.getOrderId());
			try {
				Payment payment = paypalService.createPayment(Double.valueOf(sum), "USD", "paypal", "sale",
						cartids.toString(), "http://localhost:8080/" + CANCEL_URL,
						"http://localhost:4200/" + SUCCESS_URL);
				for (Links link : payment.getLinks()) {
					if (link.getRel().equals("approval_url")) {
						System.out.println(link.getHref() + " ***");
						plink.setLink(link.getHref());
//						Invoice invoice=new Invoice();
//						invoice.setAmount(sum);
//						invoice.setUser(user);
//						Invoice inv=invoiceService.saveInvoice(invoice);
//						Transaction trans=new Transaction();
//						trans.setAmount(sum);
//						trans.setInvoice(inv);
//						trans.setPaymentStatus(PaymentStatus.PROCESSING);
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						Date date = null;
						try {
							date = formatter.parse(formatter.format(new Date()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						trans.setTransactionDate(date);
//						trans.setPaymentType(PaymentType.CREDIT_CARD);
//						transactionService.saveTransaction(trans);
//						
//						orderList.forEach(o->{o.setInvoice(inv);
//							orderService.saveOrder(o);
//							});
						return new ResponseEntity<>(plink,HttpStatus.ACCEPTED);
					}
				}

			} catch (PayPalRESTException e) {

				e.printStackTrace();
			}
			
		
		}
		return new ResponseEntity<>(plink,HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = CANCEL_URL)
	public String cancelPay() {
		System.out.println("cancelled **");
		return "cancel";
	}
	
	@PostMapping(value = SUCCESS_URL)
	public boolean successPay(@RequestParam("paymentId") String paymentId, @RequestParam("token") String token, @RequestParam("PayerID") String payerId) {
		System.out.println("---------------------------------------------******");
		System.out.println(paymentId);
		System.out.println("payer is "+payerId);
		String total="";
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			List<com.paypal.api.payments.Transaction> transactionsList=payment.getTransactions();
			String description=null;
			for(com.paypal.api.payments.Transaction transaction:transactionsList) {
				description=transaction.getDescription();
				total=transaction.getAmount().getTotal();
			}
			
			OrderConfirmation orderConfirmation = new OrderConfirmation(paymentId,description);
			orderService.orderConfirmation(orderConfirmation,total);
			System.out.println(payment.toJSON());
			if (payment.getState().equals("approved")) {
				return true;
			}
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	@GetMapping("/getorders")
	public ResponseEntity<List<Orders>> getOrders() {
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user=userService.getUserByEmail(email);
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
	
	@PostMapping("/cancelorder")
	public ResponseEntity<ResponseModel> cancelOrder(@Valid @RequestBody CancelOrder cancelOrder, Errors result){
		int invoiceId=cancelOrder.getInvoiceId();
		Invoice inv=invoiceService.getInvoiceById(invoiceId);
		String email=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		if(inv.getUser().getEmail().equals(email) && !inv.isCancellationStatus()) {
			inv.setCancellationDate(new Date());
			inv.setCancellationReason(cancelOrder.getCancellationReason());
			inv.setCancellationStatus(true);
			inv.setRefundStatus("Initiated");
			inv.setRefundDate(new Date());
			invoiceService.saveInvoice(inv);
			return new ResponseEntity<>(new ResponseModel("Successfully Cancelled the Order",""),HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<>(new ResponseModel("","Bad request"),HttpStatus.BAD_REQUEST);
		}
	}
}
