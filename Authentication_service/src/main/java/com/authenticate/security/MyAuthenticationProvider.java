package com.authenticate.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.authenticate.entities.Roles;
import com.authenticate.entities.User;
import com.authenticate.repository.UserRepository;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider{
	@Autowired
	UserRepository userRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		//get the username and password
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		User user = null;
		//compare the cred after loading from db
		try {
			user = userRepository.findByUserName(username);
			
			if(new BCryptPasswordEncoder().matches(password, user.getPassword())) {
				String role = user.getRole();
				
				return new UsernamePasswordAuthenticationToken(username, password,Arrays.asList(new SimpleGrantedAuthority(role)));
			}
			else {
				throw new BadCredentialsException("Error, bad cred");
			}
		}
		catch(UsernameNotFoundException e) {
			throw new BadCredentialsException("Error, bad cred");
		}
		
	}
	

	@Override
	public boolean supports(Class<?> authentication) {
		if(authentication.equals(UsernamePasswordAuthenticationToken.class)) {
			return true;
		}
		return false;
	}

}
