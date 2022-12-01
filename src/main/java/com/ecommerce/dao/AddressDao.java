package com.ecommerce.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;
import com.ecommerce.repository.AddressRepo;

@Component
public class AddressDao {

	@Autowired
	AddressRepo addressRepo;
	
	public Address addAddress(Address add) {
		return addressRepo.save(add);
	}
	
	public List<Address> getAddressByUser(User user){
		return addressRepo.getAllByUser(user);
	}
	
	public Address getAddressById(int id) {
		return addressRepo.getById(id);
	}
	
	public void removeAddressById(int id) {
		 addressRepo.deleteById(id);
	}
	
	public Address updateAddress(Address add) {
		return addressRepo.save(add);
	}
	
	public int addressCount() {
		List<Address> addresses=addressRepo.findAll();
		return addresses.size();
	}
	
}
