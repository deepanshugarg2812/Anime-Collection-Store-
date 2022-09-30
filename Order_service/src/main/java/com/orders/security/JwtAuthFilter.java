package com.orders.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.orders.jwtprovider.JwtTokenProvider;
import com.orders.userservice.UserService;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	@Autowired
	UserService userService;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//check for the username and token presence
		String username = request.getHeader("username");
		String token = request.getHeader("Authorization");
		
		//not authorized
		if(username==null || username.length()==0 || token==null || token.length()==0 || token.startsWith("Bearer ")==false) {
			filterChain.doFilter(request, response);
		}
		
		//extract the userdetails and valdate the token
		UserDetails user = userService.loadUserByUsername(username);
		try {
			if(jwtTokenProvider.validateToken(token.split(" ")[1].trim(), user)==false) {
				filterChain.doFilter(request, response);
			}
		}catch(Exception e) {
			filterChain.doFilter(request, response);
		}
		
		UsernamePasswordAuthenticationToken
        authentication = new UsernamePasswordAuthenticationToken(
        		user, null,
        		user == null?new ArrayList<>():user.getAuthorities()
        );
		
		authentication.setDetails(
	            new WebAuthenticationDetailsSource().buildDetails(request)
	        );
	        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
	}

}
