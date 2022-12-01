package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.AddressDao;
import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;

@Service
public class AddressService {

	@Autowired
	AddressDao addDao;
	
	public Address addAddress(Address add) {
		return addDao.addAddress(add);
	}
	
	public List<Address> getAllByUser(User user){
		return addDao.getAddressByUser(user);
	}
	
	public Address updateAddress(Address add) {
		return addDao.updateAddress(add);
	}
	
	public Address getAddressById(int id) {
		return addDao.getAddressById(id);
	}
	
	public void removeAddress(int id) {
		addDao.removeAddressById(id);
	}
	
	public int getAddressesCount() {
		return addDao.addressCount();
	}
}
