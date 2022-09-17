package com.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.products.entities.Product;

/**
 * 
 * @author Deepanshu garg
 * @version 1.0
 * @since 17-sep-2022
 * 
 * <h2>Product Repository is layer to interct with DB</h2>
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
