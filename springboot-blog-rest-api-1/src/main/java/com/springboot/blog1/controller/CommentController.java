package com.springboot.blog1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog1.payload.CommentDTO;
import com.springboot.blog1.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	CommentService commentService;

	public CommentController(CommentService commentService) {

		this.commentService = commentService;
	}

	// API for creating new resource in the server

	// http://localhost:8080/api/posts/15/comments
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") long postId,
			@Valid @RequestBody CommentDTO commentDTO) {

		return new ResponseEntity<CommentDTO>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
	}

	// API for getting all records based on postId
	// http://localhost:8080/api/posts/1/comments
	@GetMapping("/posts/{postId}/comments")
	List<CommentDTO> getCommentById(@PathVariable(value = "postId") long postId) {

		return commentService.getAllCommentsByPostId(postId);
	}

	// API for getting records based on commentId
	// http://localhost:8080/api/posts/1/comments/4
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> getCommentById(@PathVariable("postId") Long postId,
			@PathVariable(value = "commentId") Long commentId) {

		return new ResponseEntity<CommentDTO>(commentService.getCommentByCommentId(postId, commentId), HttpStatus.OK);
	}

	// API for updating alredy existing comment
	// http://localhost:8080/api/posts/1/comments/1
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable("postId") Long postId,
			@PathVariable("commentId") Long commentId,@Valid  @RequestBody CommentDTO commentDTO) {

		return new ResponseEntity<CommentDTO>(commentService.updateComment(postId, commentId, commentDTO),
				HttpStatus.OK);
	}

	// API for delete the comment by Id

	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable("postId") Long postId,
			@PathVariable("commentId") Long commentId) {
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<String>("comment deleted successfully", HttpStatus.OK);
	}

}
