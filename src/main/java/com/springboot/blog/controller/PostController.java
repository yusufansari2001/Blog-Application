package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD REST APIs for Post Resource"
)
public class PostController {
	
	private PostService postService;
	
	public PostController(PostService postService) {
		this.postService=postService;
	}
	// create blog post rest api
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	@Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
	//swagger authentication
	@SecurityRequirement(
            name = "Bear Authentication"
    )
	public ResponseEntity<PostDto>createPost(@Valid @RequestBody PostDto postDto){
		return new ResponseEntity<>(postService.createPost(postDto),HttpStatus.CREATED);
		
	}
	
	@Operation(
	            summary = "Get All Posts REST API",
	            description = "Get All Posts REST API is used to fetch all the posts from the database"
	)
	@ApiResponse(
	            responseCode = "200",
	            description = "Http Status 200 SUCCESS"
	)
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
	         ){
		return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
	}
	 
	@Operation(
	            summary = "Get Post By Id REST API",
	            description = "Get Post By Id REST API is used to get single post from the database"
	)
	@ApiResponse(
	            responseCode = "200",
	            description = "Http Status 200 SUCCESS"
	)
	@GetMapping("/{id}")
	public PostDto getPostById(@PathVariable(name = "id") long id) {
		PostDto postDto=postService.getPostById(id);
		return postDto;
		
	}
	
	@Operation(
	            summary = "update Post REST API",
	            description = "Update Post REST API is used to update a particular post in the database"
	)
	@ApiResponse(
	            responseCode = "200",
	            description = "Http Status 200 SUCCESS"
	)
	@SecurityRequirement(
	            name = "Bear Authentication"
	)
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto>updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable(name="id") long id){
		PostDto post1=postService.updatePostById(postDto,id);
		return new ResponseEntity<>(post1,HttpStatus.OK);
	}
	 
	@Operation(
	       summary = "Delete Post REST API",
	       description = "Delete Post REST API is used to delete a particular post from the database"
	)
	@ApiResponse(
	       responseCode = "200",
	       description = "Http Status 200 SUCCESS"
	)
	// delete post rest api
	@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){

        postService.deletePostById(id);

        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
    
	 
    @DeleteMapping
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    public ResponseEntity<String>deletePosts(){
    	postService.deletePosts();
    	return new ResponseEntity<>("All posts deleted",HttpStatus.OK);
    }
    
    
    @GetMapping("categories/{id}")
    public ResponseEntity<List<PostDto>>getPostsByCategory(@PathVariable(name = "id") Long id){
    	return new ResponseEntity<>(postService.getPostsBycategory(id), HttpStatus.OK);
    }

}
