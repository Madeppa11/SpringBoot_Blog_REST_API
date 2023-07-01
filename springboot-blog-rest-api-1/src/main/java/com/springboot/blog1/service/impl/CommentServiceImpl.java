package com.springboot.blog1.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog1.entity.Comment;
import com.springboot.blog1.entity.Post;
import com.springboot.blog1.exception.BlogAPIException;
import com.springboot.blog1.exception.ResourceNotFoundException;
import com.springboot.blog1.payload.CommentDTO;
import com.springboot.blog1.payload.PostDTO;
import com.springboot.blog1.repository.CommentRepository;
import com.springboot.blog1.repository.PostRepository;
import com.springboot.blog1.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	CommentRepository commentRepository;
	PostRepository postRepository;
	ModelMapper mapper;

	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper mapper) {

		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper=mapper;
	}

	@Override
	public CommentDTO createComment(Long postId, CommentDTO commentDTO) {

		// creating instance of comment and converting DTO to entity object
		Comment comment = mapToEntity(commentDTO);

		// retrive post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

		// set post to comment entity
		comment.setPost(post);

		// save comment entity to database
		Comment newComment = commentRepository.save(comment);

		return mapToDto(newComment);
	}

//	@Override
//	public List<CommentDTO> getAllCommentsByPostId(long posId) {
//		List<Comment> comments=commentRepository.findByPostId(posId);
//		return comments.stream().map(com->mapToDto(com)).collect(Collectors.toList());
//	}

//	@Override
//	public List<CommentDTO> getAllCommentsByPostId(long posId) {
//		
//	List<Comment> comments=	commentRepository.findByPostId(posId);
//
//		return comments.stream().map(com->mapToDto(com)).collect(Collectors.toList());
//	}

	/* Convert from entity to DTO */
	private CommentDTO mapToDto(Comment comment) {
		CommentDTO commentDto = mapper.map(comment, CommentDTO.class);
//		commentDto.setBody(comment.getBody());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setName(comment.getName());
//		commentDto.setId(comment.getId());

		return commentDto;
	}

	/* Convert from DTO to entity object */
	private Comment mapToEntity(CommentDTO commentDTO) {
		Comment comment = mapper.map(commentDTO, Comment.class);
//		comment.setName(commentDTO.getName());
//		comment.setEmail(commentDTO.getEmail());
//		comment.setBody(commentDTO.getBody());
//		comment.setPost(comment.getPost());
//		comment.setId(commentDTO.getId());
		return comment;
	}

	@Override
	public List<CommentDTO> getAllCommentsByPostId(Long postId) {

		// retriving comments by postId
		List<Comment> comments = commentRepository.findByPostId(postId);

		// converting list of comments entity into list of commentDTO
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDTO getCommentByCommentId(Long postId, Long commentId) {
		// retriving post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

		// retriving comment entity by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

		// Checking wheather comment belongs to particular post or not if it not belongs
		// then created custom exception that is blogAPIException
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_GATEWAY, "Comment doesnot belongs to post.");

		}
		return mapToDto(comment);
	}

	@Override
	public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO) {

		// retriving post by postId
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

		// retriving comment by commentId
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_GATEWAY, "Comment Doesnot belongs to post.");
		}

		// updating prvious value with new values
		comment.setName(commentDTO.getName());
		comment.setEmail(commentDTO.getEmail());
		comment.setBody(commentDTO.getBody());

		// storing new values in repository
		Comment newComment = commentRepository.save(comment);

		return mapToDto(newComment);
	}

	@Override
	public void deleteComment(Long postId, Long commentId) {
		// retriving post by postId
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

		// retriving comment by commentId
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_GATEWAY, "Comment Doesnot belongs to post.");
		} else {
			commentRepository.delete(comment);
		}
	}

}
