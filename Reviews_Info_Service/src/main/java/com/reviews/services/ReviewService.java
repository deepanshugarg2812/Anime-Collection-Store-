package com.reviews.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reviews.entities.Review;

@Service
public interface ReviewService {
	//to get all reviews review
	public List<Review> getAllReviewsForProduct(String productId);
	
	//get a review by username
	public List<Review> getAllReviewsForUser(String username);
	
	//add a review for a product 
	public Review addReview(Review review,String username);
	
	//delete a review by username and id
	public boolean deleteReview(Long id,String username) throws Exception;
}
