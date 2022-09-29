package com.orders.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.orders.productservice.Product;
import com.orders.userservice.User;

@Entity
@Table(name = "OrderTable")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//indicate which user has bought the product in this order
	@OneToOne(fetch = FetchType.EAGER,targetEntity = User.class)
	private User user;
	
	//List of all the products
	@OneToMany(targetEntity = Product.class)
	private List<Product> products;
	
	@Column
	private double orderTotal;
	
	@Column
	private int totalQuantity;
	
	public Order() {}
	
	public Order(User user, List<Product> products, double orderTotal, int totalQuantity) {
		super();
		this.user = user;
		this.products = products;
		this.orderTotal = orderTotal;
		this.totalQuantity = totalQuantity;
	}

	public Order(Long id, User user, List<Product> products, double orderTotal, int totalQuantity) {
		super();
		this.id = id;
		this.user = user;
		this.products = products;
		this.orderTotal = orderTotal;
		this.totalQuantity = totalQuantity;
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", user=" + user + ", products=" + products + ", orderTotal=" + orderTotal
				+ ", totalQuantity=" + totalQuantity + "]";
	}
}
