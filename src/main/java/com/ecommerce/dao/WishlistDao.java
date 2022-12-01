package com.ecommerce.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.entity.User;
import com.ecommerce.entity.WishList;
import com.ecommerce.repository.WishlistRepo;

@Component
public class WishlistDao {

	@Autowired
	WishlistRepo wishlistRepo;
	
	public List<WishList> getWishlistByUser(User user) {
		return wishlistRepo.findByUser(user);
	}
	
	public WishList save(WishList wl) {
		return wishlistRepo.save(wl);
	}
	
	public void remove(WishList wl) {
		wl.setProduct(null);
		wl.setUser(null);
		wishlistRepo.save(wl);
//		wishlistRepo.deleteById(wl.getWishlistId());;
	}
	
	public WishList getWishListById(int id) {
		return wishlistRepo.getById(id);
	}

	public List<WishList> getByUser_UserIdAndProduct_ProductId(int userId, int productId) {
		// TODO Auto-generated method stub
		return wishlistRepo.getByUser_UserIdAndProduct_ProductId(userId, productId);
	}
}
