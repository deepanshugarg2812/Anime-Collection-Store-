package com.authenticate.entities;

import java.util.Arrays;
import java.util.Collection;

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


@Entity
@Table(name = "User")
public class User implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 6)
	@Column(nullable = false,name = "User_Name",unique = true)
	private String userName;
	
	@Size(min = 6)
	@Column(nullable = false,name = "Name")
	private String name;
	
	@Size(min = 6)
	@Column(nullable = false,name = "Password")
	private String password;
	
	@Email
	@Column(nullable = false,name = "Email",unique = true)
	private String email;
	
	private String role;
	
	
	public User() {}
	
	
	public User( @Size(min = 6) String userName, @Size(min = 6) String name, @Size(min = 6) String password,
			@Email String email) {
		super();
		this.userName = userName;
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public User(Long id, @Size(min = 6) String userName, @Size(min = 6) String name, @Size(min = 6) String password,
			@Email String email) {
		super();
		this.id = id;
		this.userName = userName;
		this.name = name;
		this.password = password;
		this.email = email;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", name=" + name + ", password=" + password + ", email="
				+ email + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
