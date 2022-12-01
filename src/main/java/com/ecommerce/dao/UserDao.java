package com.ecommerce.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;
import com.ecommerce.repository.AddressRepo;
import com.ecommerce.repository.UserRepo;

@Component
public class UserDao {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	AddressRepo addrRepo;
	
	public User createUser(User user) {
		return  userRepo.save(user);
	}
	
	public User getUserByEmail(String email) {
//		System.out.println(userRepo.findByEmail(email));
		return userRepo.findUserByEmail(email);
	}
	
	public User getUserById(int userId) {
		return  userRepo.getById(userId);
	}
	
	public void saveAddress(Address add) {
		addrRepo.save(add);
	}
	public boolean isExistsByEmail(String email) {
		return userRepo.existsByEmail(email);
	}
	public List<User> getAllUsers(){
		return userRepo.findAll();
	}
	public void save(User user) {
		userRepo.save(user);
	}
}
