package com.reviews.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<Review>> getAllReviews(@RequestParam(name = "id",required = true) Long id) {
		return new ResponseEntity<>(reviewService.getAllReviewsForProduct(id),HttpStatus.ACCEPTED);
	}
	
	@PostMapping(path = "/add")
	public ResponseEntity<Review> addReview(@RequestParam(name = "id",required = true) Long id,@RequestBody Review review) {
		ResponseEntity<Review> resp;
		try {
			resp = new ResponseEntity<Review>(reviewService.addReview(id,review, "deepanshu"),HttpStatus.ACCEPTED);
			return resp;
		} catch (Exception e) {
			return new ResponseEntity<Review>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(path = "/delete")
	public ResponseEntity<Void> deleteReview(@RequestParam(name = "id",required = true)Long id) {
		ResponseEntity<Review> resp;
		try {
			reviewService.deleteReview(id, "deepanshu");
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/getbyuser")
	public  ResponseEntity<List<Review>> getAllReviewsByuser(){
		ResponseEntity<List<Review>> resp;
		
		try {
			return new ResponseEntity<List<Review>>(reviewService.getAllReviewsForUser("deepanshu"),HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<List<Review>>(HttpStatus.BAD_REQUEST);
		}
	}
}
