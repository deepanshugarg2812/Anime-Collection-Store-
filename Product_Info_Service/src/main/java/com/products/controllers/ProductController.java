package com.products.controllers;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.products.entities.Product;
import com.products.service.ProductService;

/**
 * 
 * @author Deepanshu garg
 * @version 1.0
 * @since 17-sep-2022
 *	
 */

@RestController
@RequestMapping(path = "/product")
public class ProductController {
	@Autowired
	Validator validator;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	RestTemplate restTemplate;
	
	/**
	 * Method to validate the jwt token from Authentication_service
	 *
	 */
	public boolean isValidUser(String username,String token) throws RestClientException{
		//set the content type as MultiPart Form Media
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("token", token);
		body.add("username",username);
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);
		
		ResponseEntity<Boolean> res = restTemplate.postForEntity("http://localhost:8081/auth/validate", requestEntity, Boolean.class);
	
		//check the res
		if(res.getStatusCode()==HttpStatus.OK) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param product
	 * @param image
	 * @return
	 */
	@PostMapping(path = "/add",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Product> addProduct(@RequestPart(required = true , name = "product") String product,@RequestPart(required = false,name = "image") MultipartFile image,@RequestPart(required = true,name = "username")String username,@RequestPart(required = true,name = "token")String token){
		ResponseEntity<Product> response = null;
		Product prod = null;
		
		//validate the token
		try {
			if(isValidUser(username, token)==false) {
				response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				return response;
			}
		}catch(RestClientException e) {
			response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			return response;
		}
		catch(Exception e) {
			response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			return response;
		}
		
		try {
			//Create a Product object			
			prod = new ObjectMapper().readValue(product,Product.class);
			prod.setImage(image.getBytes());
			
			//Validate the product data
			Set<ConstraintViolation<Product>> res = validator.validate(prod,Product.class);
			if(res.isEmpty()==false) {
				throw new IllegalArgumentException("Illegal arguments");
			}
		}catch (IllegalArgumentException e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

		//check for issues while adding
		try {
			//add the product using the product service
			prod = productService.addProduct(prod);
			
			response = new ResponseEntity<>(prod,HttpStatus.ACCEPTED);
		}catch (IllegalArgumentException e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		
		return response;
	}
	
	/**
	 * Method fetch all the products from the DB
	 * @return
	 */
	@GetMapping(path = "/all")
	public ResponseEntity<List<Product>> getAllProduct(){
		ResponseEntity<List<Product>> response = null;
		
		try {
			List<Product> products = productService.getAllProduct();
			response = new ResponseEntity<>(products,HttpStatus.ACCEPTED);
		}catch(Exception e) {
			response = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		return response;
	}
	
	/**
	 * Method to delete the product from db
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/delete")
	public ResponseEntity<List<Product>> deleteProduct(@RequestParam(required = true,name="id")Long id,@RequestPart(required = true,name = "username")String username,@RequestPart(required = true,name = "token")String token){
		ResponseEntity<List<Product>> response = null;
		
		//validate the token
		try {
			if(isValidUser(username, token)==false) {
				response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				return response;
			}
		}catch(RestClientException e) {
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			return response;
		}
		catch(Exception e) {
			response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			return response;
		}
		
		try {
			productService.deleteProduct(id);
			List<Product> products = productService.getAllProduct();
			response = new ResponseEntity<>(products,HttpStatus.ACCEPTED);
		}
		catch(IllegalArgumentException e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			response = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		return response;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/find")
	public ResponseEntity<Product> findProductById(@RequestParam(required = true,name="id")Long id){
		ResponseEntity<Product> response = null;
		
		try {
			Product products = productService.getById(id);
			response = new ResponseEntity<>(products,HttpStatus.ACCEPTED);
		}
		catch(IllegalArgumentException e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			response = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		return response;
	}
	
	/**
	 * 
	 * @param product
	 * @param image
	 * @return
	 */
	@PostMapping(path = "/update",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Product> updateProduct(@RequestPart(required = true , name = "product") String product,@RequestPart(required = false,name = "image") MultipartFile image,@RequestPart(required = true,name = "username")String username,@RequestPart(required = true,name = "token")String token){
		ResponseEntity<Product> response = null;
		Product prod = null;
		
		//validate the token
		try {
			if(isValidUser(username, token)==false) {
				response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				return response;
			}
		}catch(RestClientException e) {
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			return response;
		}
		catch(Exception e) {
			response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			return response;
		}
		
		try {
			//Create a Product object			
			prod = new ObjectMapper().readValue(product,Product.class);
			prod.setImage(image.getBytes());
			
			//Validate the product data
			Set<ConstraintViolation<Product>> res = validator.validate(prod,Product.class);
			if(res.isEmpty()==false) {
				throw new IllegalArgumentException("Illegal arguments");
			}
		}catch (IllegalArgumentException e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

		//check for issues while adding
		try {
			//add the product using the product service
			prod = productService.updateProduct(prod);
			
			response = new ResponseEntity<>(prod,HttpStatus.ACCEPTED);
		}catch (IllegalArgumentException e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		
		return response;
	}
}
