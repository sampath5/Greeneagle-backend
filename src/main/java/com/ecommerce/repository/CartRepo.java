package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {

	List<Cart> findAllByUserAndVisibleTrue(User user);

	Optional<Cart> findByUser_UserIdAndProd_ProductId(int user, int product);
	
	List<Cart> getByUser_UserIdAndProd_ProductId(int user, int prod);
	
	int getCountByUser_UserIdAndProd_ProductId(User user, Product product);
}
