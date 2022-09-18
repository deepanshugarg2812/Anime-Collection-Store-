package com.authenticate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.authenticate.entities.User;
import com.authenticate.repository.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService{

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
			User res = userRepository.save(user);
			return res;
		}
		catch(Exception e) {
			throw new Exception("Error occured");
		}
	}
}
