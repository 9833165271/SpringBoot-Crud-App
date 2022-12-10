package com.example.demo.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.ErrorData;

@RestControllerAdvice
public class CustomeExceptionHandlerService {
	/*
	 * Below method is called by FC. When ProductNotFoundException is thrown by any Rest 
	 * Controller (after throwing exception)
	 * 
	 * 
	 */
	
	//-->Message comes in String Format 
	/*
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> handleProductNotFoundException(
			ProductNotFoundException pne
			){
		return new ResponseEntity<String>(pne.getMessage(), HttpStatus.NOT_FOUND);
		
	} */
	
	//-->Message comes in JSON  Format
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorData> handleProductNotFoundException(
			ProductNotFoundException pne
			){
		return new ResponseEntity<ErrorData>(
				new ErrorData(
						pne.getMessage(), 
						new Date().toString(), 
						"Product"), 
				HttpStatus.NOT_FOUND
				);
		
	}

}
