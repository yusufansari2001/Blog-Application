package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto);

	PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);
	
	PostDto getPostById(long id);

	PostDto updatePostById(PostDto postDto, long id);

	void deletePostById(long id);

	void deletePosts();
	
	List<PostDto> getPostsBycategory(Long id);
	

}
