package com.products.entities;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * 
 * @author Deepanshu garg
 * @version 1.0
 * @since 17-sep-2022
 *
 * <h2>Class declares the product entity which is mapped to the db using the orm framework JPA</h2>
 *	<p>
 *	id -> Define the id for the particular product and is auto-increment based on DB <br>
 *	price -> Define the price of the particular product <br>
 *	name -> Define the name of the product <br>
 *	shortDescription -> Small description about the product <br>
 *	image -> Store the image in byte format in DB (LOB -> For large object store i db either binary or character) <br>
 *	</p>
 */

@Entity
@Table(name = "Product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false,precision = 3,name = "Price")
	@Min(value = 1)
	private double price;
	
	@Column(nullable = false,name = "Name")
	@Size(min = 3)
	private String name;
	
	@Column(nullable = true,name = "Short_Description")
	@Size(min = 10)
	private String shortDescription;
	
	@Lob
	private byte[] image;
	
	public Product() {
		super();
	}

	public Product(double price, String name, String shortDescription, byte[] image) {
		super();
		this.price = price;
		this.name = name;
		this.shortDescription = shortDescription;
		this.image = image;
	}
	
	

	public Product(Long id, @Min(1) double price, @Size(min = 3) String name, @Size(min = 10) String shortDescription,
			byte[] image) {
		super();
		this.id = id;
		this.price = price;
		this.name = name;
		this.shortDescription = shortDescription;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", price=" + price + ", name=" + name + ", shortDescription=" + shortDescription
				+ ", image=" + Arrays.toString(image) + "]";
	}
}
