package com.springboot.blog1.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.blog1.payload.ErrorDetails;

//used to handle global exception
//in order to customize validation exceprion we extended ResponseEntityExceptionHandler
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// used to handle specific exception and return custom response to client
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest) {

		// defining errorDetails variable in constructor
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BlogAPIException.class)
	public ResponseEntity<ErrorDetails> handleBlogApiException(BlogAPIException exception, WebRequest webRequest) {

		// defining errorDetails variable in constructor
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	// global exception handling
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handlleException(Exception exception, WebRequest webRequest) {

		// defining errorDetails variable in constructor
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// handleMethodArgumentNotValid is used to handle the exception of validation of bean class

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String,String> errors=new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName=((FieldError)error).getField();
		String message=	error.getDefaultMessage();
		errors.put(fieldName, message);
					});
		
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_GATEWAY);
	}

}
