package com.springboot.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper mapper) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper=mapper;
	}
	
	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		List<Comment> comments=commentRepository.findByPostId(postId);
		
		return comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		// TODO Auto-generated method stub
		Comment comment=mapToEntity(commentDto);
		Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
		comment.setPost(post);
		
		Comment comment1=commentRepository.save(comment);
		return mapToDto(comment1);
	}

	

	@Override
	public CommentDto getCommentByCommentId(long commentId,long postId) {
		// TODO Auto-generated method stub
		Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "id", postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "id", commentId));
        
        if(comment.getPost().getId()!=post.getId()) {
        	throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belong to the post");
        }
		
		return mapToDto(comment);
	}

	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
		
		Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "id", postId));
		Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "id", commentId));
		
		
		if(comment.getPost().getId()!=post.getId()) {
        	throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belong to the post");
        }
		
		comment.setBody(commentDto.getBody());
		comment.setEmail(commentDto.getEmail());
		comment.setName(commentDto.getName());
		
		Comment comment1=commentRepository.save(comment);
		
		
		return mapToDto(comment1);
	}
	
	@Override
    public void deleteComment(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(comment.getPost().getId()!=post.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        commentRepository.delete(comment);
    }
	
	private CommentDto mapToDto(Comment comment1) {
		// TODO Auto-generated method stub
		CommentDto commentDto=mapper.map(comment1, CommentDto.class);
//		CommentDto commentDto=new CommentDto();
//		commentDto.setId(comment1.getId());
//		commentDto.setBody(comment1.getBody());
//		commentDto.setEmail(comment1.getEmail());
//		commentDto.setName(comment1.getName());
		
		return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		// TODO Auto-generated method stub
		Comment comment=mapper.map(commentDto, Comment.class);
//      Comment comment = new Comment();
//      comment.setId(commentDto.getId());
//      comment.setName(commentDto.getName());
//      comment.setEmail(commentDto.getEmail());
//      comment.setBody(commentDto.getBody());
      return  comment;
	}

	

	

}
