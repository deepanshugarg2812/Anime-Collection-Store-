package com.reviews.security.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "User")
public class User implements UserDetails{
	private static final long SerialVersionUID = 10l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 6)
	@Column(nullable = false,name = "UserName",unique = true)
	private String username;
	
	@Size(min = 6)
	@Column(nullable = false,name = "Name")
	private String name;
	
	@Size(min = 6)
	@Column(nullable = false,name = "Password")
	private String password;
	
	@Email
	@Column(nullable = false,name = "Email",unique = true)
	private String email;
	
	private String role = "";
	
	
	public User() {}
	
	
	public User( @Size(min = 6) String username, @Size(min = 6) String name, @Size(min = 6) String password,
			@Email String email) {
		super();
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public User(Long id, @Size(min = 6) String username, @Size(min = 6) String name, @Size(min = 6) String password,
			@Email String email) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
	}
	
	public User(Long id, @Size(min = 6) String username, @Size(min = 6) String name, @Size(min = 6) String password,
			@Email String email,String role) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
		this.role = role;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

	@JsonIgnore
	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}




	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + username + ", name=" + name + ", password=" + password + ", email="
				+ email + ", role=" + role + "]";
	}


	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if(role!=null && role.length()>0) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
