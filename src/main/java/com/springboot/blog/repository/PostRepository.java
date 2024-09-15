package com.springboot.blog.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entity.Post;

public interface PostRepository extends JpaRepository<Post,Long>{

//	Page<Post> findAll(Pageable pageable);
//
//	//Page<Post> findAll(Pageable pageable);
	List<Post> findByCategoryId(Long categoryId);

}
