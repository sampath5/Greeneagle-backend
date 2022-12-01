package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {
//	public Address
	public List<Address> getAllByUser(User user);

}
