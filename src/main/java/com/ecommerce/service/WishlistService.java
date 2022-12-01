package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.WishlistDao;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.entity.WishList;

@Service
public class WishlistService {

	
	@Autowired
	WishlistDao wDao;
	
	public WishList addToWishList(WishList wl) {
		return wDao.save(wl);
	}
	
	public List<WishList> getWishlistByUser(User user) {
		return wDao.getWishlistByUser(user);
	}
	
	public void removeFromWishlist(WishList wl) {
		wDao.remove(wl);
	}
	
	public WishList getWishlistById(int id) {
		return wDao.getWishListById(id);
	}
	
	public List<WishList> getByUser_UserIdAndProduct_ProductId(User user, Product product){
		return wDao.getByUser_UserIdAndProduct_ProductId(user.getUserId(),product.getProductId());
	}
}
