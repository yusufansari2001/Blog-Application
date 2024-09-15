package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.CategoryDto;

public interface CategoryService {

	CategoryDto addCategory(CategoryDto categoryDto);
	
	CategoryDto getCategory(Long id);
	
	List<CategoryDto> getCategories();
	
	CategoryDto updateCategory(Long id,CategoryDto categoryDto);
	
	String deleteCategory(Long id);
}
