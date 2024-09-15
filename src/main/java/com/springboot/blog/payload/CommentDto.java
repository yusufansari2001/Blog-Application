package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CommentDto {
	private long id;
    
	// name should not be null  or empty
    // name should have at least 2 characters
	@NotEmpty
	@Size(min=2,message="the minimum size f name should be 2")
    private String name;
	
	// title should not be null  or empty
    // title should have at least 2 characters
	@NotEmpty(message="email should not be empty")
	@Email
    private String email;
	
	@NotEmpty
	@Size(min=10,message="the minimum size f body should be 2")
    private String body;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public CommentDto() {
		
	}
	public CommentDto(long id, String name, String email, String body) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.body = body;
	}
//	@Override
//	public String toString() {
//		return "CommentDto [id=" + id + ", name=" + name + ", email=" + email + ", body=" + body + "]";
//	}
//	
}
