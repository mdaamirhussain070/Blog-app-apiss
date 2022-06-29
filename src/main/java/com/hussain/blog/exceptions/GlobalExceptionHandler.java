package com.hussain.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hussain.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		
		String message=ex.getMessage();
		ApiResponse apiresponse =new ApiResponse(message,false);
		
		return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		
		Map<String, String> resp=new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((errors)->{
			String fieldName=((FieldError)errors).getField();
			String message=errors.getDefaultMessage();
			resp.put(fieldName, message);
		});
		
		return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
	}

}
