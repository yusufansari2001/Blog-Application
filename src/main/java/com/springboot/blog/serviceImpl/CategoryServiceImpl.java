package com.springboot.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService{

	
	private CategoryRepository categoryRepository;
	private ModelMapper mapper;
	

	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
		super();
		this.categoryRepository = categoryRepository;
		this.mapper = mapper;
	}






	   @Override
	    public CategoryDto addCategory(CategoryDto categoryDto) {
	        Category category = mapper.map(categoryDto, Category.class);
	        Category savedCategory = categoryRepository.save(category);
	        return mapper.map(savedCategory, CategoryDto.class);
	    }

	    @Override
	    public CategoryDto getCategory(Long categoryId) {

	        Category category = categoryRepository.findById(categoryId)
	                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

	        return mapper.map(category, CategoryDto.class);
	    }






		@Override
		public List<CategoryDto> getCategories() {
			// TODO Auto-generated method stub
			List<Category>categories=categoryRepository.findAll();
			
			return categories.stream().map((category)->mapper.map(category,CategoryDto.class))
					.collect(Collectors.toList());
		}






		@Override
		public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
			// TODO Auto-generated method stub
			Category category=categoryRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
			
			category.setDescription(categoryDto.getDescription());
			category.setName(categoryDto.getName());
			
			Category savedCategory=categoryRepository.save(category);
			return mapper.map(savedCategory, CategoryDto.class);
		}






		@Override
		public String deleteCategory(Long id) {
			// TODO Auto-generated method stub
			Category category=categoryRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
			
			categoryRepository.delete(category);
			
			return "Category deleted successfully";
		}









}
