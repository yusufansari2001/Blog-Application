package com.springboot.blog.serviceImpl;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	private PostRepository postRepository;
	private ModelMapper mapper;
	private CategoryRepository categoryRepositoty;
	
	public PostServiceImpl(PostRepository postRepository,ModelMapper mapper,CategoryRepository categoryRepositoty) {
		this.postRepository=postRepository;
		this.mapper=mapper;
		this.categoryRepositoty=categoryRepositoty;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		//convert dto to entity
		Post post=mapToEntity(postDto);
		Category categoy=categoryRepositoty.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		
		post.setCategory(categoy);
		Post newPost=postRepository.save(post);
		
		//convert entity to dto
		PostDto postDto1=mapToDto(newPost);
		return postDto1;
		
	}
	// convert Entity into DTO


	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir) {
		// TODO Auto-generated method stub
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
		PageRequest pageable=PageRequest.of(pageNo, pageSize,sort);
		
		Page<Post>posts=postRepository.findAll(pageable);
		List<Post>result=posts.getContent();
		List<PostDto>posts1=result.stream().map(post->mapToDto(post)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(posts1);
		postResponse.setLast(posts.isLast());
		postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        return postResponse;
		


	}

	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);

		
	}

	@Override
	public PostDto updatePostById(PostDto postDto, long id) {
		// TODO Auto-generated method stub

		Post post=postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		Category categoy=categoryRepositoty.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		
		post.setCategory(categoy);
		post.setDescription(postDto.getDescription());
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		postRepository.save(post);
		
		return mapToDto(post);
	}

	@Override
	public void deletePostById(long id) {
		// TODO Auto-generated method stub
		Post post=postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.deleteById(id);
		
	}

	@Override
	public void deletePosts() {
		// TODO Auto-generated method stub
		postRepository.deleteAll();
		
		
		
	}
	
	private PostDto mapToDto(Post post) {
		PostDto postDto=mapper.map(post, PostDto.class);
//		PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
	}
   // convert dto to entity
	private Post mapToEntity(PostDto postDto) {
		// TODO Auto-generated method stub
		Post post=mapper.map(postDto, Post.class);
//		Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
	}

	@Override
	public List<PostDto> getPostsBycategory(Long id) {
	
		Category categoy=categoryRepositoty.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		
		List<Post>posts=postRepository.findByCategoryId(id);
		return posts.stream().map((post)->mapper.map(post,PostDto.class))
				.collect(Collectors.toList());
	}
	
	 
   

}
