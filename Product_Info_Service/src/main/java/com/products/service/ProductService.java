package com.products.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.products.entities.Product;

/**
 * 
 * @author Deepanshu garg
 * @version 1.0
 * @since 17-sep-2022
 * 
 * <h2>Interface define the Product Service.</h2>
 */
@Service
public interface ProductService {
	Product addProduct(Product product) throws IllegalArgumentException; 
	List<Product> getAllProduct() throws Exception;
	boolean deleteProduct(Long id) throws IllegalArgumentException;
	Product updateProduct(Product product) throws IllegalArgumentException;
	Product getById(Long id) throws IllegalArgumentException;
}
