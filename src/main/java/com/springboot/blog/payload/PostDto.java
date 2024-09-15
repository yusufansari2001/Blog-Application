package com.springboot.blog.payload;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(
      description = "PostDto Model Information"
)
public class PostDto {
	private long id;
	
	@Schema(
            description = "Blog Post Title"
    )
	// title should not be null  or empty
    // title should have at least 2 characters
	@NotEmpty
	@Size(min=2,message="the minimum size f title should be 2")
    private String title;
	
	 @Schema(
	            description = "Blog Post Description"
	    )
	// title should not be null  or empty
    // title should have at least 2 characters
	@NotEmpty
	@Size(min=10,message="the minimum size of description should be 10")
    private String description;
	
	 @Schema(
	            description = "Blog Post Content"
	    )
	@NotEmpty
    private String content;
    private Set<CommentDto>comments;
    
    @Schema(
            description = "Blog Post Category"
    )
    private long categoryId;
    
	public PostDto(long id, String title, String description, String content, Set<CommentDto> comments,long categoryId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
		this.comments = comments;
		this.categoryId=categoryId;
	}
	
	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public Set<CommentDto> getComments() {
		return comments;
	}
	public void setComments(Set<CommentDto> comments) {
		this.comments = comments;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public PostDto() {
		
	}

//	@Override
//	public String toString() {
//		return "PostDto [id=" + id + ", title=" + title + ", description=" + description + ", content=" + content + "]";
//	}
 
    
    
 

}
