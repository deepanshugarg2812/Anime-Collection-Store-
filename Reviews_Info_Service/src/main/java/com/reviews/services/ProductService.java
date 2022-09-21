package com.reviews.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.reviews.entities.Product;

@Service
public class ProductService {
	@Autowired
	RestTemplate restTemplate;
	
	public Product getProductFromService(Long id) throws Exception{
		ResponseEntity<Product> prod = restTemplate.getForEntity("http://localhost:8082/product/find?id=" + id, Product.class);
	
		if(prod.getStatusCode()==HttpStatus.ACCEPTED) {
			return prod.getBody();
		}
		throw new Exception("Error occured while fetching");
	}
}
