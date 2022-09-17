package com.products.controllers;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
	
	/**
	 * 
	 * @param product
	 * @param image
	 * @return
	 */
	@PostMapping(path = "/add",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Product> addProduct(@RequestPart(required = true , name = "product") String product,@RequestPart(required = false,name = "image") MultipartFile image){
		ResponseEntity<Product> response = null;
		Product prod = null;
		
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
	public ResponseEntity<List<Product>> deleteProduct(@RequestParam(required = true,name="id")Long id){
		ResponseEntity<List<Product>> response = null;
		
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
	@PostMapping(path = "/update")
	public ResponseEntity<Product> updateProduct(@RequestPart(required = true , name = "product") String product,@RequestPart(required = false,name = "image") MultipartFile image){
		ResponseEntity<Product> response = null;
		Product prod = null;
		
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
