package com.authenticate.controllers;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.authenticate.entities.User;
import com.authenticate.security.JwtTokenProvider;
import com.authenticate.service.MyUserDetailService;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	MyUserDetailService myUserDetailService;
	
	@Autowired
	Validator validator;
	
	@PostMapping(path = "/token",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String> getToken(@RequestPart(required = true,name = "username") String username,@RequestPart(required = true,name = "password") String password){
		//create a reponse object
		ResponseEntity<String> response = null;
		
		//authenticate the use first
		try {
			Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
			if(auth.isAuthenticated()==false) {
				throw new UsernameNotFoundException("Error , user not found");
			}
			
			//generate the token 
			try {
				String token = jwtTokenProvider.generateToken(myUserDetailService.loadUserByUsername(username));
				response = new ResponseEntity<>(token,HttpStatus.ACCEPTED);
			}catch(Exception e) {
				System.out.println(e.getMessage());
				response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch(BadCredentialsException e) {
			response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			response = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		
		
		return response;
	}
	
	
	@PostMapping(path = "/register")
	public ResponseEntity<User> registerUser(@RequestBody @Valid User user){
		//create a reponse object
		ResponseEntity<User> response = null;
		
		//validate the data
		try {
			
			//save to db
			try {
				User res = myUserDetailService.saveUser(user);
				response = new ResponseEntity<>(res,HttpStatus.ACCEPTED);
			}
			catch(Exception e) {
				response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
				
		return response;
	}
	
	@GetMapping(path = "/validate",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Boolean> verifyToken(@RequestPart(required = true,name = "username")String username,@RequestPart(required = true,name = "token")String token){
		//create a reponse object
		ResponseEntity<Boolean> response = null;
		
		try {
			User user = myUserDetailService.loadUserByUsername(username);
			if(user==null) {
				response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			else {
				boolean isValid = jwtTokenProvider.validateToken(token, user);
				
				if(isValid==false) {
					response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
				else {
					response = new ResponseEntity<>(HttpStatus.OK);
				}
			}
		}
		catch(UsernameNotFoundException e) {
			response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return response;
	}
}
