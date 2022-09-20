package com.products.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.products.security.entities.User;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,Long>{
	
	@Query("select u from User u where u.username = ?1")
	public UserDetails findByUserName(String userName);
}
