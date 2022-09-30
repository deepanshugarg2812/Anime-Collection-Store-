package com.orders.userservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.orders.userservice.*;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,Long>{
	
	@Query("select u from User u where u.username = ?1")
	User findByUserName(String userName);
}
