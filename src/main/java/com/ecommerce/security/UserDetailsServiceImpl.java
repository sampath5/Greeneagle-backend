package com.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.entity.User;
//import com.ecommerce.exception.UsernameNotFoundException;
import com.ecommerce.repository.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	UserRepo userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws com.ecommerce.exception.UsernameNotFoundException{
		System.out.println(email);
		User user = userRepository.findUserByEmail(email);
		System.out.println("userDetailsServiceImpl line 21");
		if(user==null) {
			System.out.println("line 26");
			throw new UsernameNotFoundException("Email not found");
		}
		System.out.println(" line 27 "+user);
		return UserDetailsImpl.build(user);
	}

}