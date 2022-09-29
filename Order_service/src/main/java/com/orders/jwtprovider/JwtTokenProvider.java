package com.orders.jwtprovider;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenProvider {
	
	private String SECRET_KEY = "hellothisisthesigningkeyhellothisisthesigningkey";
	
	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
        claims.put("Role", "USER");
        return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis())).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	 public Boolean validateToken(String token, UserDetails userDetails) {
		 if(isTokenExpired(token)==false) {
			 if(userDetails.getUsername().equals(extractUsername(token))==true) {
				 return true;
			 }
			 return false;
		 }
		 return false;
	 }
}
