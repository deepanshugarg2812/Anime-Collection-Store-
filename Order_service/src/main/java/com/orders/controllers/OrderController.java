package com.orders.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orders.entities.Order;
import com.orders.service.OrderService;

@RestController
@RequestMapping(path="/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	//place the order
	//save it in table by removing from cart
	@GetMapping(path = "/place")
	public ResponseEntity<Order> placeOrder(@RequestHeader(name = "username")String username){
		//get the cart from the cart service for this user and place the order
		return null;
	}
	
	//get order details by user
	@GetMapping(path = "/get")
	public ResponseEntity<List<Order>> getOrder(@RequestHeader(name = "username")String username){
		//get the cart from the cart service for this user and place the order
		return null;
	}
	
	//get order details by orderid
	@GetMapping(path = "/getdetail")
	public ResponseEntity<Order> getOrderById(@RequestHeader(name = "username")String username,@RequestParam(name="orderId")Long orderId){
		//get the cart from the cart service for this user and place the order
		return null;
	}
	
}
