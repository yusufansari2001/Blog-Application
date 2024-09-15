package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto comentDto);
    
    List<CommentDto>getCommentsByPostId(long postId);
    
    CommentDto getCommentByCommentId(long commentId,long postId);
    
    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);

	void deleteComment(Long postId, Long commentId);
}
