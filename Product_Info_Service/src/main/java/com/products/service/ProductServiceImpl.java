package com.products.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.products.entities.Product;
import com.products.repositories.ProductRepository;

/**
 * 
 * @author Deepanshu garg
 * @version 1.0
 * @since 17-sep-2022
 * 
 * <h2>Class implements the Product Service interface.</h2>
 */

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductRepository productRepository;

	@Override
	public Product addProduct(Product product) throws IllegalArgumentException{
		try {
			Product prod = productRepository.save(product);
			return prod;
		}catch(Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/*
	 * Method is used to fetch all the products from db and throw exception of occured
	 */
	@Override
	public List<Product> getAllProduct() throws Exception{
		try {
			return productRepository.findAll();
		}catch(Exception e) {
			throw new Exception("Error occured while fetching details from the DB");
		}
	}

	@Override
	public boolean deleteProduct(Long id) throws IllegalArgumentException{
		if(productRepository.existsById(id)) {
			productRepository.deleteById(id);
			return true;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Product updateProduct(Product product) throws IllegalArgumentException{
		if(productRepository.existsById(product.getId())) {
			productRepository.save(product);
			return product;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Product getById(Long id) throws IllegalArgumentException{
		if(productRepository.existsById(id)){
			return productRepository.findById(id).get();
		}
		else {
			throw new IllegalArgumentException();
		}
	}

}
