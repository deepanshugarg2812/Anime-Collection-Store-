package com.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.cart.entity.Cart;

@Repository
@EnableJpaRepositories
public interface CartRepository extends JpaRepository<Cart, Long>{

}
