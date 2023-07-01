package com.springboot.blog1.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog1.entity.Post;
import com.springboot.blog1.exception.ResourceNotFoundException;
import com.springboot.blog1.payload.PostDTO;
import com.springboot.blog1.payload.PostResponse;
import com.springboot.blog1.repository.PostRepository;
import com.springboot.blog1.service.PostService;

@Service
public class PostSeriveImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	//used for modelMapper
	private ModelMapper mapper;

	public PostSeriveImpl(PostRepository postRepository,ModelMapper mapper) {
		super();
		this.postRepository = postRepository;
		this.mapper=mapper;
	}

	// ---------------------------------------------------------------------------------
	@Override
	public PostDTO createPost(PostDTO postDTO) {

		// converting from DTO to entity
		Post post = mapToEntity(postDTO);
		postRepository.save(post);

		// converting from entity to DTO
		PostDTO newPost = mapToDTO(post);
		return newPost;
	}

	// ---------------------------------------------------------------------------------
	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir) {
		/*PostResponce is created to display more detaills about paging like how many pages are 
		 * there and what is the page number and houmany contents are there and total pages and total elemants*/
		/* create pageble instance so that we can add pageNo and pageSize,sort by and sort direction as page request
		*/
		Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
		Page<Post> posts = postRepository.findAll(pageable);

		// get content for page object
		List<Post> listOfPost = posts.getContent(); /*we are storing all the contents in lostOfPost variable*/
		List<PostDTO> content=listOfPost.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());/*we are converting the elements into DTO object*/
		
		PostResponse postResponse=new PostResponse(); /*PostResponce object created to define the variable*/
		postResponse.setContent(content);
		postResponse.setPageNumber(pageNo);
		postResponse.setPageSize(pageSize);
		postResponse.setTotalElement(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
	}

	// ---------------------------------------------------------------------------------
	@Override
	public PostDTO getById(Long id) {

		// fetching entity based on id
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		PostDTO postDto = mapToDTO(post); // converting from entity to DTO
		return postDto;
	}

	// ---------------------------------------------------------------------------------
	@Override
	public PostDTO updatePost(Long id, PostDTO postDTO) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		// converting from dtoToEntity
		post.setPostTitle(postDTO.getPostTitle());
		post.setPostDescription(postDTO.getPostDescription());
		post.setPostContent(postDTO.getPostContent());
		Post updatedPost = postRepository.save(post); // saving in repository

		return mapToDTO(updatedPost);
	}

	// ---------------------------------------------------------------------------------
	@Override
	public void deletePostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		postRepository.deleteById(id);
	}

	// convert entity to DTO
	private PostDTO mapToDTO(Post post) {

		//with the help of modelmapper library we can map entity into DTO 
		PostDTO postDto=mapper.map(post, PostDTO.class);
//		PostDTO entityToDTO = new PostDTO();
		
		
//		entityToDTO.setId(post.getId());
//		entityToDTO.setPostTitle(post.getPostTitle());
//		entityToDTO.setPostDescription(post.getPostDescription());
//		entityToDTO.setPostContent(post.getPostContent());

		return postDto;
	}

	// convert DTO to entity
	private Post mapToEntity(PostDTO postDTo) {
		
		//with the help of modelmapper library we can map entity into DTO , hear postDTo is source object and Post.class is destination object
		Post post=mapper.map(postDTo, Post.class);
		
		//below codes are alternative for modelmapper
//		Post dtoToEntity = new Post();
//		dtoToEntity.setPostTitle(postDTo.getPostTitle());
//		dtoToEntity.setPostDescription(postDTo.getPostDescription());
//		dtoToEntity.setPostContent(postDTo.getPostContent());

		return post;
	}
	


}
