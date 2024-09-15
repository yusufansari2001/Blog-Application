package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException {
       private HttpStatus status;
       private String message;
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public BlogApiException(HttpStatus badRequest, String message) {
		super();
		this.status = badRequest;
		this.message = message;
	}
    
}
