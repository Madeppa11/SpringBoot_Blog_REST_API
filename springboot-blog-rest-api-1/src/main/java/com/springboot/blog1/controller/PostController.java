package com.springboot.blog1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog1.payload.PostDTO;
import com.springboot.blog1.payload.PostResponse;
import com.springboot.blog1.service.PostService;
import com.springboot.blog1.utils.AppConstant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	// construcor injection
	public PostController(PostService postService) {

		this.postService = postService;
	}

	/*---------------------------------------------------------------------------------
	 * create post posts blog API: http://localhost:8080/api/posts
	 */
	@PostMapping()
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {

		return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
	}

	/*---------------------------------------------------------------------------------
	 * create get all posts API: http://localhost:8080/api/posts
	 * http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=postTitle
	 pagination and sorting can be rquired when we display domain data in the tabular form in UI
	 pagination contains two fileds pagesSize and pageNumber there default value is 10 and 1 respectively
	 pagination can be applied to GET API 
	 */
	@GetMapping()
	public PostResponse getPost(
			@RequestParam(value = "pageNo",defaultValue = AppConstant.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
			@RequestParam(value = "pageSize",defaultValue = AppConstant.DEFAULT_PAGE_SIZE,required = false) int pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstant.DEFAULT_SORT_BY,required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue =AppConstant.DEFAULT_SORT_DIR,required = false) String sortDir) {

		return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
	}

	/*---------------------------------------------------------------------------------
	 * get post by id http://localhost:8080/api/posts/4
	 */
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getById(@PathVariable("id") long id) {

		return new ResponseEntity<PostDTO>(postService.getById(id), HttpStatus.OK);
	}

	/*---------------------------------------------------------------------------------
	 * update existing resource:http://localhost:8080/api/posts/update/4
	 */

	@PutMapping("/update/{id}")
	public ResponseEntity<PostDTO> updatePost(@PathVariable("id") long id, @RequestBody PostDTO postDto) {

		return new ResponseEntity<PostDTO>(postService.updatePost(id, postDto), HttpStatus.OK);
	}

	/*---------------------------------------------------------------------------------
	 * delete post by id:http://localhost:8080/api/posts/delete/
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deletePostByID(@PathVariable("id") long id) {
		postService.deletePostById(id);
		return new ResponseEntity<>("post entity deleted succesfully.......!", HttpStatus.OK);
	}
	
}
