package com.springboot.blog1.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDTO {

	private Long id;
	
	@NotEmpty(message = "Name shouldnot be null")
	private String name;
	
	@NotEmpty(message = "email shouldnot be null or empty")
	@Email
	private String email;
	
	@NotEmpty(message = "Body shouldnot be null or empty")
	@Size(min = 10,message = "body should be minimum 10 words..!")
	private String body;
}
