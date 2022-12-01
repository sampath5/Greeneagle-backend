package com.ecommerce.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartRepo;

@Component
public class CartDao {

	@Autowired
	CartRepo cartRepo;
	
	public Cart addToCart(Cart cart) {
		return cartRepo.save(cart);
	}
	
	public List<Cart> getCartProducts(User user){
		return cartRepo.findAllByUserAndVisibleTrue(user);
	}
	
	public Optional<Cart> getCartById(int id) {
		return cartRepo.findById(id);
	}
	
	public Cart updateCart(Cart cart) {
		return cartRepo.save(cart);
	}
	public void removeFromCart(Cart cart) {
		cartRepo.save(cart);
//		cartRepo.delete(cart);
	}

	public Optional<Cart> getCartByUserIdAndProductId(User user, Product product) {
		return cartRepo.findByUser_UserIdAndProd_ProductId(user.getUserId(),product.getProductId());
	}
	
	public int getCartCountByUserIdAndProductId(User user, Product product) {
		return cartRepo.getCountByUser_UserIdAndProd_ProductId(user,product);
	}
	
	public List<Cart> getByUserAndProduct(User user,Product product){
		return cartRepo.getByUser_UserIdAndProd_ProductId(user.getUserId(), product.getProductId());
	}
	
}

