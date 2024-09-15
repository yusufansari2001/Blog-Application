package com.springboot.blog.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.springboot.blog.exception.BlogApiException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}")
	private String JwtSecret;
	
	@Value("${app-jwt-expiration-milliseconds}")
	private long JwtExpirationDate;
	
	
	//generate token
	//authentication object will have user details, because we set the authentication 
	//object in security context after authentication
	public String generateToken(Authentication authentication) {
		
		String username=authentication.getName();
		
		Date currentDate=new Date();
		
		Date expiration=new Date(currentDate.getTime()+JwtExpirationDate);
		
		String token=Jwts.builder()
				.subject(username)
				.issuedAt(new Date())
				.expiration(expiration)
				.signWith(key())
				.compact();
		
		return token;
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JwtSecret));
	}
	
	//get name from token
	public String getUsername(String token) {
		return Jwts.parser()
		 .verifyWith((SecretKey) key())
		 .build()
		 .parseSignedClaims(token)
		 .getPayload()
		 .getSubject();
	}
	
	//validate jwt token
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
			 .verifyWith((SecretKey) key())
			 .build()
			 .parse(token);
			
			return true;
			
		}catch(MalformedJwtException  malformedJwtException) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"invalid jwt token");
		}catch(ExpiredJwtException expiredJwtException) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"expired jwt token");
		}catch(UnsupportedJwtException UnsupportedJwtException) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"unsupported jwt token");
		}catch(IllegalArgumentException illegalArgumentException) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"token cannot be null or empty");
		}
	}

}
