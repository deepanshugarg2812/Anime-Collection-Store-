package com.products.authfilters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.products.security.JwtTokenProvider;


@Component
public class JwtAuthenticateFilter extends OncePerRequestFilter{
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header==null || header.length()==0 || !header.startsWith("Bearer ")) {
        	chain.doFilter(request, response);
            return;
        }
        

        // Get jwt token and validate
        final String username = request.getHeader("username");
        final String token = header.split(" ")[1].trim();
        UserDetails user = null;
        try {
        	user = userDetailsService.loadUserByUsername(username);
        	
        	if(jwtTokenProvider.validateToken(token, user)==false) {
        		chain.doFilter(request, response);
                return;
        	}
        	
        }catch(Exception e) {
        	chain.doFilter(request, response);
            return;
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
        chain.doFilter(request, response);
	}

}
