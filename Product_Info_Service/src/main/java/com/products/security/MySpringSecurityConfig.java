package com.products.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.products.authfilters.JwtAuthenticateFilter;

@Configuration
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	JwtAuthenticateFilter jwtTokenFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and();

        // Set unauthorized requests exception handler
        http = http
            .exceptionHandling()
            .authenticationEntryPoint(
                (request, response, ex) -> {
                    response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        ex.getMessage()
                    );
                }
            )
            .and();

        // Set permissions on endpoints
        http.authorizeRequests()
            // Our public endpoints
            .antMatchers("/product/add").hasRole("ADMIN")
            .antMatchers("/product/all").permitAll()
            .antMatchers("/product/delete").hasRole("ADMIN")
            .antMatchers("/product/find").permitAll()
            .antMatchers("/product/update").hasRole("ADMIN")
            .anyRequest().authenticated();

        // Add JWT token filter
        http.addFilterBefore(
            jwtTokenFilter,
            UsernamePasswordAuthenticationFilter.class
        );
    }

}
