package com.reviews.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reviews.security.entities.User;

@Entity
@Table(name = "Review")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Comment" , nullable = false)
	private String comment;
	
	@Column(name = "Stars" , nullable = false)
	private int stars;
	
	@OneToOne(targetEntity = User.class)
	private User user;
	
	@ManyToOne(targetEntity = Product.class)
	private Product product;
	
	
	public Review() {} 

	public Review(Long id, String comment, int stars, User user) {
		super();
		this.id = id;
		this.comment = comment;
		this.stars = stars;
		this.user = user;
	}
	
	public Review( String comment, int stars, User user) {
		super();
		this.comment = comment;
		this.stars = stars;
		this.user = user;
	}
	
	public Review( String comment, int stars) {
		super();
		this.comment = comment;
		this.stars = stars;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonIgnore
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
