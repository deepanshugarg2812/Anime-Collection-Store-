package com.orders.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.orders.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	OrderService orderService;
	
	
}
