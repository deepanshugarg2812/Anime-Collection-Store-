package com.cart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.repository.CartRepository;

@Service
public class CartService {
	@Autowired
	CartRepository cartRepository;
	
	
}
