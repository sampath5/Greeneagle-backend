package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.User;
import com.ecommerce.entity.WishList;

@Repository
public interface WishlistRepo extends JpaRepository<WishList, Integer>{

	List<WishList> findByUser(User user);
	
	List<WishList> getByUser_UserIdAndProduct_ProductId(int user, int prod);
	
	void deleteById(int id);
}
