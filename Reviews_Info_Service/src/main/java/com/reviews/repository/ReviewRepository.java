package com.reviews.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.reviews.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long>{
	@Query("select r from Review r where user_id = ?1")
	public List<Review> findByUserId(Long id);
}
