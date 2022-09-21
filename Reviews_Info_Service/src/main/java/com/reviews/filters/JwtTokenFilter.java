package com.reviews.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.reviews.security.entities.User;
import com.reviews.securityClasses.JwtTokenProvider;

@Component
public class JwtTokenFilter extends OncePerRequestFilter{
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//get the Headers
		String authHeader = request.getHeader("Authorization");
		String username = request.getHeader("username");
		
		
		//if any of header not present forward the request 
		if(authHeader==null || username==null || authHeader.length()<7 || authHeader.startsWith("Bearer ")==false) {
			filterChain.doFilter(request, response);
			return;
		}
		
		//get the token
		String token = authHeader.substring(7);
		UserDetails user = null;
		try {
			user = userDetailsService.loadUserByUsername(username);
			
			if(user==null) {
				filterChain.doFilter(request, response);
				return;
			}
			
			//validate the token
			if(jwtTokenProvider.validateToken(token, user)==false) {
				filterChain.doFilter(request, response);
				return;
			}
		}catch(Exception e) {
			filterChain.doFilter(request, response);
			return;
		}
		
		//set the details 
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(),null,user.getAuthorities());
		auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(auth);
	
		filterChain.doFilter(request, response);
		return;
	}

}
