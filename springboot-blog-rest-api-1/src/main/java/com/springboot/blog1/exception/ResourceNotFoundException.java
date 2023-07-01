package com.springboot.blog1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)    /*it cause springboot to respond with the specified http status code when ever this exception is throws from your controller*/
public class ResourceNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String fieldName;
	private long fieldValue;
	
	
	public ResourceNotFoundException(String resourceName, String fieldName, long id) {
		super(String.format("%s not found with %s:'%s'",resourceName,fieldName,id));// it will print like that :::post not found with id:1
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = id;
	}


	public String getResourceName() {
		return resourceName;
	}


	public String getFieldName() {
		return fieldName;
	}


	public long getFieldValue() {
		return fieldValue;
	}

}
