package com.orders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.orders.entities.Order;


@Repository
@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Order,Long>{

}
