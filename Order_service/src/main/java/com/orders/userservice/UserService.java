package com.orders.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import  com.orders.userservice.*;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;

	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		if(user==null) {
			throw new UsernameNotFoundException("Error, user not found");
		}
		return user;
	}
	
	public User saveUser(User user) throws Exception{
		System.out.println(user);
		try {
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			user.setRole("ROLE_USER");
			User res = userRepository.save(user);
			return res;
		}
		catch(Exception e) {
			throw new Exception("Error occured");
		}
	}
	
	public User saveAdmin(User user) throws Exception{
		System.out.println(user);
		try {
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			user.setRole("ROLE_ADMIN");
			User res = userRepository.save(user);
			return res;
		}
		catch(Exception e) {
			throw new Exception("Error occured");
		}
	}
}