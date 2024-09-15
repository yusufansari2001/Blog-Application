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
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	
	private CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService=categoryService;
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto>createCategory( @RequestBody CategoryDto categoryDto){
		CategoryDto response=categoryService.addCategory(categoryDto);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<CategoryDto>getCategory(@PathVariable Long id){
		
		CategoryDto category=categoryService.getCategory(id);
		 return new ResponseEntity<>(category,HttpStatus.OK);
		
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryDto>>getCategories(){
		return new ResponseEntity<>(categoryService.getCategories(),HttpStatus.OK);
	}
	
	@PutMapping("{id}")
    public ResponseEntity<CategoryDto>updateCategory(@PathVariable Long id,@RequestBody CategoryDto categoryDto){
		
		CategoryDto category=categoryService.updateCategory(id,categoryDto);
		 return new ResponseEntity<>(category,HttpStatus.OK);
		
	}
	
	@DeleteMapping("{id}")
     public ResponseEntity<String>deleteCategory(@PathVariable Long id){
		
		String category=categoryService.deleteCategory(id);
		 return new ResponseEntity<>(category,HttpStatus.OK);
		
	}
}
