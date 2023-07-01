package com.springboot.blog1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.blog1.payload.CommentDTO;

@Service
public interface CommentService {
	
	CommentDTO createComment(Long postId,CommentDTO commentDTO);
	List<CommentDTO> getAllCommentsByPostId(Long postId);
	CommentDTO getCommentByCommentId(Long postId,Long commentId);
	CommentDTO updateComment(Long postId,Long commentId,CommentDTO commentDTO);
	void deleteComment(Long postId,Long commentId);

}
