package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.CartDao;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;

@Service
public class CartService {

	@Autowired
	CartDao cartDao;
	
	public Cart addToCart(Cart cart) {
		return cartDao.addToCart(cart);
	}
	
	public List<Cart> getAllCartByUser(User user){
		return cartDao.getCartProducts(user);
	}
	
	public Optional<Cart> getCartById(int id) {
		return cartDao.getCartById(id);
	}
	
	public Cart updateCart(Cart cart) {
		return cartDao.updateCart(cart);
	}
	
	public void removeProductFromCart(Cart cart) {
		cart.setQuantity(0);
		cartDao.removeFromCart(cart);
	}

	public Optional<Cart> getCartByUserIdAndProductId(User user, Product product) {
		return cartDao.getCartByUserIdAndProductId(user,product);
	}
	
	public int getCartCountByUserIdAndProductId(User user, Product product) {
		return cartDao.getCartCountByUserIdAndProductId(user,product);
	}
	
	public List<Cart> getByUserAndProduct(User user, Product product){
		return cartDao.getByUserAndProduct(user, product);
	}
}

