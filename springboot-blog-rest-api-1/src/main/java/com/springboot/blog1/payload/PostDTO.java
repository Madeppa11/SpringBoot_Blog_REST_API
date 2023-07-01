package com.springboot.blog1.payload;


import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDTO {

	private long id;
	
	@NotEmpty
	@Size(min = 2,message = "Title should be mininum 2 character")
	private String postTitle;
	
	@NotEmpty
	@Size(min = 10,message = "description should not more then ten words")
	private String postDescription;
	
	@NotEmpty
	private String postContent;
	private Set<CommentDTO> comments;
}
