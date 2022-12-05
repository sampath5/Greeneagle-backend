package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.UserDao;
import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;

@Service
public class UserService {

	@Autowired
	UserDao userDao;
	
	public User createUser(User user) {
		return userDao.createUser(user);
	}
	
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}
	
	public User getUserById(int userId) {
		return userDao.getUserById(userId);
	}
	
	public void saveAddress(Address add) {
		userDao.saveAddress(add);
	}
	
	public boolean isExistsByEmail(String email) {
		return userDao.isExistsByEmail(email);
	}
	public List<User> getAllUsers(){
		return userDao.getAllUsers();
	}
	public User save(User user) {
		return userDao.save(user);
	}
}
