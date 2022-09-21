package com.reviews.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reviews.entities.Review;
import com.reviews.services.ReviewServiceImpl;

@RestController
@RequestMapping(path = "/review")
public class ReviewController {
	@Autowired
	ReviewServiceImpl reviewService;

	@GetMapping(path = "/get")
	public List<Review> getAllReviews() {
		return reviewService.getAllReviewsForProduct(null);
	}
	
	@PostMapping(path = "/add")
	public Review addReview(@RequestBody Review review) {
		return reviewService.addReview(review, "deepanshu");
	}
	
	@DeleteMapping(path = "/delete")
	public void deleteReview(@RequestParam(name = "id")Long id) {
		try {
			reviewService.deleteReview(id, "deepanshu");
		} catch (Exception e) {
			
		}
	}
	
	@GetMapping(path = "/getbyuser")
	public  List<Review> getAllReviewsByuser(){
		return reviewService.getAllReviewsForUser("deepanshu");
	}
}
