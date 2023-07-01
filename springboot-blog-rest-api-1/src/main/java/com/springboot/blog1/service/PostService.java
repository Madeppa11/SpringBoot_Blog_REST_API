package com.springboot.blog1.service;

import java.util.List;

import com.springboot.blog1.payload.PostDTO;
import com.springboot.blog1.payload.PostResponse;

public interface PostService {
 
	PostDTO createPost(PostDTO postDTO);
	PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir);
	PostDTO getById(Long id);
	PostDTO updatePost(Long id,PostDTO postDTO);
	void deletePostById(Long id);

}
