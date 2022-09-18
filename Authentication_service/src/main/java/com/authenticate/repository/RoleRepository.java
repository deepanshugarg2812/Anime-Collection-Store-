package com.authenticate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.authenticate.entities.Roles;
import com.authenticate.entities.User;

@Repository
@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<Roles,Long>{
	
}
