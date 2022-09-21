package com.reviews.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reviews.entities.Review;
import com.reviews.repository.ReviewRepository;
import com.reviews.security.entities.User;

import io.jsonwebtoken.io.IOException;

@Service
public class ReviewServiceImpl implements ReviewService{
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	public List<Review> getAllReviewsForProduct(String productId) {
		return reviewRepository.findAll();
	}

	@Override
	public List<Review> getAllReviewsForUser(String username) throws UsernameNotFoundException{
		//get the user details
		User user = null;
		try {
			user = (User) userDetailsService.loadUserByUsername(username);
		}catch(UsernameNotFoundException e) {
			throw new UsernameNotFoundException("Error , user not present");
		}
		
		return reviewRepository.findByUserId(user.getId());
	}

	@Override
	public Review addReview(Review review, String username) throws UsernameNotFoundException{
		//get the user details
		User user = null;
		try {
			user = (User) userDetailsService.loadUserByUsername(username);
		}catch(UsernameNotFoundException e) {
			throw new UsernameNotFoundException("Error , user not present");
		}
		review.setUser(user);
		Review res = reviewRepository.save(review);
		res.getUser().setPassword("");
		return res;
	}

	@Override
	public boolean deleteReview(Long id, String username) throws Exception{
		//get the user details
		User user = null;
		try {
			user = (User) userDetailsService.loadUserByUsername(username);
		}catch(UsernameNotFoundException e) {
			throw new UsernameNotFoundException("Error , user not present");
		}
		
		Optional<Review> review = reviewRepository.findById(id);
		if(review.isPresent()==false || review.get().getUser().getId()!=user.getId()) {
			throw new Exception("Forged request");
		}
		
		reviewRepository.deleteById(id);
		return true;
	}

}
