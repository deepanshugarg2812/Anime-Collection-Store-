package com.cart.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cart.userservice.User;

//contains the list of products for a user
@Entity
@Table(name = "Cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//id for the user for which this cart belongs
	@OneToOne(targetEntity = User.class)
	private User user;
	
	//list of products and there count
	@OneToMany
	@JoinColumn(name = "cid")
	private List<ProductNode> products;
	
	//total values
	private double totalValue;
	
	public Cart() {}

	public Cart(Long id, User user, List<ProductNode> products, double totalValue) {
		super();
		this.id = id;
		this.user = user;
		this.products = products;
		this.totalValue = totalValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ProductNode> getProducts() {
		return products;
	}

	public void setProducts(List<ProductNode> products) {
		this.products = products;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", user=" + user + ", products=" + products + ", totalValue=" + totalValue + "]";
	}
	
	
}
